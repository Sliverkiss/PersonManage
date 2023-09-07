package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.sliverkiss.dao.AssessApprovalDao;
import io.github.sliverkiss.dao.DepartmentDao;
import io.github.sliverkiss.dao.EmployeeDao;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.domain.entity.Department;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.assess.AssessApproval;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.service.AssessApprovalService;
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

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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
    @Autowired
    private AssessApprovalDao approvalDao;
    @Autowired
    private AssessApprovalService approvalService;

    /**
     * 员工基本信息统计图表
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    public List<Map<String, Object>> personalPie(String column) {
        String sql = column + " as name,count(*) as value";
        QueryWrapper<Personal> wrapper = new QueryWrapper<> ();
        // 根据传入列column进行分组，返回map{name:xxx,value:xxx}
        wrapper.groupBy ( column ).select ( sql );
        List<Map<String, Object>> result = personalDao.selectMaps ( wrapper );
        return result;
    }

    /**
     * 员工工作信息数据统计
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    public List<Map<String, Object>> EmployeePie(String column) {
        String sql = column + " as name,count(*) as value";
        QueryWrapper<Employee> wrapper = new QueryWrapper<> ();
        // 根据传入列column进行分组，返回map{name:xxx,value:xxx}
        wrapper.groupBy ( column ).select ( sql );
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

    public List<Map<String, Object>> assessPointBar(Integer assessId) {
        List<AssessApproval> approvalList = approvalService.getList ();
        List<AssessDeclare> declareList = approvalList.stream ().map ( AssessApproval::getDeclare ).collect ( toList () );
        System.out.println ( declareList );
        // 过滤并聚合数组，只统计审核通过的绩效审核记录
        Integer finalAssessId = assessId;
        Map<String, List<AssessApproval>> map = approvalList.stream ()
                .filter ( e -> e.getStatus ().equals ( "审核通过" ) )
                .filter ( e -> e.getDeclare ().getAssessId ().equals ( assessId ) )
                .collect ( groupingBy ( e -> e.getEmployee ().getDepartment ().getDepartmentName () ) );
        // 结果返回
        List<Map<String, Object>> list = new ArrayList<> ();
        // 对每个部门进行平均分统计
        for (Map.Entry entry : map.entrySet ()) {
            Map<String, Object> resMap = new HashMap<> ();
            Object key = entry.getKey ();
            // 获取积分集合
            List<AssessApproval> values = (List<AssessApproval>) entry.getValue ();
            Double avgScore = values.stream ().mapToInt ( AssessApproval::getScore ).summaryStatistics ().getAverage ();
            // 设置返回结果格式{name:XXX,value:XXX}
            resMap.put ( "name", key );
            resMap.put ( "value", avgScore );
            list.add ( resMap );
        }
        // 没有平均分的部门默认设置为零
        List<Department> departmentList = departmentService.list ();
        for (Department department : departmentList) {
            if (map.get ( department.getDepartmentName () ) == null) {
                Map<String, Object> resMap = new HashMap<> ();
                resMap.put ( "name", department.getDepartmentName () );
                resMap.put ( "value", 0.0 );
                list.add ( resMap );
            }
        }
        return list;
    }

}
