package io.github.sliverkiss.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.BaseEntity;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.ICrudService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */

public abstract class BaseController<S extends ICrudService<T>, T extends BaseEntity> {

    @Autowired
    protected S service;

    protected Logger log = LoggerFactory.getLogger ( this.getClass () );

    /**
     * 加载
     *
     * @param id
     *
     * @return
     *
     * @throws Exception
     */
    @ApiOperation(value = "加载", notes = "根据ID加载")
    @GetMapping("/edit/{id}")
    public T edit(@PathVariable Long id) throws Exception {
        T entity = service.getById ( id );
        afterEdit ( entity );
        return entity;
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "ID存在修改，不存在添加")
    public ResponseResult save(@RequestBody T entity) {
        try {
            beforeSave ( entity );
            return service.saveEntity ( entity );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改", notes = "ID存在修改，不存在添加")
    public ResponseResult update(@RequestBody T entity) {
        try {
            beforeUpdate ( entity );
            return service.updateEntity ( entity );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        try {
            service.deleteEntity ( id );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
        return ResponseResult.okResult ();
    }

    /**
     * 保存前执行
     *
     * @param entity
     *
     * @throws Exception
     */
    public void beforeSave(T entity) throws Exception {

    }

    public void beforeUpdate(T entity) throws Exception {

    }

    /**
     * 模板方法：在加载后执行
     *
     * @param entity
     */
    public void afterEdit(T entity) {

    }

    /**
     * 根据id获取信息
     *
     * @param id
     *
     * @return
     */
    @GetMapping("/info/{id}")
    public T info(@PathVariable Long id) {
        return service.getById ( id );
    }

}
