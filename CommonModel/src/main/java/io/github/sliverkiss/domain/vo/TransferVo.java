package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransferVo extends Transfer {

    private String EmployeeName;// 员工姓名
    private String beforeDepartmentName;// 调出部门名称
    private String afterDepartmentName;// 调入部门名称

    public TransferVo(Transfer transfer) {
        super ( transfer );
    }
}
