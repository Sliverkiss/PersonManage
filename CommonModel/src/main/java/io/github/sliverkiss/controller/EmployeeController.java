package io.github.sliverkiss.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import io.github.sliverkiss.controller.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.dao.TrainningRecordDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@RequestMapping("/admin")
public class EmployeeController {

    @Autowired
    private TrainningRecordDao personalDao;

    @Resource
    private EmployeeService employeeService;


    @RequestMapping("/test")
    public List<TrainningRecord> getById() {
        return personalDao.getRecordListByEmployeeId ( 1 );
    }


    @GetMapping("/employee/page")
    public ResponseResult getEmployeeList(EmployeeQueryDTO employeeQueryDTO) {
        return employeeService.selectEmployeePage ( employeeQueryDTO );
    }

    @PostMapping("employee/save")
    public ResponseResult saveEmployee(@RequestBody JSONObject jsonObject) {
        // 将json对象转换成员工资料视图
        EmployeeVo employeeVo = JSONObject.parseObject ( jsonObject.toJSONString (), EmployeeVo.class );
        return employeeService.saveEmployee ( employeeVo );
    }

    @GetMapping("/employee/list/dasborad")
    public ResponseResult selectEmployee() {
        return ResponseResult.okResult ( employeeService.list () );
    }

    @DeleteMapping("employee/delete/{id}")
    public ResponseResult deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee ( id );
    }

    @RequestMapping("employee/update")
    public ResponseResult updateEmployee(@RequestBody JSONObject jsonObject) {
        // 将json对象转换成员工资料视图
        EmployeeVo employeeVo = JSONObject.parseObject ( jsonObject.toJSONString (), EmployeeVo.class );
        log.warn ( "开始打印" );
        System.out.println ( employeeVo );
        log.warn ( "结束打印" );
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
