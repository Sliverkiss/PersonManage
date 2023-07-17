package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */

public interface ICrudService<T extends BaseEntity> extends IService<T> {


    ResponseResult saveEntity(T entity);

    @Transactional(rollbackFor = Exception.class)
    ResponseResult updateEntity(T entity);

    @Transactional(rollbackFor = Exception.class)
    ResponseResult deleteEntity(Integer id);


}
