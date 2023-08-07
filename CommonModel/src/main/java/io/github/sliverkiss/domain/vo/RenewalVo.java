package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Renewal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

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
    // 姓名
    private String name;
    // 员工续约列表
    private String departmentName;
    // 合同终止日期
    private String endContract;
    // 员工合同列表，一对多关系
    private List<Renewal> renewalList;

    public RenewalVo(Renewal renewal) {
        super ( renewal );
    }
}
