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
import java.util.stream.Collectors;

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
        // stream流获取该部门下所有员工
        List<Employee> employeeList = employeeService.list ().stream ()
                .filter ( e -> e.getDepartment ().getId ().equals ( id ) ).collect ( Collectors.toList () );
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
}
