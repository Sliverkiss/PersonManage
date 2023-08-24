package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.TrainningRecordDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainningRecord;

/**
 * 培训记录(TrainningRecord)表服务接口
 *
 * @author tistzach
 * @since 2023-08-05 19:35:02
 */
public interface TrainningRecordService extends ICrudService<TrainningRecord> {

    ResponseResult selectPage(TrainningRecordDTO trainningRecordDTO);
}

