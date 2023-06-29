package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Indicator;
import org.apache.ibatis.annotations.Mapper;

/**
 * 绩效考核指标(Indicator)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:56:03
 */
@Mapper
public interface IndicatorDao extends MyMapper<Indicator> {

}

