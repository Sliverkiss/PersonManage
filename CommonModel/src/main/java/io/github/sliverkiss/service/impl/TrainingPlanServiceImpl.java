package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.TrainingPlanDao;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.service.TrainingPlanService;
import org.springframework.stereotype.Service;

/**
 * 培训计划(TrainingPlan)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:57:12
 */
@Service("trainingPlanService")
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanDao, TrainingPlan> implements TrainingPlanService {

}

