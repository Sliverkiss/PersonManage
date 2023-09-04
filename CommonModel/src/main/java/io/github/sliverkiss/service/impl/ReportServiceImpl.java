package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.sliverkiss.constants.EmployeeConstants;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.service.DepartmentService;
import io.github.sliverkiss.service.EmployeeService;
import io.github.sliverkiss.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据报表服务类
 *
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/1
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private PersonalDao personalDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工统计图表
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    public List<Map<String, Object>> personalPie(String column) {
        String sql = column + " as name,count(*) as value";
        QueryWrapper<Personal> wrapper = new QueryWrapper<> ();
        wrapper.groupBy ( column )
                .select ( sql );
        List<Map<String, Object>> result = personalDao.selectMaps ( wrapper );
        return result;
    }

    /**
     * 部门员工分布统计
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    public List<Map<String, Object>> EmployeePie(String column) {
        String sql = column + " as name,count(*) as value";
        QueryWrapper<Employee> wrapper = new QueryWrapper<> ();
        wrapper.groupBy ( column )
                .select ( sql );
        List<Map<String, Object>> result = employeeDao.selectMaps ( wrapper );
        return result;
    }

    /**
     * 部门员工统计
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public List<Map<String, Object>> DepartmentBar() {
        QueryWrapper<Employee> wrapper = new QueryWrapper<> ();
        // 排除离职员工
        wrapper.notIn ( "work_state", "离职" );
        wrapper.groupBy ( "department_id" ).select ( "department_id as name,count(*) as value" );
        List<Map<String, Object>> result = employeeDao.selectMaps ( wrapper );
        // 多表查询统计
        result.forEach ( e -> {
            Integer departmentId = (Integer) e.get ( "name" );
            Department department = departmentDao.selectById ( departmentId );
            e.put ( "name", department.getDepartmentName () );
        } );
        return result;
    }

    @Override
    public List<Map<String, Object>> ResignationBar() {
        // 员工离职率=离职人数/平均在职人数=离职人数/[期初人数+期末人数]
        // 获取所有部门
        List<Department> departmentList = departmentService.list ();
        List<Map<String, Object>> result = new ArrayList<> ();
        departmentList.forEach ( e -> {
            Map<String, Object> map = new HashMap<> ();
            // 部门总人数
            List<Employee> employeeList = employeeService.list ();
            // 平均在职人数
            Integer avagePerson = Math.round ( 20 / departmentList.size () );
            log.warn ( "kaish1" );
            System.out.println ( employeeList.size () );
            System.out.println ( departmentList.size () );
            System.out.println ( avagePerson );
            log.warn ( "结束" );
            // 该部门离职人数
            List<Employee> stallPersonList = employeeList.stream ()
                    .filter ( item -> item.getWorkState ().equals ( EmployeeConstants.WORK_STATE_LEAVES ) )
                    .filter ( item -> item.getDepartmentId ().equals ( e.getId () ) )
                    .collect ( Collectors.toList () );
            // 离职率
            Double count = Double.valueOf ( (Math.round ( stallPersonList.size () / avagePerson ) / 100) );
            map.put ( "name", e.getDepartmentName () );
            map.put ( "value", count );
            result.add ( map );
        } );
        return result;
    }

}
