package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工(Employee)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:55:32
 */
@Mapper
public interface EmployeeDao extends MyMapper<Employee> {

}

