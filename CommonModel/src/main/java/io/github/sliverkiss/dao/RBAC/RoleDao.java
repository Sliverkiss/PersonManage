package io.github.sliverkiss.dao.RBAC;


import io.github.sliverkiss.dao.ICrudDao;
import io.github.sliverkiss.domain.entity.RBAC.Role;
import io.github.sliverkiss.domain.entity.RBAC.UserRole;
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
public interface RoleDao extends ICrudDao<Role> {

    @Select("select * from user_role where user_id =#{userId}")
    List<UserRole> getUserRoleByUserId(Integer userId);


}
