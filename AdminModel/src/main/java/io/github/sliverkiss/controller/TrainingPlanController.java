package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.TrainingPlanDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.service.impl.TrainingPlanServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@RestController
@RequestMapping("admin/training/plan")
public class TrainingPlanController extends BaseController<TrainingPlanServiceImpl, TrainingPlan> {
    @GetMapping("/page")
    public ResponseResult selectPage(TrainingPlanDTO trainingPlanDTO) {
        return service.selectPage ( trainingPlanDTO );
    }

}
