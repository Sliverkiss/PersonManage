package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.ScheduledCache;

import java.util.List;

/**
 * 员工表(Employee)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-05 11:45:05
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface EmployeeDao extends ICrudDao<Employee> {

    @Select("select * from hros.employee_work where id=#{id} and del_flag=0")
    @Results({
            @Result(property = "personal", column = "personal_id", one = @One(select = "io.github.sliverkiss.dao.PersonalDao.getByEmployeeId")),
            @Result(property = "department", column = "department_id", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById"))
    })
    public Employee getEmployeeById(Integer id);

    @Select("select * from hros.employee_work where work_state='在职' and del_flag=0")
    @Results({
            @Result(property = "personal", column = "personal_id", one = @One(select = "io.github.sliverkiss.dao.PersonalDao.getByEmployeeId")),
            @Result(property = "department", column = "department_id", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById"))
    })
    public List<Employee> selectList();

    @Select("select * from hros.employee_work where post=#{post} and work_state='在职' and del_flag=0")
    @Results({
            @Result(property = "personal", column = "personal_id", one = @One(select = "io.github.sliverkiss.dao.PersonalDao.getByEmployeeId")),
            @Result(property = "department", column = "department_id", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById"))
    })
    List<Employee> getEmpByPost(@Param("post") String post);

    @Select("select * from hros.employee_work where id in #{ids} and del_flag=0")
    @Results({
            @Result(property = "personal", column = "personal_id", one = @One(select = "io.github.sliverkiss.dao.PersonalDao.getByEmployeeId")),
            @Result(property = "department", column = "department_id", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById"))
    })
    List<Employee> getEmpByIds(List<Integer> ids);

    // 获取部门下所有员工
    @Select("select * from hros.employee_work where department_id = #{dept} and del_flag=0")
    @Results({
            @Result(property = "personal", column = "personal_id", one = @One(select = "io.github.sliverkiss.dao.PersonalDao.getByEmployeeId")),
            @Result(property = "department", column = "department_id", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById"))
    })
    List<Employee> getEmpListByDeptId(Integer dept);
}

