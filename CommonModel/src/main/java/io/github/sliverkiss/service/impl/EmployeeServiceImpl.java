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
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 员工表(Employee)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-05 11:45:06
 */
@Service("employeeService")
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
        try{
            Page<Employee> page = new Page<> ( 1, 5 );
            Page<Employee> employeePage = this.page ( page );
            IPage<EmployeeVo> employeeVoIPage = EntityUtils.toPage ( employeePage, EmployeeVo::new );
            List<Integer> personalIds = EntityUtils.toList ( employeeVoIPage.getRecords (), EmployeeVo::getPersonalId );
            if (personalIds.size () > 0) {
                LambdaQueryWrapper<Personal> wrapper = Wrappers.lambdaQuery ( Personal.class ).in ( Personal::getId, personalIds );
                List<Personal> personalList = personalDao.selectList ( wrapper );
                //将employee填入employeeVo
                employeeVoIPage.getRecords ().forEach ( employeeVo -> {
                    Personal personal = personalList.get ( employeeVo.getPersonalId () - 1 );
                    employeeVo.setName ( personal.getName () )
                            .setGender ( personal.getGender () )
                            .setBirthday ( personal.getBirthday () )
                            .setIdCard ( personal.getIdCard () )
                            .setWedlock ( personal.getWedlock () )
                            .setSpecialty ( personal.getSpecialty () )
                            .setNation ( personal.getNation () )
                            .setNaticePlace ( personal.getNaticePlace () )
                            .setPolitic ( personal.getPolitic () )
                            .setPhone ( personal.getPhone () )
                            .setEmail ( personal.getEmail () )
                            .setAddress ( personal.getAddress () )
                            .setTiptopDegree ( personal.getTiptopDegree () )
                            .setSchool ( personal.getSchool () );
                } );
            }
            Set<Integer> departmentIds=EntityUtils.toSet(employeeVoIPage.getRecords(), EmployeeVo::getDepartmentId);
            if (departmentIds.size() > 0) {
                //将部门名称填入employeeVo
                LambdaQueryWrapper<Department> departmentWrapper = Wrappers.lambdaQuery(Department.class).in(Department::getId, departmentIds);


            }



            return ResponseResult.okResult ( employeeVoIPage );
        }catch (Exception e){
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
        try {
            personalDao.insert ( personal );
            employee.setPersonalId ( personal.getId () )
                    .setWorkState ( "在职" )
                    .setHireDate ( employee.getStartContract () );
            this.save ( employee );
        } catch (Exception e) {
            throw e;
        }
        return ResponseResult.okResult ( AppHttpCodeEnum.SUCCESS );
    }
}

