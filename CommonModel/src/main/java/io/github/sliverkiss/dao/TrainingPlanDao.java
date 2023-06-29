package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import org.apache.ibatis.annotations.Mapper;

/**
 * 培训计划(TrainingPlan)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:57:12
 */
@Mapper
public interface TrainingPlanDao extends MyMapper<TrainingPlan> {

}

