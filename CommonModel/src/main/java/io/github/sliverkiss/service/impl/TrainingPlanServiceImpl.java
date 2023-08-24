package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.TrainingPlanDTO;
import io.github.sliverkiss.dao.TrainingPlanDao;
import io.github.sliverkiss.dao.TrainningRecordDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.domain.vo.TrainingPlanVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.TrainingPlanService;
import io.github.sliverkiss.service.TrainningRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.EntityUtils;

/**
 * 培训计划(TrainingPlan)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-05 19:17:27
 */
@Service("trainingPlanService")
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanDao, TrainingPlan> implements TrainingPlanService {
    @Autowired
    private TrainningRecordDao trainningRecordDao;
    @Autowired
    private TrainningRecordService trainningRecordService;

    @Override
    public ResponseResult selectPage(TrainingPlanDTO trainingPlanDTO) {
        Page<TrainingPlan> page = this.toPage ( trainingPlanDTO );
        String planName = trainingPlanDTO.getPlanName ();
        String status = trainingPlanDTO.getStatus ();
        try {
            // 模糊查询
            LambdaQueryWrapper<TrainingPlan> wrapper = Wrappers.lambdaQuery ( TrainingPlan.class )
                    .like ( StringUtils.isNotBlank ( planName ), TrainingPlan::getPlanName, planName )
                    .like ( StringUtils.isNotBlank ( status ), TrainingPlan::getStatus, status );
            Page<TrainingPlan> trainingPlanPage = this.page ( page, wrapper );
            IPage<TrainingPlanVo> trainingPlanVoIPage = EntityUtils.toPage ( trainingPlanPage, TrainingPlanVo::new );
            // 属性注入
            return ResponseResult.okResult ( trainingPlanVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResponseResult signUp(TrainningRecord trainningRecord) {
        System.out.println ( trainningRecord );
        TrainingPlan plan = this.getById ( trainningRecord.getPlanId () );
        if (!SystemConstants.PLANE_STATUS_DRAFT.equals ( plan.getStatus () )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "该培训计划已结束，无法报名～" );
        }
        try {
            TrainningRecord result = trainningRecordDao.selectOne ( Wrappers.lambdaQuery ( TrainningRecord.class )
                    .eq ( TrainningRecord::getPlanId, trainningRecord.getPlanId () )
                    .eq ( TrainningRecord::getEmployeeId, trainningRecord.getEmployeeId () ) );
            if (ObjectUtils.isNull ( result )) {

                trainningRecordService.saveOrUpdate ( trainningRecord );
                return ResponseResult.okResult ( AppHttpCodeEnum.SUCCESS.getCode (), "报名成功～" );
            } else {
                return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "已报名，请勿重复报名～" );
            }
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    @Transactional
    public void recordStateChange(TrainingPlan trainingPlan, String state) {
        TrainningRecord trainningRecord = new TrainningRecord ();
        trainningRecord.setPlanState ( state );
        trainningRecordService.update ( trainningRecord, Wrappers.lambdaQuery ( TrainningRecord.class )
                .in ( TrainningRecord::getPlanId, trainingPlan.getId () ) );
    }

}

