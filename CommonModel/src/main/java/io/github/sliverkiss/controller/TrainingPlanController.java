package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.TrainConstants;
import io.github.sliverkiss.controller.DTO.TrainingPlanDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.TrainningRecordService;
import io.github.sliverkiss.service.impl.TrainingPlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@RestController
@RequestMapping("admin/training/plan")
public class TrainingPlanController extends BaseController<TrainingPlanServiceImpl, TrainingPlan> {
    @Autowired
    private TrainningRecordService recordService;

    @GetMapping("/page")
    public ResponseResult selectPage(TrainingPlanDTO trainingPlanDTO) {
        return service.selectPage ( trainingPlanDTO );
    }

    @GetMapping("/sign")
    public ResponseResult signUp(TrainningRecord trainningRecord) {
        System.out.println ( trainningRecord );
        // return null;
        return service.signUp ( trainningRecord );
    }

    @Override
    public void beforeUpdate(TrainingPlan trainingPlan) throws Exception {
        switch (trainingPlan.getStatus ()) {
            case TrainConstants.TRAIN_PLAN_NONE:
                // 如果计划状态为未开始，则什么都不做
                // 调试时启用
                service.recordStateChange ( trainingPlan, "已报名" );
                break;
            case TrainConstants.TRAIN_PLAN_DOING:
                // 如果计划状态为进行中，则修改该计划培训人员培训状态为已结束
                service.recordStateChange ( trainingPlan, TrainConstants.TRAIN_PLAN_DOING );
                break;
            default:
                // 计划为已结束，则修改该计划培训人员培训状态为进行中
                service.recordStateChange ( trainingPlan, TrainConstants.TRAIN_PLAN_DONE );
                break;
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        LambdaQueryWrapper<TrainningRecord> wrapper = Wrappers.lambdaQuery ( TrainningRecord.class );
        wrapper.eq ( TrainningRecord::getPlanId, id );
        List<TrainningRecord> list = recordService.list ( wrapper );
        if (list.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该计划已被使用，无法删除！" );
        }
        return service.deleteEntity ( id );
    }


}
