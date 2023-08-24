package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DepartmentQueryDTO extends Page {
    private Integer departmentId;
    private String manager;
}
