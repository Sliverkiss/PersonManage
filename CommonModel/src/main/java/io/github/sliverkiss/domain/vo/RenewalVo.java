package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Renewal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RenewalVo extends Renewal {

    //姓名
    private String name;

    private String departmentName;

    public RenewalVo(Renewal renewal) {
      super(renewal);
    }
}
