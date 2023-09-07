package io.github.sliverkiss.dao.RBAC;


import io.github.sliverkiss.dao.ICrudDao;
import io.github.sliverkiss.domain.entity.RBAC.Permission;
import io.github.sliverkiss.domain.entity.RBAC.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/7
 */
@Mapper
// @CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface PermissionDao extends ICrudDao<Permission> {

    @Select("select * from role_permission where role_id = #{roleId}")
    List<RolePermission> getRolePermissionByRoleId(Integer roleId);
}
