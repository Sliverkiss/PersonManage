package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DepartmentVo extends Department {

    // 上级部门
    private Department parentDepartment;
    // 部门下员工名单
    private List<EmployeeVo> employeeVoList;

    // 岗位列表
    private List<String> postList;

    public DepartmentVo(Department department) {
        super ( department );
    }
}
