package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Indicator;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 绩效考核标准表(Indicator)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-05 15:53:06
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface IndicatorDao extends ICrudDao<Indicator> {

}

