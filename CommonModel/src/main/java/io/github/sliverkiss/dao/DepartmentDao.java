package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门(Department)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:48:50
 */
@Mapper
public interface DepartmentDao extends MyMapper<Department> {

}

