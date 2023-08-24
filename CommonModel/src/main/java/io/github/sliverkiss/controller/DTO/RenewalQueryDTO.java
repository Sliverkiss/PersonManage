package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RenewalQueryDTO extends Page {
    private String employeeId;//员工编号
    private String departmentId;
    private String name;//员工姓名
    private String status;//审核状态：通过，未通过
}
