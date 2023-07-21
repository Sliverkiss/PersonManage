package io.github.sliverkiss.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import io.github.sliverkiss.domain.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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

    @GetMapping("employee/download")
    public void downloadEmployee(HttpServletResponse response) throws Exception {
        response.setContentType ( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        response.setCharacterEncoding ( "utf-8" );
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode ( "员工信息表", "UTF-8" ).replaceAll ( "\\+", "%20" );
        response.setHeader ( "Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx" );
        List<EmployeeVo> list = employeeService.getEmployeeVoList ();
        EasyExcel.write ( response.getOutputStream (), EmployeeVo.class ).sheet ( "sheet1" ).doWrite ( list );
    }
}
