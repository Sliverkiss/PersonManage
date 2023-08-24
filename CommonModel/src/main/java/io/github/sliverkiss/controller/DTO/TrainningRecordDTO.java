package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@Data
public class TrainningRecordDTO extends Page {
    private String planName;
    private String planState;
    private String employeeId;
}
