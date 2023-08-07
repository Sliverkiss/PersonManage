package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.DepartmentQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.service.impl.DepartmentServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/6
 */
@RestController
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController<DepartmentServiceImpl, Department> {

    @GetMapping("/list")
    public ResponseResult selectDepartmentList() {
        return ResponseResult.okResult ( service.list () );
    }

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(DepartmentQueryDTO departmentQueryDTO) {
        return service.selectPage ( departmentQueryDTO );
    }
}
