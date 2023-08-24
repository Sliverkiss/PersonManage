package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/16
 */
@Data
public class ReinstatementQueryDTO extends Page {
    private String employeeId;
    private String employeeName;
    private String departmentId;
    private String state;

}
