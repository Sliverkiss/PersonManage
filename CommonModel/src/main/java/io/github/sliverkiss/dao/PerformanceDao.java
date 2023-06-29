package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Performance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工绩效(Performance)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:56:35
 */
@Mapper
public interface PerformanceDao extends MyMapper<Performance> {

}

