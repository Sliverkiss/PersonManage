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
    public ResponseResult save(@RequestBody T entity) throws Exception {
        try {
            if (beforeSaveCheck ( entity )) {
                beforeSave ( entity );
                return service.saveEntity ( entity );
            } else {
                return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "同一员工不能同时拥有多个申请记录,请耐心等待管理员审核～" );
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改", notes = "ID存在修改，不存在添加")
    public ResponseResult update(@RequestBody T entity) throws Exception {
        try {
            if (!checkUpdate ( entity )) {
                throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
            }
            beforeUpdate ( entity );
            return service.updateEntity ( entity );
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return service.deleteEntity ( id );
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

    public boolean checkUpdate(T entity) throws Exception {
        return true;
    }

    /**
     * 更新前执行
     *
     * @param entity 实体
     *
     * @throws Exception 异常
     */
    public void beforeUpdate(T entity) throws Exception {
        beforeSave ( entity );
    }

    /**
     * 在删除之前
     *
     * @param entity 实体
     *
     * @throws Exception 异常
     */
    public void beforeDelete(Integer id) throws Exception {
    }

    /**
     * 模板方法：在加载后执行
     *
     * @param entity
     */
    public void afterEdit(T entity) {

    }

    /**
     * 在保存之前检查验证
     *
     * @return boolean
     */
    public boolean beforeSaveCheck(T entity) {
        return true;
    }


    /**
     * 根据id获取信息
     *
     * @param id
     *
     * @return
     */
    @GetMapping("/info/{id}")
    public T info(@PathVariable Integer id) {
        return service.getById ( id );
    }

}
