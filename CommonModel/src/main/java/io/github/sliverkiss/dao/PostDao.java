package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 岗位信息(Post)表数据库访问层
 *
 * @author tistzach
 * @since 2023-09-04 19:59:36
 */
@Mapper
// @CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface PostDao extends ICrudDao<Post> {

}

