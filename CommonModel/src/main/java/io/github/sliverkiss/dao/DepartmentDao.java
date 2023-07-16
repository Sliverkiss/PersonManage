package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Department;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 部门(Department)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-06 12:50:04
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface DepartmentDao extends BaseMapper<Department> {

}

