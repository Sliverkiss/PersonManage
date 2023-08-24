package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.EmployeeConstants;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.ResignationDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Resignation;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.ResignationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/3
 */
@RestController
@RequestMapping("admin/department/resignation")
public class ResignationController extends BaseController<ResignationServiceImpl, Resignation> {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page")
    public ResponseResult selectPage(ResignationDTO resignationDTO) {
        return service.selectPage ( resignationDTO );
    }

    @Override
    public void beforeSave(Resignation resignation) {
        // 获取当前日期并注入
        String applyDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( resignation ).ifPresent ( e -> {
            resignation.setApplyDate ( applyDate );
        } );
    }

    @Override
    public boolean beforeSaveCheck(Resignation resignation) {
        List<Resignation> list = service.list ( Wrappers.lambdaQuery ( Resignation.class ).eq ( Resignation::getEmployeeId, resignation.getEmployeeId () )
                .eq ( Resignation::getState, SystemConstants.SYSTEM_STATUS_NONE ) );
        if (list.size () == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void beforeUpdate(Resignation resignation) {
        // 注入审核日期
        String applyDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( resignation ).ifPresent ( e -> {
            resignation.setReviewDate ( applyDate );
        } );
        // 若是审核通过，修改员工信息
        if (resignation.getState ().equals ( SystemConstants.SYSTEM_STATUS_YES )) {
            Employee employee = employeeService.getById ( resignation.getEmployeeId () );
            employee.setWorkState ( EmployeeConstants.WORK_STATE_LEAVES );
            // 修改员工在职状态为离职
            employeeService.update ( employee, Wrappers.lambdaQuery ( Employee.class ).eq ( Employee::getId, employee.getId () ) );
        }
    }


}
