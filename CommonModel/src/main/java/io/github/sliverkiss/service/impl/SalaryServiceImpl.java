package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.SalaryDao;
import io.github.sliverkiss.domain.DTO.SalaryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Salary;
import io.github.sliverkiss.domain.vo.SalaryVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.SalaryService;
import io.github.sliverkiss.utils.ArrayUtils;
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
    @Resource
    private ArrayUtils arrayUtils;

    @Override
    public ResponseResult selectSalaryPage(SalaryDTO salaryDTO) {

        Integer currentPage = salaryDTO.getCurrentPage ();
        Integer pageSize = salaryDTO.getPageSize ();
        String name = salaryDTO.getName ();
        String employeeId = salaryDTO.getEmployeeId ();
        String departmentId = salaryDTO.getDepartmentId ();
        String salaryDate = salaryDTO.getSalaryDate ();
        String status = salaryDTO.getStatus ();

        if (currentPage == null || pageSize <= 0 || pageSize == null || pageSize < 1) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
        Page<Salary> page = new Page<> ( currentPage, pageSize );
        try {
            LambdaQueryWrapper<Salary> salaryWrapper = Wrappers.lambdaQuery ( Salary.class );
            Page<Salary> salaryPage = this.page ( page, salaryWrapper );
            IPage<SalaryVo> salaryVoIPage = EntityUtils.toPage ( salaryPage, SalaryVo::new );
            // 注入属性

        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    /**
     * 工资表连接员工工作表，注入员工姓名和部门名称属性
     *
     * @param salaryVoIPage 工资vo ipage
     */
    public void salaryInnerJoinPersonal(IPage<SalaryVo> salaryVoIPage) {
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

