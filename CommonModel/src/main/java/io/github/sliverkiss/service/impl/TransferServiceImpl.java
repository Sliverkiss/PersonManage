package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.TransferDao;
import io.github.sliverkiss.domain.DTO.TransferQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Transfer;
import io.github.sliverkiss.domain.vo.TransferVo;
import io.github.sliverkiss.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 调岗申请表(Transfer)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-27 15:00:20
 */
@Service("transferService")
public class TransferServiceImpl extends ServiceImpl<TransferDao, Transfer> implements TransferService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public ResponseResult selectPage(TransferQueryDTO transferQueryDTO) {
        Page<Transfer> page = toPage ( transferQueryDTO );
        String employeeId = transferQueryDTO.getEmployeeId ();
        String employeeName = transferQueryDTO.getEmployeeName ();
        String state = transferQueryDTO.getState ();
        String notState = transferQueryDTO.getNotState ();
        List<Integer> personalIds = personalDao.selectList ( Wrappers.lambdaQuery ( Personal.class )
                        .like ( StringUtils.isNotBlank ( employeeName ), Personal::getName, employeeName ) )
                .stream ().map ( Personal::getId ).collect ( Collectors.toList () );
        List<Integer> employeeIds = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class ).in ( personalIds.size () > 0, Employee::getPersonalId, personalIds ) )
                .stream ().map ( Employee::getId ).collect ( Collectors.toList () );
        try {
            // 模糊查询
            LambdaQueryWrapper<Transfer> wrapper = Wrappers.lambdaQuery ( Transfer.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Transfer::getEmployeeId, employeeId )
                    .like ( StringUtils.isNotBlank ( state ), Transfer::getState, state )
                    .notLike ( StringUtils.isNotBlank ( notState ), Transfer::getState, notState );// 获取数据
            Page<Transfer> transferPage = this.page ( page, wrapper );
            IPage<TransferVo> transferVoIPage = EntityUtils.toPage ( page, TransferVo::new );
            // 属性注入，员工姓名，调出部门，调入部门
            this.transferInnerJoinDepartment ( transferVoIPage );
            this.transferInnerJoinPersonal ( transferVoIPage );
            // 注入员工调岗列表
            this.onePatchTransferList ( transferVoIPage );
            return ResponseResult.okResult ( transferVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 调岗表连接部门表，根据调出部门、调入部门编号查询部门名称
     *
     * @param transferVoIPage 调岗审核视图
     */
    public void transferInnerJoinDepartment(IPage<TransferVo> transferVoIPage) {
        if (transferVoIPage.getRecords ().size () > 0) {
            // 获取部门信息
            Set<Integer> departmentIds = EntityUtils.toSet ( transferVoIPage.getRecords (), Transfer::getBeforeDepartment );
            departmentIds.addAll ( EntityUtils.toSet ( transferVoIPage.getRecords (), Transfer::getAfterDepartment ) );
            if (departmentIds.size () > 0) {
                List<Department> departmentList = departmentDao.selectList ( Wrappers.lambdaQuery ( Department.class ).in ( Department::getId, departmentIds ) );
                Map<Integer, Department> collect = EntityUtils.toMap ( departmentList, Department::getId, e -> e );
                // 注入调出部门与调入部门名称
                transferVoIPage.getRecords ().forEach ( transferVo -> {
                    Department beforeDepartment = collect.get ( transferVo.getBeforeDepartment () );
                    Department afterDepartment = collect.get ( transferVo.getAfterDepartment () );
                    Optional.ofNullable ( beforeDepartment ).ifPresent ( e -> transferVo.setBeforeDepartmentName ( e.getDepartmentName () ) );
                    Optional.ofNullable ( afterDepartment ).ifPresent ( e -> transferVo.setAfterDepartmentName ( e.getDepartmentName () ) );
                } );
            }
        }
    }

    /**
     * 注入员工调岗列表，一对多关系，一个员工拥有多条调岗记录
     *
     * @param transferVoIPage 调岗审核视图
     */
    public void onePatchTransferList(IPage<TransferVo> transferVoIPage) {
        transferVoIPage.getRecords ().forEach ( e -> {
            e.setTransferList ( this.list ( Wrappers.lambdaQuery ( Transfer.class ).in ( Transfer::getEmployeeId, e.getEmployeeId () ).orderByDesc ( Transfer::getApplyDate ) ) );
        } );
    }

    /**
     * 调岗表连接员工信息表，注入员工姓名属性
     *
     * @param transferVoIPage 调岗审核视图
     */
    public void transferInnerJoinPersonal(IPage<TransferVo> transferVoIPage) {
        try {
            // 获取员工信息
            if (transferVoIPage.getRecords ().size () > 0) {
                Set<Integer> employeeIds = transferVoIPage.getRecords ().stream ().map ( Transfer::getEmployeeId ).collect ( Collectors.toSet () );
                List<Integer> personalIds = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class ).in ( Employee::getId, employeeIds ) )
                        .stream ().map ( Employee::getPersonalId ).collect ( Collectors.toList () );
                List<Personal> personalList = personalDao.selectList ( Wrappers.lambdaQuery ( Personal.class ).in ( Personal::getId, personalIds ) );
                Map<Integer, Personal> map = EntityUtils.toMap ( personalList, Personal::getId, e -> e );
                // 注入员工姓名
                transferVoIPage.getRecords ().forEach ( transferVo -> {
                    Employee employee = employeeDao.selectById ( transferVo.getEmployeeId () );
                    Department department = departmentDao.selectById ( employee.getDepartmentId () );
                    Personal personal = map.get ( employee.getPersonalId () );
                    Optional.ofNullable ( personal ).ifPresent ( e -> {
                        // 注入员工姓名
                        transferVo.setEmployeeName ( e.getName () )
                                // 注入部门名称
                                .setDepartmentName ( department.getDepartmentName () );
                    } );
                } );
            }
        } catch (Exception e) {
            throw e;
        }

    }

}

