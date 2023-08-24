package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.controller.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.RBAC.UserDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.RBAC.User;
import io.github.sliverkiss.domain.vo.ContractVo;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.utils.BeanCopyUtils;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.sliverkiss.utils.CheckUtil.checkInt;
import static io.github.sliverkiss.utils.CheckUtil.checkStr;

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
    @Autowired
    private UserDao userDao;

    /**
     * 员工检索，实现员工信息的多方位检索，包活员工编号、入职日期、部门、岗位和姓名检索
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult selectEmployeePage(EmployeeQueryDTO employeeQueryDTO) {
        // 数据校验
        Integer currentPage = employeeQueryDTO.getCurrentPage ();
        Integer pageSize = employeeQueryDTO.getPageSize ();

        String name = employeeQueryDTO.getName ();
        String employeeId = employeeQueryDTO.getEmployeeId ();
        Integer departmentId = employeeQueryDTO.getDepartmentId ();
        String post = employeeQueryDTO.getPost ();
        String hireDate = employeeQueryDTO.getHireDate ();
        Integer userId = employeeQueryDTO.getUserId ();
        Integer role = employeeQueryDTO.getUserRole ();
        if (currentPage == null || pageSize <= 0 || pageSize == null || pageSize < 1) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
        Page<Employee> page = new Page<> ( currentPage, pageSize );
        // 如果员工名不为空，则按姓名模糊查询
        List<Integer> personalIds = personalDao.selectList ( Wrappers.lambdaQuery ( Personal.class )
                        .like ( StringUtils.isNotBlank ( name ), Personal::getName, name ) )
                .stream ().map ( Personal::getId ).collect ( Collectors.toList () );
        // 获取查询到的部门信息
        List<Department> departmentList = departmentDao
                .selectList ( Wrappers.lambdaQuery ( Department.class )
                        .in ( Department::getId, departmentId ) );

        // 数据校检,防止产生报错
        if (checkStr ( name, personalIds ) || checkInt ( departmentId, departmentList )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.FIND_NOT_FOUND );
        } else {
            // 如果 员工编号不为空，则按员工编号模糊查询
            try {
                LambdaQueryWrapper<Employee> employeeWrapper = Wrappers.lambdaQuery ( Employee.class )
                        .like ( StringUtils.isNotBlank ( employeeId ), Employee::getId, employeeId )
                        .like ( StringUtils.isNotBlank ( post ), Employee::getPost, post )
                        .in ( personalIds.size () > 0, Employee::getPersonalId, personalIds )
                        .eq ( departmentList.size () > 0, Employee::getDepartmentId, departmentId )
                        .like ( StringUtils.isNotBlank ( hireDate ), Employee::getId, hireDate );
                // 判断用户角色，筛选信息
                if (role.equals ( UserContants.ROLE_USER )) {
                    User user = userDao.selectById ( userId );
                    Employee employee = this.getById ( user.getEmployeeId () );
                    if (employee == null) {
                        throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
                    }
                    employeeWrapper.eq ( Employee::getId, employee.getId () );
                }
                // 将 条件查询结果用page包装起来
                Page<Employee> employeePage = this.page ( page, employeeWrapper );
                IPage<EmployeeVo> employeeVoIPage = EntityUtils.toPage ( employeePage, EmployeeVo::new );
                // 分页模糊查询
                // 注入personal属性
                this.employeeInnerJoinPersonal ( employeeVoIPage );
                // 注入department属性
                this.employeeInnerJoinDepartment ( employeeVoIPage );
                return ResponseResult.okResult ( employeeVoIPage );
            } catch (SystemException e) {
                throw new SystemException ( AppHttpCodeEnum.FIND_NOT_FOUND );
            }
        }
    }

    /**
     * 员工工作表连接员工信息表查询
     *
     * @param employeeVoIPage 员工资料视图
     */
    public void employeeInnerJoinPersonal(IPage<EmployeeVo> employeeVoIPage) {
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
    }

    /**
     * 员工工作表连接部门表查询
     *
     * @param employeeVoIPage 员工资料视图
     */
    public void employeeInnerJoinDepartment(IPage<EmployeeVo> employeeVoIPage) {
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
    }

    /**
     * 获取员工信息
     *
     * @param employeeId 雇员id
     *
     * @return {@link EmployeeVo}
     */
    @Override
    public EmployeeVo getEmployeeVo(Integer employeeId) {
        Employee employee = this.getById ( employeeId );
        Personal personal = personalDao.selectById ( employee.getPersonalId () );
        EmployeeVo employeeVo = EntityUtils.toObj ( employee, EmployeeVo::new );
        Optional.ofNullable ( personal ).ifPresent ( e -> employeeVo.addPersonalInfo ( personal ) );
        return employeeVo;
    }

    /**
     * 入职登记
     *
     * @param employeeVo 员工视图
     *
     * @return {@link ResponseResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult saveEmployee(EmployeeVo employeeVo) {
        // 将视图分解成对应实体类
        Employee employee = BeanCopyUtils.copyBean ( employeeVo, Employee.class );
        Personal personal = BeanCopyUtils.copyBean ( employeeVo, Personal.class );
        String departmentName = employeeVo.getDepartmentName ();
        Department department = departmentDao.selectOne ( Wrappers.lambdaQuery ( Department.class ).eq ( Department::getDepartmentName, departmentName ) );
        try {
            personalDao.insert ( personal );
            // 计算合同截止日期
            Optional.ofNullable ( employee ).ifPresent ( e -> {
                e.setEndContract ( DateUtil.endContract ( e.getStartContract (), e.getContractTerm () ) );
            } );
            employee.setPersonalId ( personal.getId () )
                    .setDepartmentId ( department.getId () )
                    .setWorkState ( "在职" )
                    .setHireDate ( employee.getStartContract () );
            this.save ( employee );
        } catch (Exception e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
        }
        return ResponseResult.okResult ( AppHttpCodeEnum.SUCCESS );
    }

    /**
     * 更新员工资料信息
     *
     * @param employeeVo 员工签证官
     *
     * @return {@link ResponseResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateEmployee(EmployeeVo employeeVo) {
        Employee employee = BeanCopyUtils.copyBean ( employeeVo, Employee.class );
        Personal personal = BeanCopyUtils.copyBean ( employeeVo, Personal.class );
        Department department = departmentDao.selectOne ( Wrappers.<Department>lambdaQuery ().eq ( Department::getDepartmentName, employeeVo.getDepartmentName () ) );
        employee.setDepartmentId ( department.getId () );
        // 计算合同截止日期
        Optional.ofNullable ( employee ).ifPresent ( e -> {
            e.setDepartmentId ( department.getId () )
                    .setEndContract ( DateUtil.endContract ( e.getStartContract (), e.getContractTerm () ) );
        } );
        try {
            personalDao.update ( personal, Wrappers.lambdaQuery ( Personal.class ).eq ( Personal::getId, employee.getPersonalId () ) );
            this.update ( employee, Wrappers.lambdaQuery ( Employee.class ).eq ( Employee::getId, employee.getId () ) );
        } catch (SystemException e) {
            throw new SystemException ( AppHttpCodeEnum.SYSTEM_ERROR );
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
        if (id != null) {
            Employee employee = this.getById ( id );
            Personal personal = personalDao.selectById ( employee.getPersonalId () );
            try {
                // 删除员工基本数据
                personalDao.deleteById ( personal.getId () );
                // 删除员工工作信息
                this.removeById ( id );
                return ResponseResult.okResult ( AppHttpCodeEnum.SUCCESS );
            } catch (Exception e) {
                log.error ( "[员工信息删除失败]{}", e );
                return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR );
            }
        } else {
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

    @Override
    public List<EmployeeVo> getEmployeeVoList() {
        EmployeeQueryDTO employeeQueryDTO = new EmployeeQueryDTO ();
        employeeQueryDTO.setCurrentPage ( 1 );
        employeeQueryDTO.setPageSize ( 100 );
        IPage<EmployeeVo> page = (IPage<EmployeeVo>) this.selectEmployeePage ( employeeQueryDTO ).getData ();
        List<EmployeeVo> list = page.getRecords ();
        return list;
    }

    @Override
    public List<Integer> getEmployeeIdsLikeName(String employeeName) {
        if (StringUtils.isBlank ( employeeName )) {
            return null;
        }
        try {
            List<Integer> personalIds = personalDao.selectList (
                            Wrappers.lambdaQuery ( Personal.class ).like ( Personal::getName, employeeName ) )
                    .stream ().map ( Personal::getId ).collect ( Collectors.toList () );
            List<Integer> employeeIds = this.list ( Wrappers.lambdaQuery ( Employee.class )
                    .in ( Employee::getPersonalId, personalIds ) ).stream ().map ( Employee::getId ).collect ( Collectors.toList () );
            return employeeIds;
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

    /**
     * 根据部门id查询员工id集合
     *
     * @param departmentId 部门id
     *
     * @return {@link List}<{@link Integer}>
     */
    @Override
    public List<Integer> getEmployeeIdsByDepartmentId(String departmentId) {
        return this.list ( Wrappers.lambdaQuery ( Employee.class ).eq ( Employee::getDepartmentId, departmentId ) )
                .stream ().map ( Employee::getId ).collect ( Collectors.toList () );
    }

}

