package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.TrainningRecord;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 培训记录(TrainningRecord)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-05 19:35:02
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface TrainningRecordDao extends ICrudDao<TrainningRecord> {

}

