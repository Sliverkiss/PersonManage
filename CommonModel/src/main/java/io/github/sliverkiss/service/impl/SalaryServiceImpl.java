package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.SalaryQueryDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.RBAC.UserDao;
import io.github.sliverkiss.dao.SalaryDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.RBAC.User;
import io.github.sliverkiss.domain.entity.Salary;
import io.github.sliverkiss.domain.vo.SalaryVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 工资表(Salary)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-16 08:21:39
 */
@Service("salaryService")
public class SalaryServiceImpl extends ServiceImpl<SalaryDao, Salary> implements SalaryService {
    @Resource
    private PersonalDao personalDao;
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private DepartmentDao departmentDao;
    @Autowired
    private UserDao userDao;

    @Override
    public ResponseResult selectSalaryPage(SalaryQueryDTO salaryQueryDTO) {
        Page<Salary> page = toPage ( salaryQueryDTO );
        String employeeId = salaryQueryDTO.getEmployeeId ();
        String salaryDate = salaryQueryDTO.getSalaryDate ();
        String status = salaryQueryDTO.getStatus ();
        Integer userId = salaryQueryDTO.getUserId ();
        Integer role = salaryQueryDTO.getRole ();
        try {
            // 模糊查询
            LambdaQueryWrapper<Salary> salaryWrapper = Wrappers.lambdaQuery ( Salary.class )
                    .like ( StringUtils.isNotBlank ( employeeId ), Salary::getEmployeeId, employeeId )
                    .like ( StringUtils.isNotBlank ( salaryDate ), Salary::getSalaryDate, salaryDate )
                    .like ( StringUtils.isNotBlank ( status ), Salary::getStatus, status );
            // 判断用户角色，筛选信息
            if (role.equals ( UserContants.ROLE_USER )) {
                User user = userDao.selectById ( userId );
                Employee employee = employeeDao.selectById ( user.getEmployeeId () );
                if (employee == null) {
                    throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
                }
                salaryWrapper.eq ( Salary::getEmployeeId, employee.getId () );
            }
            Page<Salary> salaryPage = this.page ( page, salaryWrapper );
            IPage<SalaryVo> salaryVoIPage = EntityUtils.toPage ( salaryPage, SalaryVo::new );
            // 注入属性
            this.salaryInnerJoinEmployee ( salaryVoIPage );
            return ResponseResult.okResult ( salaryVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 工资表连接员工工作表，注入员工姓名和部门名称属性
     *
     * @param salaryVoIPage 工资vo ipage
     */
    public void salaryInnerJoinEmployee(IPage<SalaryVo> salaryVoIPage) {
        List<Integer> employeeIds = salaryVoIPage.getRecords ().stream ().map ( Salary::getEmployeeId ).collect ( Collectors.toList () );
        if (!employeeIds.isEmpty ()) {
            List<Employee> employeList = employeeDao.selectList ( Wrappers.lambdaQuery ( Employee.class )
                    .in ( Employee::getId, employeeIds ) );
            Map<Integer, Employee> map = EntityUtils.toMap ( employeList, Employee::getId, e -> e );
            salaryVoIPage.getRecords ().forEach ( salaryVo -> {
                Employee employee = map.get ( salaryVo.getEmployeeId () );
                Personal personal = personalDao.selectById ( employee.getPersonalId () );
                Department department = departmentDao.selectById ( employee.getDepartmentId () );
                Optional.ofNullable ( department ).ifPresent ( e -> salaryVo.setDepartmentName ( e.getDepartmentName () ) );
                Optional.ofNullable ( personal ).ifPresent ( e -> salaryVo.setName ( e.getName () ) );
            } );
        }
    }

}

