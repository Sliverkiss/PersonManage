package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Leave;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假记录(Leave)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:56:21
 */
@Mapper
public interface LeaveDao extends MyMapper<Leave> {

}

