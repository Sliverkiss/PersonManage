package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.assess.AssessStaff;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@Data
public class AssessStaffVo extends AssessStaff {

    public AssessStaffVo(AssessStaff entity) {
        super ( entity );
    }
}
