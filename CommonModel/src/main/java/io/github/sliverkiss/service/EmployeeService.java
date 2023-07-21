package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.vo.EmployeeVo;

import java.util.List;

/**
 * 员工表(Employee)表服务接口
 *
 * @author tistzach
 * @since 2023-07-05 11:45:06
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 员工检索，实现员工信息的多方位检索，包活员工编号、入职日期、部门、岗位和姓名检索
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult selectEmployeePage(EmployeeQueryDTO employeeQueryDTO );

    /**
     * 入职登记
     *
     * @param employeeVo 员工资料视图
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult saveEmployee(EmployeeVo employeeVo);

    /**
     * 更新员工资料信息
     *
     * @param employeeVo 员工资料视图
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult updateEmployee(EmployeeVo employeeVo);

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

    List<EmployeeVo> getEmployeeVoList();
}

