package io.github.sliverkiss.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.ResignationDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.ResignationDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Resignation;
import io.github.sliverkiss.domain.vo.ResignationVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.ResignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 离职记录表(Resignation)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-03 16:36:38
 */
@Service("resignationService")
public class ResignationServiceImpl extends ServiceImpl<ResignationDao, Resignation> implements ResignationService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeService employeeService;


    /**
     * 多条件分页模糊查询
     *
     * @param resignationDTO 辞职dto
     *
     * @return {@link ResponseResult}
     */
    public ResponseResult selectPage(ResignationDTO resignationDTO) {
        String employeeId = resignationDTO.getEmployeeId ();
        String employeeName = resignationDTO.getEmployeeName ();
        String state = resignationDTO.getState ();
        // 用户数据隔离
        Integer userEmpId = resignationDTO.getUserEmpId ();
        Integer userRole = resignationDTO.getUserRole ();
        Page<Resignation> page = toPage ( resignationDTO );
        try {
            // 查询条件
            LambdaQueryWrapper<Resignation> wrapper = Wrappers.lambdaQuery ( Resignation.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Resignation::getEmployeeId, employeeId )
                    .eq ( StringUtils.isNotBlank ( state ), Resignation::getState, state )
                    .orderByDesc ( Resignation::getReviewDate )
                    .orderByDesc ( Resignation::getApplyDate );
            // 模糊查询员工姓名
            if (StringUtils.isNotBlank ( employeeName )) {
                List<Integer> employeeIds = employeeService.getEmployeeIdsLikeName ( employeeName );
                System.out.println ( employeeIds );
                if (employeeIds.size () > 0 && employeeName != null) {
                    wrapper.in ( Resignation::getEmployeeId, employeeIds );
                } else {
                    return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
                }
            }
            // 数据隔离
            if (userRole.equals ( UserContants.ROLE_USER )) {
                wrapper.eq ( userEmpId != null, Resignation::getEmployeeId, userEmpId );
            }
            Page<Resignation> resignationPage = this.page ( page, wrapper );
            IPage<ResignationVo> resignationVoIPage = EntityUtils.toPage ( resignationPage, ResignationVo::new );
            // 注入属性
            this.resignationInnerJoinEmployee ( resignationVoIPage );
            return ResponseResult.okResult ( resignationVoIPage );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
        }
    }

    /**
     * 离职记录表连接入职员工表，查询员工姓名、员工部门，并注入属性
     *
     * @param resignationVoIPage 签证官ipage辞职
     */
    public void resignationInnerJoinEmployee(IPage<ResignationVo> resignationVoIPage) {
        if (resignationVoIPage.getRecords ().size () > 0) {
            List<Integer> employeeIds = resignationVoIPage.getRecords ().stream ().map ( Resignation::getEmployeeId ).collect ( Collectors.toList () );
            if (!employeeIds.isEmpty ()) {
                Map<Integer, Employee> map = getEmployeeMap ( employeeDao, employeeIds );
                resignationVoIPage.getRecords ().forEach ( resignationVo -> {
                    Employee employee = map.get ( resignationVo.getEmployeeId () );
                    Personal personal = personalDao.selectById ( employee.getPersonalId () );
                    Department department = departmentDao.selectById ( employee.getDepartmentId () );
                    resignationVo.setEmployeeName ( personal.getName () )
                            .setDepartmentName ( department.getDepartmentName () );
                } );
            }
        }
    }

}

