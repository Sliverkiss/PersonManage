package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.AssessItemQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessItem;

/**
 * 考核项(AssessItem)表服务接口
 *
 * @author tistzach
 * @since 2023-08-17 16:39:34
 */
public interface AssessItemService extends ICrudService<AssessItem> {

    ResponseResult selectPage(AssessItemQueryDTO dto);
}

