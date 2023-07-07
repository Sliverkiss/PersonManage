package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.vo.ContractVo;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 员工表(Employee)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-05 11:45:06
 */
@Service("employeeService")
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
    @Resource
    private PersonalDao personalDao;
    @Resource
    private DepartmentDao departmentDao;

    /**
     * 无筛选条件分页查询员工 列表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult selectEmployeePage() {
        try {
            Page<Employee> page = new Page<> ( 1, 5 );
            Page<Employee> employeePage = this.page ( page );
            IPage<EmployeeVo> employeeVoIPage = EntityUtils.toPage ( employeePage, EmployeeVo::new );
            List<Integer> personalIds = EntityUtils.toList ( employeeVoIPage.getRecords (), EmployeeVo::getPersonalId );
            if (personalIds.size () > 0) {
                LambdaQueryWrapper<Personal> wrapper = Wrappers.lambdaQuery ( Personal.class ).in ( Personal::getId, personalIds );
                List<Personal> personalList = personalDao.selectList ( wrapper );
                Map<Integer, Personal> map = EntityUtils.toMap ( personalList, Personal::getId, e -> e );
                // 将employee填入employeeVo
                employeeVoIPage.getRecords ().forEach ( employeeVo -> {
                    Personal personal = map.get ( employeeVo.getPersonalId () );
                    Optional.ofNullable ( personal ).ifPresent ( e -> employeeVo.addPersonalInfo ( personal ) );
                } );
            }
            Set<Integer> departmentIds = EntityUtils.toSet ( employeeVoIPage.getRecords (), EmployeeVo::getDepartmentId );
            if (departmentIds.size () > 0) {
                // 将部门名称填入employeeVo
                LambdaQueryWrapper<Department> departmentWrapper = Wrappers.lambdaQuery ( Department.class ).in ( Department::getId, departmentIds );
                List<Department> departments = departmentDao.selectList ( departmentWrapper );
                Map<Integer, Department> map = EntityUtils.toMap ( departments, Department::getId, e -> e );
                employeeVoIPage.getRecords ().forEach ( employeeVo -> {
                    Department department = map.get ( employeeVo.getDepartmentId () );
                    employeeVo.setDepartmentName ( department.getDepartmentName () );
                } );
            }
            return ResponseResult.okResult ( employeeVoIPage );
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 入职登记
     *
     * @param employeeVo 员工视图
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult saveEmployee(EmployeeVo employeeVo) {
        // 将试图分解成对应实体类
        Employee employee = BeanCopyUtils.copyBean ( employeeVo, Employee.class );
        Personal personal = BeanCopyUtils.copyBean ( employeeVo, Personal.class );
        String departmentName = employeeVo.getDepartmentName ();
        LambdaQueryWrapper<Department> departmentWrapper = Wrappers.lambdaQuery ( Department.class ).eq ( Department::getDepartmentName, departmentName );
        Department department = departmentDao.selectOne ( departmentWrapper );
        try {
            personalDao.insert ( personal );

            employee.setPersonalId ( personal.getId () )
                    .setDepartmentId ( department.getId () )
                    .setWorkState ( "在职" )
                    .setHireDate ( employee.getStartContract () );
            this.save ( employee );
        } catch (Exception e) {
            throw e;
        }
        return ResponseResult.okResult ( AppHttpCodeEnum.SUCCESS );
    }

    /**
     * 删除员工资料信息
     *
     * @param id id
     *
     * @return {@link ResponseResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteEmployee(Integer id) {
        if ( id!=null){
            Employee employee = this.getById(id);
            Personal personal = personalDao.selectById(employee.getPersonalId());
            try{
                //删除员工基本数据
                personalDao.deleteById(personal.getId());
                //删除员工工作信息
                this.removeById(id);
                return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
            }catch (Exception e) {
                log.error("[员工信息删除失败]{}", e);
                return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR );
            }
        }else {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
    }

    /**
     * 无筛选条件分页查询合同 列表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult selectContractPage() {
        // 查询员工工作信息
        Page<Employee> page = new Page<> ( 1, 5 );
        Page<Employee> employeePage = this.page ( page );
        // 将员工工作列表转化为合同视图
        IPage<ContractVo> contractVoIPage = EntityUtils.toPage ( employeePage, ContractVo::new );
        // 查询员工编号列表
        List<Integer> personalIds = EntityUtils.toList ( contractVoIPage.getRecords (), ContractVo::getPersonalId );
        if (personalIds.size () > 0) {
            LambdaQueryWrapper<Personal> wrapper = Wrappers.lambdaQuery ( Personal.class ).in ( Personal::getId, personalIds );
            List<Personal> personalList = personalDao.selectList ( wrapper );
            Map<Integer, Personal> map = EntityUtils.toMap ( personalList, Personal::getId, e -> e );
            // 将员工姓名填入合同视图
            contractVoIPage.getRecords ().forEach ( contractVo -> {
                Personal personal = map.get ( contractVo.getPersonalId () );
                Optional.ofNullable ( personal ).ifPresent ( e -> contractVo.setName ( e.getName () ) );
            } );
        }
        Set<Integer> departmentIds = EntityUtils.toSet ( contractVoIPage.getRecords (), ContractVo::getDepartmentId );
        if (departmentIds.size () > 0) {
            // 将部门名称填入合同视图
            LambdaQueryWrapper<Department> departmentWrapper = Wrappers.lambdaQuery ( Department.class ).in ( Department::getId, departmentIds );
            List<Department> departments = departmentDao.selectList ( departmentWrapper );
            Map<Integer, Department> map = EntityUtils.toMap ( departments, Department::getId, e -> e );
            contractVoIPage.getRecords ().forEach ( contractVo -> {
                Department department = map.get ( contractVo.getDepartmentId () );
                Optional.ofNullable ( department ).ifPresent ( e -> contractVo.setDepartmentName ( e.getDepartmentName () ) );
            } );
        }
        return ResponseResult.okResult ( contractVoIPage );
    }
}

