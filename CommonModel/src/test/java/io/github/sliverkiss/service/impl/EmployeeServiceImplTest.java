package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.sliverkiss.controller.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/8
 */

@SpringBootTest
class EmployeeServiceImplTest {

    @Resource
    private EmployeeService employeeService;

    @Autowired
    private PersonalDao personalDao;

    @MockBean
    EmployeeQueryDTO employeeQueryDTO;

    @Test
    void selectEmployeePage() {
        employeeQueryDTO.setCurrentPage ( 1 );
        employeeQueryDTO.setPageSize ( 100 );
        IPage<EmployeeVo> page = (IPage<EmployeeVo>) employeeService.selectEmployeePage ( employeeQueryDTO ).getData ();
        List<EmployeeVo> list = page.getRecords ();
        System.out.println ( list );
    }

    @Test
    void getEmployee() {

    }
}