package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Indicator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class IndicatorVo extends Indicator {

    private String departmentName;

    public IndicatorVo(Indicator indicator) {
        super ( indicator );
    }

}
