package io.github.sliverkiss.service;

import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;

/**
 * 部门(Department)表服务接口
 *
 * @author tistzach
 * @since 2023-07-06 12:50:06
 */
public interface DepartmentService extends ICrudService<Department> {

    public ResponseResult selectDepartmentList();
}

