package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Salary;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 工资表(Salary)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-16 08:21:39
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface SalaryDao extends ICrudDao<Salary> {


}

