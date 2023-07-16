package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Salary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工资表(Salary)表数据库访问层
 *
 * @author tistzach
 * @since 2023-07-16 08:21:39
 */
@Mapper
public interface SalaryDao extends BaseMapper<Salary> {

}

