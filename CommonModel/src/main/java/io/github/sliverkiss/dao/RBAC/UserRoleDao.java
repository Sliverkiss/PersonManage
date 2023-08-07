package io.github.sliverkiss.dao.RBAC;


import io.github.sliverkiss.dao.ICrudDao;
import io.github.sliverkiss.domain.entity.RBAC.UserRole;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * (UserRole)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-07 18:27:46
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface UserRoleDao extends ICrudDao<UserRole> {

}

