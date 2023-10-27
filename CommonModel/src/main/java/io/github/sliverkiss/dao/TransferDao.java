package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Transfer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.ScheduledCache;

/**
 * 调岗申请表(Transfer)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-27 15:00:20
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface TransferDao extends ICrudDao<Transfer> {

    @Select("select * from hros.department_transfer where id=#{id} and del_flag=0")
    @Results({
            @Result(property = "employee", column = "employee_id", one = @One(select = "io.github.sliverkiss.dao.EmployeeDao.getEmployeeById")),
            @Result(property = "startDepartment", column = "before_department", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById")),
            @Result(property = "endDepartment", column = "after_department", one = @One(select = "io.github.sliverkiss.dao.DepartmentDao.getById")),
            @Result(property = "itemList", column = "id", one = @One(select = "io.github.sliverkiss.dao.TransferItemDao.getItemsByTransferId")),
    })
    public Transfer selectById(Integer id);
}

