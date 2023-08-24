package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/19
 */
@Data
public class AssessDeclareQueryDTO extends Page {
    private String title;
    private String status;
}
