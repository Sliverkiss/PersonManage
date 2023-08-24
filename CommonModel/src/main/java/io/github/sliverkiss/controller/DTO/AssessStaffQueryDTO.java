package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@Data
public class AssessStaffQueryDTO extends Page {
    private String title;
}
