package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门(Department)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-06 12:50:04
 */
@Mapper
// @CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface DepartmentDao extends ICrudDao<Department> {
    @Select("select * from department_post where department_id =#{departmentId} ")
    public List<Post> getPostList(Integer departmentId);

    @Select("select * from department_info where id =#{departmentId} ")
    Department getById(Integer departmentId);

}

