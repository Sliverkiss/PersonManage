package io.github.sliverkiss.dao.RBAC;


import io.github.sliverkiss.dao.ICrudDao;
import io.github.sliverkiss.domain.entity.RBAC.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * (RolePermission)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-07 18:24:18
 */
@Mapper
// @CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface RolePermissionDao extends ICrudDao<RolePermission> {

}

