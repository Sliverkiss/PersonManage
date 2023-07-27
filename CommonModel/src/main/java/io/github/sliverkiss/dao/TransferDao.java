package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Transfer;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 调岗申请表(Transfer)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-27 15:00:20
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface TransferDao extends ICrudDao<Transfer> {

}

