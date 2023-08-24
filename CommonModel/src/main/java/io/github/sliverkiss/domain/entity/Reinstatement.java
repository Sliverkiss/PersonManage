package io.github.sliverkiss.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Optional;

/**
 * 复职记录表(Reinstatement)表实体类
 *
 * @author tistzach
 * @since 2023-08-16 09:24:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("department_reinstatement")
public class Reinstatement extends BaseEntity implements Serializable {
    @TableId
    private Integer id;

    // 员工编号
    private Integer employeeId;
    // 复职原因
    private String reason;
    // 部门意见
    private String departmentComment;
    // 申请日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String applyDate;
    // 审核日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String reinDate;
    // 审核人
    private String director;
    // 审核状态
    private String state;

    public Reinstatement(Reinstatement reinstatement) {
        Optional.ofNullable ( reinstatement ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.reason = e.getReason ();
            this.departmentComment = e.getDepartmentComment ();
            this.applyDate = e.getApplyDate ();
            this.reinDate = e.getReinDate ();
            this.director = e.getDirector ();
            this.state = e.getState ();
        } );

    }

}
