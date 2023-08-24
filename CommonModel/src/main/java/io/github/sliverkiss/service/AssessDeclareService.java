package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.AssessDeclareQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;

import java.util.List;

/**
 * 绩效申报(AssessDeclare)表服务接口
 *
 * @author tistzach
 * @since 2023-08-17 16:40:04
 */
public interface AssessDeclareService extends ICrudService<AssessDeclare> {

    /**
     * 判断declare是否存在数据库
     *
     * @param declare 声明
     *
     * @return boolean
     */
    public boolean existDeclare(AssessDeclare declare);

    ResponseResult selectPage(AssessDeclareQueryDTO dto);

    AssessDeclare getDeclare(Integer assessId, Integer emploeyyId, Integer type);

    <T extends AssessDeclare> List<T> getDeclareChildren(T entity);
}

