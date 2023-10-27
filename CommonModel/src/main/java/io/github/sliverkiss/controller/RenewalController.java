package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Renewal;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.impl.RenewalServiceImpl;
import io.github.sliverkiss.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@RestController
@RequestMapping("/admin/employee/renewal")
public class RenewalController extends BaseController<RenewalServiceImpl, Renewal> {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO) {
        return service.selectRenewalPage ( renewalQueryDTO );
    }

    @Override
    public boolean beforeSaveCheck(Renewal renewal) {
        List<Renewal> list = service.list ( Wrappers.lambdaQuery ( Renewal.class ).eq ( Renewal::getEmployeeId, renewal.getEmployeeId () )
                .eq ( Renewal::getState, SystemConstants.RENEWAL_STATUS_WAIT ) );
        if (list.isEmpty ()) {
            return true;
        }
        throw new RuntimeException ( "该员工已存在发起续约记录，请勿重复添加～" );
    }

    @Override
    public void beforeSave(Renewal renewal) throws Exception {
        // 注入申请日期
        String applyDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( renewal ).ifPresent ( e -> e.setApplyDate ( applyDate ) );
        // 注入合同起始日期和终止日期
        Employee employee = employeeService.getById ( renewal.getEmployeeId () );
        Optional.ofNullable ( employee ).ifPresent ( e -> {
            renewal.setStartContract ( e.getStartContract () )
                    .setEndContract ( e.getEndContract () );
        } );

    }

    @Override
    public void beforeUpdate(Renewal renewal) throws Exception {
        // 获取当前日期并注入renewal
        String approvedDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( renewal ).ifPresent ( e -> e.setApprovedDate ( approvedDate ) );
        // 如果同意续约，则修改员工工作信息
        if (renewal.getState ().equals ( SystemConstants.RENEWAL_STATUS_YES )) {
            Optional.ofNullable ( renewal ).ifPresent ( e -> e.setApprovedDate ( approvedDate ) );
            // 注入合同起始日期和终止日期
            Employee employee = employeeService.getById ( renewal.getEmployeeId () );
            Optional.ofNullable ( employee ).ifPresent ( e -> {
                String startContract = e.getEndContract ();
                String endContract = DateUtil.endContract ( e.getEndContract (), renewal.getRenewalAge () );
                e.setStartContract ( startContract )
                        .setContractTerm ( renewal.getRenewalAge () )
                        .setEndContract ( endContract );
                // 更新员工数据
                employeeService.updateById ( employee );
            } );
        }
    }

    @GetMapping("/nearRenewalList")
    public ResponseResult getEmpListByNearRenewal() {
        List<Employee> employeeList = employeeService.list ();
        List<Employee> result = employeeList.stream ()
                .filter ( e -> {
                    try {
                        return DateUtil.dayCompare ( e.getEndContract () ) <= 30;
                    } catch (ParseException ex) {
                        throw new RuntimeException ( ex );
                    }
                } )
                .collect ( Collectors.toList () );
        return ResponseResult.okResult ( result );
    }


}
