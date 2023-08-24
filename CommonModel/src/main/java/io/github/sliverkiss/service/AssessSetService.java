package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.AssessSetQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessSet;

/**
 * 绩效考核计划(AssessSet)表服务接口
 *
 * @author tistzach
 * @since 2023-08-17 16:39:11
 */
public interface AssessSetService extends ICrudService<AssessSet> {

    ResponseResult selectPage(AssessSetQueryDTO dto);
}

