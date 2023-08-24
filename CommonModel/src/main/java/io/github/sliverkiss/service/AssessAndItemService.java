package io.github.sliverkiss.service;

import io.github.sliverkiss.domain.entity.assess.AssessAndItem;

import java.util.List;

/**
 * 考核计划中间表(AssessAndItem)表服务接口
 *
 * @author tistzach
 * @since 2023-08-17 16:40:30
 */
public interface AssessAndItemService extends ICrudService<AssessAndItem> {

    void deleteBatchIds(List<Integer> itemIdList);
}

