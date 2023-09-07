package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/4
 */
@Data
public class PostQueryDTO extends Page {
    private String name;
    private String departmentId;
}
