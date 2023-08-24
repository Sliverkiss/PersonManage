package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 绩效申报(AssessDeclare)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-17 16:40:04
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface AssessDeclareDao extends ICrudDao<AssessDeclare> {

}

