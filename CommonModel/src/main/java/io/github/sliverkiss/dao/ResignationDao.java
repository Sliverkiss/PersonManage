package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Resignation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 离职记录(Resignation)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:56:46
 */
@Mapper
public interface ResignationDao extends MyMapper<Resignation> {

}

