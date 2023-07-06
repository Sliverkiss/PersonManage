package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.vo.EmployeeVo;

/**
 * 员工表(Employee)表服务接口
 *
 * @author tistzach
 * @since 2023-07-05 11:45:06
 */
public interface EmployeeService extends IService<Employee> {

    public ResponseResult selectEmployeePage();

    public ResponseResult saveEmployee(EmployeeVo employeeVo);
}

