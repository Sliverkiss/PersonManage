package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.EmployeeConstants;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.ReinstatementQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Reinstatement;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.ReinstatementServiceImpl;
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
 * @date 2023/8/16
 */
@RestController
@RequestMapping("/admin/reinstatement")
public class ReinstatementController extends BaseController<ReinstatementServiceImpl, Reinstatement> {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/page")
    public ResponseResult selectPage(ReinstatementQueryDTO reinstatementQueryDTO) {
        return service.selectPage ( reinstatementQueryDTO );
    }

    @Override
    public void beforeSave(Reinstatement reinstatement) {
        // 获取当前日期并注入
        String applyDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( reinstatement ).ifPresent ( e -> {
            reinstatement.setApplyDate ( applyDate );
        } );
    }

    @Override
    public boolean beforeSaveCheck(Reinstatement reinstatement) {
        List<Reinstatement> list = service.list ( Wrappers.lambdaQuery ( Reinstatement.class ).eq ( Reinstatement::getEmployeeId, reinstatement.getEmployeeId () )
                .eq ( Reinstatement::getState, SystemConstants.SYSTEM_STATUS_NONE ) );
        if (list.size () == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void beforeUpdate(Reinstatement reinstatement) {
        // 注入审核日期
        String reinDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( reinstatement ).ifPresent ( e -> {
            reinstatement.setReinDate ( reinDate );
        } );
        // 若是审核通过，修改员工信息
        if (reinstatement.getState ().equals ( SystemConstants.SYSTEM_STATUS_YES )) {
            Employee employee = employeeService.getById ( reinstatement.getEmployeeId () );
            employee.setWorkState ( EmployeeConstants.WORK_STATE_COME );
            // 修改员工在职状态为在职
            employeeService.update ( employee, Wrappers.lambdaQuery ( Employee.class ).eq ( Employee::getId, employee.getId () ) );
        }
    }
}
