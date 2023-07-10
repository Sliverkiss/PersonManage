package io.github.sliverkiss.domain.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeQueryDTO extends Page {
    private String employeeId;
    private String name;
    private Integer DepartmentId;
    private String post;
    private String hireDate;

}
