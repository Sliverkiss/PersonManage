package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Reinstatement;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/16
 */
@Data
@Accessors(chain = true)
public class ReinstatementVo extends Reinstatement {
    private String employeeName;
    private String departmentName;

    public ReinstatementVo(Reinstatement reinstatement) {
        super ( reinstatement );
    }
}
