package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Resignation;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 离职记录表(Resignation)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-03 16:36:38
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface ResignationDao extends ICrudDao<Resignation> {

}

