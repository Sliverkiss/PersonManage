package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;

/**
 * 部门(Department)表服务接口
 *
 * @author tistzach
 * @since 2023-07-06 12:50:06
 */
public interface DepartmentService extends IService<Department> {

    public ResponseResult selectDepartmentList();
}

