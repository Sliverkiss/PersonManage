package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Notice;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 公告(Notice)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-14 15:35:47
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface NoticeDao extends ICrudDao<Notice> {

}

