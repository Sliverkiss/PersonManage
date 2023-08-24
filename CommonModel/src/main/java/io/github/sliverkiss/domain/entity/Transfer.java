package io.github.sliverkiss.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("department_transfer")
public class Transfer extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工编号
    private Integer employeeId;
    // 调出部门
    private Integer beforeDepartment;
    // 调入部门
    private Integer afterDepartment;
    // 调动岗位
    private String transferPost;
    // 调动理由
    private String reason;
    // 调动类型
    private String transferType;
    // 调动种类
    private String kind;
    // 申请日期
    private String applyDate;
    // 调出部门意见
    private String beforeComment;
    // 调出部门结果
    private String beforeState;
    // 调出部门审核日期
    private String beforeDate;
    // 调入部门意见
    private String afterComment;
    // 调入部门结果
    private String afterState;
    // 调入部门审核日期
    private String afterDate;
    // 人事处意见
    private String personalComment;
    // 人事处结果
    private String personalState;
    // 人事处审核日期
    private String personalDate;
    // 总审核状态
    private String state;

    @TableField(exist = false)
    private Integer transferRole;// 调岗审核权限，1为调出部门，2为调入部门，3为人事处，0为普通员工

    public Transfer(Transfer transfer) {
        Optional.ofNullable ( transfer ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.beforeDepartment = e.getBeforeDepartment ();
            this.afterDepartment = e.getAfterDepartment ();
            this.transferPost = e.getTransferPost ();
            this.reason = e.getReason ();
            this.transferType = e.getTransferType ();
            this.kind = e.getKind ();
            this.applyDate = e.getApplyDate ();
            this.beforeComment = e.getBeforeComment ();
            this.beforeState = e.getBeforeState ();
            this.beforeDate = e.getBeforeDate ();
            this.afterComment = e.getAfterComment ();
            this.afterState = e.getAfterState ();
            this.afterDate = e.getAfterDate ();
            this.personalComment = getPersonalComment ();
            this.personalState = getPersonalState ();
            this.personalDate = e.getPersonalDate ();
            this.state = e.getState ();
            this.transferRole = e.getTransferRole ();
        } );
    }
}

