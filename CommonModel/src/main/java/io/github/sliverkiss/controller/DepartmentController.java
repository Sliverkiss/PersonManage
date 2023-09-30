package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.controller.DTO.DepartmentQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/6
 */
@RestController
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController<DepartmentServiceImpl, Department> {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult ( service.list () );
    }

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(DepartmentQueryDTO departmentQueryDTO) {
        return service.selectPage ( departmentQueryDTO );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        // 获取正在使用该部门的员工
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery ( Employee.class );
        wrapper.eq ( Employee::getDepartmentId, id );
        List<Employee> employeeList = employeeService.list ( wrapper );
        if (employeeList.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该部门已被使用，无法删除" );
        }
        LambdaQueryWrapper<Department> departmentWrapper = Wrappers.lambdaQuery ( Department.class );
        departmentWrapper.eq ( Department::getParentId, id );
        List<Department> departmentList = service.list ( departmentWrapper );
        if (departmentList.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该部门存在下属部门，无法删除" );
        }
        return service.deleteEntity ( id );
    }

    // @Override
    // public void beforeSave(Department entity) throws Exception {
    //     Employee employee = employeeService.getEmployeeById ( id );
    // }

}
