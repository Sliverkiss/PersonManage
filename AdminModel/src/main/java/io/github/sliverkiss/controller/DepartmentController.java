package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.service.DepartmentService;
import io.github.sliverkiss.utils.DateUtil;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/6
 */
@RestController
@RequestMapping("/admin")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;
    @GetMapping("/department/list")
    public ResponseResult selectDepartmentList() {
        return departmentService.selectDepartmentList();
    }

    @SneakyThrows
    @GetMapping("/test")
    public void dateCompare(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2014-03-02");
        Date date2 = sdf.parse("2018-03-02");
        Integer year1= DateUtil.dateCompare(date1, date2).getYear ();
        System.out.println (year1);
    }
}
