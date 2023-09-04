package io.github.sliverkiss.dao.RBAC;


import io.github.sliverkiss.dao.ICrudDao;
import io.github.sliverkiss.domain.entity.RBAC.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 用户(User)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-03 13:12:51
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface UserDao extends ICrudDao<User> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer saveUser(User user);
}

