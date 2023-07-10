package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/8
 */
@SpringBootTest
@Slf4j
class EmployeeControllerTest {

    @Resource
    private EmployeeService employeeService;

    @Test
    public void test(){
        String s = DateUtil.endContract ( "2022-07-08", 3 );
        System.out.println (s);
    }

    @Test
    public void test2(){
        Page<Employee> page = new Page<> ( 1, 5 );
        Page<Employee> employeePage = employeeService.page ( page );
        IPage<EmployeeVo> employeeVoIPage = EntityUtils.toPage ( employeePage, EmployeeVo::new );
        employeeVoIPage.getRecords().stream ().map ( Employee::getDepartmentId ).collect( Collectors.toList());
        log.warn (employeeVoIPage.toString());
    }
}