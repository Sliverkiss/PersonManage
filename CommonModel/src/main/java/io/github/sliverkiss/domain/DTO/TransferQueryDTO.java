package io.github.sliverkiss.domain.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransferQueryDTO extends Page {
    private String employeeId;
    private String employeeName;
    // 审核状态
    private String state;
    private String notState;
}
