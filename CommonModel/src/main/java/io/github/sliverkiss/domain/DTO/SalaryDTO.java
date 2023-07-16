package io.github.sliverkiss.domain.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SalaryDTO extends Page {
    private String employeeId;// 员工编号
    private String departmentId;
    private String name;// 员工姓名
    private String salaryDate;// 薪资发放年月
    private String status;// 发放状态：通过，未通过
}
