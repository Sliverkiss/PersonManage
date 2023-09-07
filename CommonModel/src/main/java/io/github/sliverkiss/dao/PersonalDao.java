package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 人员信息表(Personal)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-05 11:45:33
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface PersonalDao extends BaseMapper<Personal> {

    @Select("select * from employee_info where id=#{personalId}")
    Personal getByEmployeeId(Integer personalId);

    @Select("select * from employee_work where id=#{employeeId}")
    @Results({
            @Result(property = "personal", column = "personal_id", one = @One(select = "getByEmployeeId")),
            @Result(property = "department", column = "department_id", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById"))
    })
    Employee getById(@Param("employeeId") Integer employeeId);

}

