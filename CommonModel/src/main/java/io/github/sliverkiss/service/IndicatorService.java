package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.IndicatorDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Indicator;

/**
 * 绩效考核标准表(Indicator)表服务接口
 *
 * @author tistzach
 * @since 2023-08-05 15:53:06
 */
public interface IndicatorService extends ICrudService<Indicator> {

    ResponseResult selectPage(IndicatorDTO indicatorDTO);
}

