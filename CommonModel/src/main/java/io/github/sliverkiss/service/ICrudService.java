package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.BaseEntity;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */

public interface ICrudService<T extends BaseEntity> extends IService<T> {

    @Transactional(rollbackFor = Exception.class)
    default ResponseResult saveEntity(T entity) {
        try {
            beforeSave ( entity );
            this.save ( entity );
            afterSave ( entity );
            return ResponseResult.okResult ();
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }

    ;

    @Transactional(rollbackFor = Exception.class)
    default ResponseResult updateEntity(T entity) {
        return saveEntity ( entity );
    }

    ;

    @Transactional(rollbackFor = Exception.class)
    default ResponseResult deleteEntity(Integer id) {
        if (id != null) {
            try {
                this.removeById ( id );
                return ResponseResult.okResult ();
            } catch (Exception e) {
                throw new SystemException ( AppHttpCodeEnum.DELETE_FAILED );
            }
        } else {
            return ResponseResult.errorResult ( AppHttpCodeEnum.DELETE_FAILED );
        }
    }

    ;

    /**
     * 保存前执行
     *
     * @param entity 实体
     */
    default void beforeSave(T entity) {
    }

    ;

    /**
     * 保存后执行
     *
     * @param entity 实体
     */
    default void afterSave(T entity) {
    }

    ;

}
