package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.domain.DTO.TransferQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Transfer;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.TransferServiceImpl;
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
 * @date 2023/7/27
 */
@RestController
@RequestMapping("admin/department/transfer")
public class TransferController extends BaseController<TransferServiceImpl, Transfer> {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(TransferQueryDTO transferQueryDTO) {
        return service.selectPage ( transferQueryDTO );
    }

    @Override
    public void beforeSave(Transfer transfer) {
        Employee employee = employeeService.getById ( transfer.getEmployeeId () );
        // 获取当前日期并注入
        String applyDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        // 注入调出部门信息
        Optional.ofNullable ( employee ).ifPresent ( e -> {
            transfer.setBeforeDepartment ( e.getId () )
                    .setApplyDate ( applyDate );
        } );
        this.checkState ( transfer );
    }


    @Override
    public void beforeUpdate(Transfer transfer) {
        this.checkState ( transfer );
    }

    /**
     * 检查审核状态，若审核通过，则修改员工信息
     *
     * @param transfer 转移
     */
    public void checkState(Transfer transfer) {
        if (transfer.getAfterDepartment () != null && transfer.getState ().equals ( "通过" )) {
            Employee employee = employeeService.getById ( transfer.getEmployeeId () );
            employee.setDepartmentId ( transfer.getAfterDepartment () );
            // 更新员工部门信息
            employeeService.update ( employee, Wrappers.lambdaQuery ( Employee.class ).eq ( Employee::getId, employee.getId () ) );
        }
    }

    @Override
    public boolean beforeSaveCheck(Transfer transfer) {
        if (transfer.getState ().equals ( "审核中" )) {
            // 查询该员工是否存在多条申请记录
            List<Transfer> transfers = service.list ( Wrappers.lambdaQuery ( Transfer.class )
                    .eq ( Transfer::getEmployeeId, transfer.getEmployeeId () )
                    .in ( Transfer::getState, transfer.getState () ) );
            return transfers.size () <= 0;
        }
        return true;
    }
}
