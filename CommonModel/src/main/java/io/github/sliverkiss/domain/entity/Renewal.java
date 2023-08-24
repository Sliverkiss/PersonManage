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
 * 续约申请表(Renewal)表实体类
 *
 * @author tistzach
 * @since 2023-07-07 11:44:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("employee_renewal")
public class Renewal extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工信息编号
    private Integer employeeId;
    // 续约年数
    private Integer renewalAge;
    // 部门意见
    private String departmentComment;
    // 合同起始日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startContract;
    // 合同终止日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endContract;
    // 申请日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String applyDate;
    // 审核日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String approvedDate;
    // 审核负责人
    private String director;
    // 审核状态
    private String state;

    public Renewal(Renewal renewal) {
        Optional.ofNullable ( renewal ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.renewalAge = e.getRenewalAge ();
            this.departmentComment = e.getDepartmentComment ();
            this.startContract = e.getStartContract ();
            this.endContract = e.getEndContract ();
            this.applyDate = e.getApplyDate ();
            this.approvedDate = e.getApprovedDate ();
            this.director = e.getDirector ();
            this.state = e.getState ();
        } );
    }

}
