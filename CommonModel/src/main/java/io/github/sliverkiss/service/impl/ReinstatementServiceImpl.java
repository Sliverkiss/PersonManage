package io.github.sliverkiss.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.ReinstatementQueryDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.ReinstatementDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Reinstatement;
import io.github.sliverkiss.domain.vo.ReinstatementVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.ReinstatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 复职记录表(Reinstatement)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-16 09:24:37
 */
@Service("reinstatementService")
public class ReinstatementServiceImpl extends ServiceImpl<ReinstatementDao, Reinstatement> implements ReinstatementService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseResult selectPage(ReinstatementQueryDTO reinstatementQueryDTO) {
        String employeeId = reinstatementQueryDTO.getEmployeeId ();
        String employeeName = reinstatementQueryDTO.getEmployeeName ();
        // String departmentId =reinstatementQueryDTO.getDepartmentId ();
        String state = reinstatementQueryDTO.getState ();
        // 用户数据隔离
        Integer userEmpId = reinstatementQueryDTO.getUserEmpId ();
        Integer userRole = reinstatementQueryDTO.getUserRole ();
        Page<Reinstatement> page = toPage ( reinstatementQueryDTO );
        try {
            // 查询条件
            // List<Integer> employeeIds=employeeService.getEmployeeIdsByDepartmentId ( departmentId );
            LambdaQueryWrapper<Reinstatement> wrapper = Wrappers.lambdaQuery ( Reinstatement.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Reinstatement::getEmployeeId, employeeId )
                    .eq ( StringUtils.isNotBlank ( state ), Reinstatement::getState, state )
                    .orderByDesc ( Reinstatement::getApplyDate )
                    .orderByDesc ( Reinstatement::getReinDate );
            if (StringUtils.isNotBlank ( employeeName )) {
                List<Integer> employeeIds = employeeService.getEmployeeIdsLikeName ( employeeName );
                wrapper.in ( employeeIds.size () > 0, Reinstatement::getEmployeeId, employeeIds );
            }
            // 数据隔离
            if (userRole.equals ( UserContants.ROLE_USER )) {
                wrapper.eq ( userEmpId != null, Reinstatement::getEmployeeId, userEmpId );
            }
            Page<Reinstatement> resPage = this.page ( page, wrapper );
            IPage<ReinstatementVo> resIPage = EntityUtils.toPage ( resPage, ReinstatementVo::new );
            // 注入属性
            this.getEmployeeNameAndDepartment ( resIPage );
            return ResponseResult.okResult ( resIPage );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }
    }

    public void getEmployeeNameAndDepartment(IPage<ReinstatementVo> resIPage) {
        if (resIPage.getRecords ().size () > 0) {
            List<Integer> employeeIds = resIPage.getRecords ().stream ().map ( Reinstatement::getEmployeeId ).collect ( Collectors.toList () );
            if (!employeeIds.isEmpty ()) {
                Map<Integer, Employee> map = getEmployeeMap ( employeeDao, employeeIds );
                resIPage.getRecords ().forEach ( vo -> {
                    Employee employee = map.get ( vo.getEmployeeId () );
                    Personal personal = personalDao.selectById ( employee.getPersonalId () );
                    Department department = departmentDao.selectById ( employee.getDepartmentId () );
                    vo.setEmployeeName ( personal.getName () ).setDepartmentName ( department.getDepartmentName () );
                } );
            }
        }
    }
}

