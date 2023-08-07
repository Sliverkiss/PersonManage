package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.TrainingPlan;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 培训计划(TrainingPlan)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-05 19:17:27
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface TrainingPlanDao extends ICrudDao<TrainingPlan> {

}

