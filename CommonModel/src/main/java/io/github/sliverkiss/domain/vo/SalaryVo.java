package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
public class SalaryVo extends Salary {

    // 姓名
    private String name;

    private String departmentName;

    public SalaryVo(Salary salary) {
        super ( salary );
    }
}
