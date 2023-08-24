package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.assess.AssessApproval;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 绩效审批(AssessApproval)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-17 16:40:18
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface AssessApprovalDao extends ICrudDao<AssessApproval> {

}

