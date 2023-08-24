package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.TransferQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Transfer;

/**
 * 调岗申请表(Transfer)表服务接口
 *
 * @author tistzach
 * @since 2023-07-27 15:00:20
 */
public interface TransferService extends ICrudService<Transfer> {

    ResponseResult selectPage(TransferQueryDTO transferQueryDTO);
}

