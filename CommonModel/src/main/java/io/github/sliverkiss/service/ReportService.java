package io.github.sliverkiss.service;

import java.util.List;
import java.util.Map;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/1
 */
public interface ReportService {
    public List<Map<String, Object>> personalPie(String column);

    public List<Map<String, Object>> EmployeePie(String column);

    public List<Map<String, Object>> DepartmentBar();


    public List<Map<String, Object>> assessPointBar(Integer assessId);
}
