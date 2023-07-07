package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 合同视图
 *
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ContractVo extends Employee {
    // 姓名
    private String name;
    // 部门名称
    private String departmentName;

    public ContractVo(Employee employee) {
        super ( employee );
    }
}
