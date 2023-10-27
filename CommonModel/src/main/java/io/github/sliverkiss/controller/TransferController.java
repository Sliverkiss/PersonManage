package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.TransferQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Transfer;
import io.github.sliverkiss.domain.entity.TransferItem;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.TransferItemService;
import io.github.sliverkiss.service.impl.TransferServiceImpl;
import io.github.sliverkiss.utils.BeanCopyUtils;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/27
 */
@RestController
@RequestMapping("admin/department/transfer")
@Slf4j
public class TransferController extends BaseController<TransferServiceImpl, Transfer> {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TransferItemService itemService;

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(TransferQueryDTO transferQueryDTO) {
        return service.selectPage ( transferQueryDTO );
    }

    @GetMapping("/transferListPage")
    public ResponseResult transferListPage(TransferQueryDTO transferQueryDTO) {
        return service.transferListPage ( transferQueryDTO );
    }

    @Override
    public void beforeSave(Transfer transfer) {
        Employee employee = employeeService.getById ( transfer.getEmployeeId () );
        // 获取当前日期并注入
        String applyDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        // 注入调出部门信息
        Optional.ofNullable ( employee ).ifPresent ( e -> {
            transfer.setBeforeDepartment ( e.getDepartmentId () )
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
        if (transfer.getState () == null) {
            transfer.setState ( "审核中" );
        }
        if (transfer.getState ().equals ( "审核中" )) {
            // 查询该员工是否存在多条申请记录
            List<Transfer> transfers = service.list ( Wrappers.lambdaQuery ( Transfer.class )
                    .eq ( Transfer::getEmployeeId, transfer.getEmployeeId () )
                    .in ( Transfer::getState, transfer.getState () ) );
            return transfers.size () <= 0;
        }
        return true;
    }

    @RequestMapping("test")
    public ResponseResult getTransfer() {
        return service.test ();
    }

    @PostMapping("/approve")
    public ResponseResult approve(@RequestBody TransferItem transferItem) {
        log.warn ( "开始" );
        System.out.println ( transferItem );
        // 更新日期
        transferItem.setApproveDate ( DateUtil.currentDateFormat () );
        Transfer transfer = service.getById ( transferItem.getTransferId () );
        transfer.setId ( transferItem.getTransferId () );
        List<TransferItem> defaultItemList = itemService.list ( Wrappers.lambdaQuery ( TransferItem.class )
                .eq ( TransferItem::getTransferId, transferItem.getTransferId () )
                .eq ( TransferItem::getApproveType, transferItem.getApproveType () ) );
        // 如果数据库中存在记录，则返回相应提示
        if (defaultItemList.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该记录已审核，请勿重复审核～" );
        }
        // 如果审核结果为不通过，则将调岗审核总结果修改为未通过
        if (transferItem.getStatus ().equals ( SystemConstants.SYSTEM_STATUS_NO )) {
            transfer.setState ( SystemConstants.SYSTEM_STATUS_NO );
            // 存入审核结果
            itemService.save ( transferItem );
        }
        // 如果调出部门与调入部门一致，则copy数据
        if (transfer.getStartDepartment ().getId ().equals ( transfer.getEndDepartment ().getId () )) {
            TransferItem item = BeanCopyUtils.copyBean ( transferItem, TransferItem.class );
            itemService.save ( transferItem );
            // 调入部门ApproveType=1
            item.setApproveType ( 2 );
            itemService.save ( item );
        }
        // 如果审核结果为通过，且不为最终审核
        if (transferItem.getStatus ().equals ( SystemConstants.SYSTEM_STATUS_YES ) && transferItem.getApproveType () < 2) {
            itemService.save ( transferItem );
        }
        // 如果审核结果均为通过，则修改调岗审核总结果为通过
        if (transferItem.getStatus ().equals ( SystemConstants.SYSTEM_STATUS_YES ) && transferItem.getApproveType () == 2) {
            transfer.setState ( SystemConstants.SYSTEM_STATUS_YES );
            itemService.save ( transferItem );
            // 同步修改员工状态
            Employee employee = employeeService.getById ( transfer.getEmployee ().getId () );
            employee.setDepartmentId ( transfer.getEndDepartment ().getId () );
            employee.setPost ( transfer.getTransferPost () );
            employeeService.updateById ( employee );
        }
        // 更新流程状态
        transfer.setDeclareType ( transferItem.getApproveType () + 1 );
        service.updateById ( transfer );
        log.warn ( "结束" );
        // List<TransferItem> itemList = transfer.getItemList ();
        // System.out.println (itemList);
        return ResponseResult.okResult ();
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        Transfer transfer = service.getById ( id );
        List<Integer> itemIds = transfer.getItemList ()
                .stream ().map ( TransferItem::getId ).collect ( Collectors.toList () );
        itemService.removeByIds ( itemIds );
        return service.deleteEntity ( id );
    }
}
