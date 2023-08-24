package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.TrainingPlanDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.domain.entity.TrainningRecord;

/**
 * 培训计划(TrainingPlan)表服务接口
 *
 * @author tistzach
 * @since 2023-08-05 19:17:27
 */
public interface TrainingPlanService extends ICrudService<TrainingPlan> {


    ResponseResult selectPage(TrainingPlanDTO trainingPlanDTO);

    ResponseResult signUp(TrainningRecord trainningRecord);

    void recordStateChange(TrainingPlan trainingPlan, String state);

}

