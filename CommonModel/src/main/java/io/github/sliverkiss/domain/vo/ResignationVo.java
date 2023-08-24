package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Resignation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResignationVo extends Resignation {

    private String employeeName;// 员工姓名
    private String departmentName;// 部门名称

    public ResignationVo(Resignation resignation) {
        super ( resignation );
    }

}
