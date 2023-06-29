package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Salary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 薪资(Salary)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:56:58
 */
@Mapper
public interface SalaryDao extends MyMapper<Salary> {

}

