package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.assess.AssessSet;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */

@Data
public class AssessSetVo extends AssessSet {
    public AssessSetVo(AssessSet assessSet) {
        super ( assessSet );
    }
}
