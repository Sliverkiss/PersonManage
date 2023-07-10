package io.github.sliverkiss.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.domain.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.stream.Collectors;

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
    
    @GetMapping("/employee/page")
    public ResponseResult getEmployeeList(EmployeeQueryDTO employeeQueryDTO) {
        return employeeService.selectEmployeePage (employeeQueryDTO );
    }

    @PostMapping("employee/save")
    public ResponseResult saveEmployee(@RequestBody JSONObject jsonObject) {
        // 将json对象转换成员工资料视图
        EmployeeVo employeeVo = JSONObject.parseObject ( jsonObject.toJSONString (), EmployeeVo.class );
        return employeeService.saveEmployee ( employeeVo );
    }

    @DeleteMapping("employee/delete/{id}")
    public ResponseResult deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee ( id );
    }

    @RequestMapping("employee/update")
    public ResponseResult updateEmployee(@RequestBody JSONObject jsonObject) {
        // 将json对象转换成员工资料视图
        EmployeeVo employeeVo = JSONObject.parseObject ( jsonObject.toJSONString (), EmployeeVo.class );
        return employeeService.updateEmployee ( employeeVo );
    }

}
