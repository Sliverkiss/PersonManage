package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Reinstatement;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 复职记录表(Reinstatement)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-16 09:24:37
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface ReinstatementDao extends ICrudDao<Reinstatement> {

}

