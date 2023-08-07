package io.github.sliverkiss.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/**
 * 离职记录表(Resignation)表实体类
 *
 * @author tistzach
 * @since 2023-08-03 16:36:38
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("resignation")
public class Resignation extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工编号
    private Integer employeeId;
    // 离职类型
    private String kind;
    // 离职原因
    private String reason;
    // 部门意见
    private String departmentComment;
    // 申请日期
    private Date applyDate;
    // 审核日期
    private Date reviewDate;
    // 审核人
    private String director;
    // 审核状态
    private String state;

    public Resignation(Resignation resignation) {
        Optional.ofNullable ( resignation ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.kind = e.getKind ();
            this.reason = e.getReason ();
            this.departmentComment = e.getDepartmentComment ();
            this.applyDate = e.getApplyDate ();
            this.reviewDate = e.getReviewDate ();
            this.director = e.getDirector ();
            this.state = e.getState ();
        } );
    }
}
