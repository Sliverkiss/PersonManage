package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工表(Employee)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-05 11:45:05
 */
@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {

}

