package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.domain.vo.TrainningRecordVo;
import io.github.sliverkiss.service.TrainningRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 培训记录(TrainningRecord)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-05 19:35:02
 */
@Service("trainningRecordService")
@Slf4j
public class TrainningRecordServiceImpl extends ServiceImpl<TrainningRecordDao, TrainningRecord> implements TrainningRecordService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private TrainingPlanDao trainingPlanDao;

    @Override
    public ResponseResult selectPage(TrainningRecordDTO trainningRecordDTO) {
        log.warn ( "开始" );
        System.out.println ( trainningRecordDTO );
        log.warn ( "结束" );
        Page<TrainningRecord> page = this.toPage ( trainningRecordDTO );
        // 用户员工编号
        String employeeId = trainningRecordDTO.getEmployeeId ();
        // 模糊查询条件
        String planName = trainningRecordDTO.getPlanName ();
        String planState = trainningRecordDTO.getPlanState ();
        // 获取模糊查询id
        List<Integer> planIds = trainingPlanDao.selectList ( Wrappers.lambdaQuery ( TrainingPlan.class )
                .like ( TrainingPlan::getPlanName, planName ) ).stream ().map ( TrainingPlan::getId ).collect ( Collectors.toList () );
        try {
            // 模糊查询
            LambdaQueryWrapper<TrainningRecord> wrapper = Wrappers.lambdaQuery ( TrainningRecord.class );
            if (planIds.size () > 0 && planName != null) {
                wrapper.in ( planIds.size () > 0, TrainningRecord::getPlanId, planIds );
            }
            if (StringUtils.isNotBlank ( planState )) {
                wrapper.like ( TrainningRecord::getPlanState, planState );
            }
            // if (StringUtils.isNotBlank ( employeeId )) {
            //     wrapper.like (TrainningRecord::getEmployeeId, employeeId );
            // }
            // 如果等于普通用户，进行数据过滤
            if (UserContants.ROLE_USER.equals ( trainningRecordDTO.getUserRole () )) {
                wrapper.eq ( TrainningRecord::getEmployeeId, employeeId );
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
    public <T extends TrainningRecordVo> void recordInnerPersonal(List<T> list) {
        list.forEach ( e -> {
            Employee employee = employeeDao.getEmployeeById ( e.getEmployeeId () );
            TrainingPlan plan = trainingPlanDao.selectById ( e.getPlanId () );
            e.setEmployeeName ( employee.getPersonal ().getName () );
            e.setPlanName ( plan.getPlanName () );
        } );
    }

}

