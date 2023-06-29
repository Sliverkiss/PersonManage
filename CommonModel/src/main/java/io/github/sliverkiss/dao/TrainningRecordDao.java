package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 培训记录(TrainningRecord)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:57:24
 */
@Mapper
public interface TrainningRecordDao extends MyMapper<TrainningRecord> {

}

