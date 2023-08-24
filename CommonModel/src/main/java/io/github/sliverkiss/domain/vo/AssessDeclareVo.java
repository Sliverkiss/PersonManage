package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/19
 */
@Data
public class AssessDeclareVo extends AssessDeclare {
    public AssessDeclareVo(AssessDeclare declare) {
        super ( declare );
    }
}
