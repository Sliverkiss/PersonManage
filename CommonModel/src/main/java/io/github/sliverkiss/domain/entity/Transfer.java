package io.github.sliverkiss.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * 调岗申请表(Transfer)表实体类
 *
 * @author tistzach
 * @since 2023-07-27 15:00:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("transfer")
public class Transfer extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工编号
    private Integer employeeId;
    // 调出部门
    private Integer beforeDepartment;
    // 调入部门
    private Integer afterDepartment;
    // 调动理由
    private String reason;
    // 调动类型
    private String type;
    // 调动种类
    private String kind;
    // 申请日期
    private String applyDate;
    // 调出部门意见
    private String beforeComment;
    // 调入部门意见
    private String afterComment;
    // 人事处意见
    private String personnalComment;
    // 审核状态
    private String state;

    public Transfer(Transfer transfer) {
        Optional.ofNullable ( transfer ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.beforeDepartment = e.getBeforeDepartment ();
            this.afterDepartment = e.getAfterDepartment ();
            this.reason = e.getReason ();
            this.kind = e.getKind ();
            this.applyDate = e.getApplyDate ();
            this.beforeComment = e.getBeforeComment ();
            this.afterComment = e.getAfterComment ();
            this.personnalComment = getPersonnalComment ();
            this.state = e.getState ();
        } );
    }
}

