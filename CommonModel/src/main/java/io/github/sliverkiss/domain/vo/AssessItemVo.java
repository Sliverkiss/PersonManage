package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.assess.AssessItem;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@Data
public class AssessItemVo extends AssessItem {

    public AssessItemVo(AssessItem assessItem) {
        super ( assessItem );
    }
}
