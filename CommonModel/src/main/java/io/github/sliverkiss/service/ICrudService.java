package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.BaseEntity;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Map;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */

public interface ICrudService<T extends BaseEntity> extends IService<T> {

    /**
     * 保存实体
     *
     * @param entity 实体
     *
     * @return {@link ResponseResult}
     */
    @Transactional(rollbackFor = Exception.class)
    default ResponseResult saveEntity(T entity) {
        try {
            beforeSave ( entity );
            this.saveOrUpdate ( entity );
            afterSave ( entity );
            return ResponseResult.okResult ();
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }

    /**
     * 更新实体
     *
     * @param entity 实体
     *
     * @return {@link ResponseResult}
     */
    @Transactional(rollbackFor = Exception.class)
    default ResponseResult updateEntity(T entity) {
        return saveEntity ( entity );
    }

    /**
     * 删除实体
     *
     * @param id id
     *
     * @return {@link ResponseResult}
     */
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

    /**
     * 保存前执行
     *
     * @param entity 实体
     */
    default void beforeSave(T entity) {
    }

    /**
     * 保存后执行
     *
     * @param entity 实体
     */
    default void afterSave(T entity) {
    }


    /**
     * 校验分页参数是否存在，并返回分页数据
     *
     * @param e e
     *
     * @return {@link Page}<{@link T}>
     */
    default <T, E extends io.github.sliverkiss.domain.entity.Page> Page<T> toPage(E e) {
        Integer currentPage = e.getCurrentPage ();
        Integer pageSize = e.getPageSize ();
        if (currentPage == null || pageSize <= 0 || pageSize == null || pageSize < 1) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
        return new Page<T> ( currentPage, pageSize );
    }


    /**
     * 根据员工编号，将员工数据进行map分组转换
     *
     * @param employeeDao 员工数据接口
     * @param employeeIds 员工编号集合
     *
     * @return {@link Map}<{@link Integer}, {@link Employee}>
     */
    default Map<Integer, Employee> getEmployeeMap(EmployeeDao employeeDao, List<Integer> employeeIds) {
        List<Employee> employeList = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                .in ( Employee::getId, employeeIds ) );
        Map<Integer, Employee> map = EntityUtils.toMap ( employeList, Employee::getId, e -> e );
        return map;
    }
}
