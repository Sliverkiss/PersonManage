package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.EmployeeConstants;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.TransferQueryDTO;
import io.github.sliverkiss.dao.*;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Transfer;
import io.github.sliverkiss.domain.entity.TransferItem;
import io.github.sliverkiss.domain.vo.TransferVo;
import io.github.sliverkiss.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;

/**
 * 调岗申请表(Transfer)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-27 15:00:20
 */
@Service("transferService")
@Slf4j
public class TransferServiceImpl extends ServiceImpl<TransferDao, Transfer> implements TransferService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private TransferItemDao itemDao;

    @Override
    public ResponseResult selectPage(TransferQueryDTO transferQueryDTO) {
        Page<Transfer> page = toPage ( transferQueryDTO );
        String employeeId = transferQueryDTO.getEmployeeId ();
        String state = transferQueryDTO.getState ();
        // 用户数据隔离
        Integer userEmpId = transferQueryDTO.getUserEmpId ();
        Integer userRole = transferQueryDTO.getUserRole ();
        try {
            // 模糊查询
            LambdaQueryWrapper<Transfer> wrapper = Wrappers.lambdaQuery ( Transfer.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Transfer::getEmployeeId, employeeId )
                    .like ( StringUtils.isNotBlank ( state ), Transfer::getState, state );
            // 数据隔离
            if (userRole.equals ( UserContants.ROLE_USER )) {
                // 如果为普通用户，则查询自己数据
                wrapper.eq ( userEmpId != null, Transfer::getEmployeeId, userEmpId );
            } else {
                Employee employee = employeeDao.selectById ( userEmpId );
                Integer departmentId = employee.getDepartmentId ();
                // 如果不是人事处
                if (!departmentId.equals ( EmployeeConstants.DEPARTMENT_PERSONAL_ID )) {
                    // 获取调出部门和调入部门的数据
                    wrapper.eq ( userEmpId != null, Transfer::getBeforeDepartment, departmentId )
                            .or ()
                            .eq ( userEmpId != null, Transfer::getAfterDepartment, departmentId );
                }
            }
            // 调岗审核只保留状态为审核中的记录
            // wrapper.eq ( Transfer::getState, SystemConstants.SYSTEM_STATUS_NONE );
            Page<Transfer> transferPage = this.page ( page, wrapper );
            IPage<TransferVo> transferVoIPage = EntityUtils.toPage ( transferPage, TransferVo::new );
            // 属性注入，员工姓名，调出部门，调入部门
            this.tableFiled ( transferVoIPage.getRecords () );
            return ResponseResult.okResult ( transferVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResponseResult transferListPage(TransferQueryDTO transferQueryDTO) {
        Page<Transfer> page = toPage ( transferQueryDTO );
        String employeeId = transferQueryDTO.getEmployeeId ();
        try {
            // 模糊查询
            LambdaQueryWrapper<Transfer> wrapper = Wrappers.lambdaQuery ( Transfer.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Transfer::getEmployeeId, employeeId )
                    .eq ( Transfer::getDeclareType, 3 );
            Page<Transfer> transferPage = this.page ( page, wrapper );
            IPage<TransferVo> transferVoIPage = EntityUtils.toPage ( transferPage, TransferVo::new );
            // 属性注入，员工姓名，调出部门，调入部门
            this.tableFiled ( transferVoIPage.getRecords () );
            return ResponseResult.okResult ( transferVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResponseResult test() {
        return ResponseResult.okResult ( transferDao.selectById ( 17 ) );
    }

    @Override
    public Transfer getById(Integer id) {
        return transferDao.selectById ( id );
    }

    /**
     * 属性注入
     *
     * @param list
     */
    private <T extends Transfer> void tableFiled(List<T> list) {
        list.forEach ( e -> {
            Department startDepartment = departmentDao.getById ( e.getBeforeDepartment () );
            Department endDepartment = departmentDao.getById ( e.getAfterDepartment () );
            Employee employee = employeeDao.getEmployeeById ( e.getEmployeeId () );
            List<TransferItem> itemList = itemDao.getItemsByTransferId ( e.getId () );
            e.setStartDepartment ( startDepartment )
                    .setEndDepartment ( endDepartment )
                    .setEmployee ( employee )
                    .setItemList ( itemList );
        } );
    }
}

