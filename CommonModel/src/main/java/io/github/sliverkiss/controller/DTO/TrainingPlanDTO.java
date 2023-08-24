package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@Data
@Accessors(chain = true)
public class TrainingPlanDTO extends Page {
    private String planName;
    private String status;
}
