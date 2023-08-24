package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.TrainningRecordDTO;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.TrainingPlanDao;
import io.github.sliverkiss.dao.TrainningRecordDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.domain.vo.TrainningRecordVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.TrainningRecordService;
import io.github.sliverkiss.utils.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 培训记录(TrainningRecord)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-05 19:35:02
 */
@Service("trainningRecordService")
public class TrainningRecordServiceImpl extends ServiceImpl<TrainningRecordDao, TrainningRecord> implements TrainningRecordService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private TrainingPlanDao trainingPlanDao;

    @Override
    public ResponseResult selectPage(TrainningRecordDTO trainningRecordDTO) {
        Page<TrainningRecord> page = this.toPage ( trainningRecordDTO );
        String employeeId = trainningRecordDTO.getEmployeeId ();
        String planName = trainningRecordDTO.getPlanName ();
        String planState = trainningRecordDTO.getPlanState ();
        List<Integer> planIds = trainingPlanDao.selectList ( Wrappers.lambdaQuery ( TrainingPlan.class )
                .like ( TrainingPlan::getPlanName, planName ) ).stream ().map ( TrainingPlan::getId ).collect ( Collectors.toList () );
        if (CheckUtil.checkStr ( planName, planIds )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }
        try {
            // 模糊查询
            LambdaQueryWrapper<TrainningRecord> wrapper = Wrappers.lambdaQuery ( TrainningRecord.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), TrainningRecord::getEmployeeId, employeeId )
                    .in ( planIds.size () > 0, TrainningRecord::getPlanId, planIds )
                    .like ( StringUtils.isNotBlank ( planState ), TrainningRecord::getPlanState, planState );
            // 如果等于普通用户，进行数据过滤
            if (UserContants.ROLE_USER.equals ( trainningRecordDTO.getUserRole () )) {
                wrapper.eq ( TrainningRecord::getEmployeeId, trainningRecordDTO.getUserEmpId () );
            }
            Page<TrainningRecord> trainningRecordPage = this.page ( page, wrapper );
            IPage<TrainningRecordVo> trainningRecordVoIPage = EntityUtils.toPage ( trainningRecordPage, TrainningRecordVo::new );
            // 属性注入
            this.recordInnerPersonal ( trainningRecordVoIPage.getRecords () );
            return ResponseResult.okResult ( trainningRecordVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 培训记录表连接员工基本信息表，查询员工姓名，连接培训计划表查询计划名称
     *
     * @param trainningRecordVoIPage 培训记录签证官ipage
     */
    public void recordInnerPersonal(List<TrainningRecordVo> trainningRecordVoIPage) {
        if (ObjectUtils.isEmpty ( trainningRecordVoIPage )) {
            return;
        }
        List<Integer> employeeIds = trainningRecordVoIPage.stream ().map ( TrainningRecordVo::getEmployeeId ).collect ( Collectors.toList () );
        Map<Integer, Employee> map = this.getEmployeeMap ( employeeDao, employeeIds );
        trainningRecordVoIPage.forEach ( trainningRecordVo -> {
            Employee employee = map.get ( trainningRecordVo.getEmployeeId () );
            TrainingPlan trainingPlan = trainingPlanDao.selectById ( trainningRecordVo.getPlanId () );
            Personal personal = personalDao.selectById ( employee.getPersonalId () );
            Optional.ofNullable ( personal ).ifPresent ( e -> {
                trainningRecordVo.setEmployeeName ( e.getName () )
                        .setPlanName ( trainingPlan.getPlanName () );
            } );
        } );
    }

}

