package io.github.sliverkiss.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.AssessStaffQueryDTO;
import io.github.sliverkiss.dao.*;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.domain.entity.assess.AssessSet;
import io.github.sliverkiss.domain.entity.assess.AssessStaff;
import io.github.sliverkiss.domain.vo.AssessStaffVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.AssessStaffService;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 考核名单(AssessStaff)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-17 16:39:46
 */
@Service("assessStaffService")
@Slf4j
public class AssessStaffServiceImpl extends ServiceImpl<AssessStaffDao, AssessStaff> implements AssessStaffService {

    @Autowired
    private AssessSetDao assessSetDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private AssessSetDao setDao;
    @Autowired
    private AssessDeclareDao declareDao;

    @Override
    public ResponseResult selectPage(AssessStaffQueryDTO dto) {
        Page<AssessStaff> page = toPage ( dto );
        String title = dto.getTitle ();
        try {
            // 模糊查询
            LambdaQueryWrapper<AssessStaff> wrapper = Wrappers.lambdaQuery ( AssessStaff.class );
            // 若标题不为空，则模糊查询
            if (StringUtils.isNotBlank ( title )) {
                List<Integer> assessSetIds = assessSetDao.selectList ( Wrappers.lambdaQuery ( AssessSet.class )
                        .like ( AssessSet::getTitle, title ) ).stream ().map ( AssessSet::getId ).collect ( Collectors.toList () );
                System.out.println ( assessSetIds );
                if (assessSetIds.size () > 0) {
                    wrapper.in ( AssessStaff::getAssessId, assessSetIds );
                } else {
                    return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
                }
            }

            // 数据隔离
            Page<AssessStaff> tPage = this.page ( page, wrapper );
            IPage<AssessStaffVo> voIPage = EntityUtils.toPage ( tPage, AssessStaffVo::new );
            // 属性注入
            this.setTableFiledList ( voIPage );
            return ResponseResult.okResult ( voIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<AssessStaff> getByAssessId(Integer assessId, Integer employeeId) {
        List<AssessStaff> list = list ( Wrappers.lambdaQuery ( AssessStaff.class )
                .eq ( AssessStaff::getEmployeeId, employeeId )
                .eq ( AssessStaff::getAssessId, assessId ) );
        this.setTableFiledList ( list );
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult saveStaffAndDeclare(AssessStaff staff) {
        log.warn ( "开始" );
        staff.setCreateDate ( DateUtil.currentDateFormat () );
        Integer deptId = staff.getDeptId ();
        System.out.println ( staff );
        log.warn ( "结束" );
        // 1.获取部门下所有员工
        List<Employee> employeeList = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                .in ( Employee::getDepartmentId, deptId ) );
        Integer assessId = staff.getAssessId ();
        for (Employee employee : employeeList) {
            Integer employeeId = employee.getId ();
            List<AssessStaff> list = this.getByAssessId ( assessId, employeeId );
            if (list.size () > 0) {
                AssessStaff s = list.get ( 0 );
                String type = s.getType ();
                if (type.equals ( SystemConstants.ASSESSSTAF_TYPE_REVIEWED )) {
                    continue;
                }
            }
            staff.setEmployeeId ( employeeId );
            this.saveOrUpdate ( staff );
            this.saveDeclare ( staff.getId (), assessId, employeeId );
        }
        return ResponseResult.okResult ();
    }

    /**
     * 添加个人绩效申报信息
     *
     * @param staffId    员工id
     * @param assessId   评估id
     * @param employeeId 雇员id
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDeclare(Integer staffId, Integer assessId, Integer employeeId) {
        try {
            AssessStaff assessStaff = this.getById ( staffId );
            // 2.个人绩效申报增加记录
            AssessDeclare assessDeclare = new AssessDeclare ();
            assessDeclare.setAssessId ( assessId ).setCreateDate ( DateUtil.currentDateFormat () )
                    .setEmployeeId ( employeeId ).setApprovalScore ( 0 ).setScore ( 0 );
            String type = assessStaff.getType ();
            if (type.equals ( SystemConstants.ASSESSSTAF_TYPE_REVIEWED )) {
                assessDeclare.setStatus ( "未申报" );
            }
            declareDao.insert ( assessDeclare );
        } catch (Exception e) {
            throw new RuntimeException ( "添加个人绩效失败！" );
        }

    }

    /**
     * 注入属性
     *
     * @param voIPage 签证官ipage
     */
    public void setTableFiledList(IPage<AssessStaffVo> voIPage) {
        voIPage.getRecords ().forEach ( vo -> {
            this.setTableFiled ( vo );
        } );
    }

    /**
     * 注入属性
     *
     * @param list 列表
     */
    public void setTableFiledList(List<AssessStaff> list) {
        list.forEach ( vo -> {
            this.setTableFiled ( vo );
        } );
    }

    public <T extends AssessStaff> void setTableFiled(T staff) {
        Employee employee = employeeDao.selectById ( staff.getEmployeeId () );
        Personal personal = personalDao.selectById ( employee.getPersonalId () );
        Department department = departmentDao.selectById ( employee.getDepartmentId () );
        AssessSet assessSet = setDao.selectById ( staff.getAssessId () );
        Optional.ofNullable ( personal ).ifPresent ( e -> staff.setEmployeeName ( e.getName () ) );
        Optional.ofNullable ( assessSet ).ifPresent ( e -> staff.setAssessTitle ( e.getTitle () ) );
        Optional.ofNullable ( department ).ifPresent ( e -> {
            staff.setDeptId ( e.getId () ).setDeptName ( e.getDepartmentName () );
        } );
    }
}

