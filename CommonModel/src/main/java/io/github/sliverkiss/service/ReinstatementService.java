package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.ReinstatementQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Reinstatement;

/**
 * 复职记录表(Reinstatement)表服务接口
 *
 * @author tistzach
 * @since 2023-08-16 09:24:37
 */
public interface ReinstatementService extends ICrudService<Reinstatement> {

    ResponseResult selectPage(ReinstatementQueryDTO reinstatementQueryDTO);
}

