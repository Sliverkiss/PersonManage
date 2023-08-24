package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Employee;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 员工表(Employee)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-05 11:45:05
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface EmployeeDao extends ICrudDao<Employee> {

}

