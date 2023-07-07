package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/5
 */
@RestController
@RequestMapping("/admin")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @GetMapping("/employee/list")
    public ResponseResult getEmployeeList() {
        return employeeService.selectEmployeePage ();
    }

    @PostMapping("employee/save")
    public ResponseResult saveEmployee(@RequestBody EmployeeVo employeeVo) {
        return employeeService.saveEmployee(employeeVo);
    }

    @DeleteMapping("employee/delete/{id}")
    public ResponseResult deleteEmployee(@PathVariable Integer id){
        return employeeService.deleteEmployee(id);
    }
}
