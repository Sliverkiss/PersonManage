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

    /**
     * 获取员工列表
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult selectEmployeePage();

    /**
     * 入职登记
     *
     * @param employeeVo 员工签证官
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult saveEmployee(EmployeeVo employeeVo);

    /**
     * 删除员工
     *
     * @param id id
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult deleteEmployee(Integer id);
    /**
     * 获取合同列表
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult selectContractPage();
}

