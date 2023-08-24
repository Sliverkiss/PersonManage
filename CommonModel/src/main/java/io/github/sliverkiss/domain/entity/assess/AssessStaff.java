package io.github.sliverkiss.domain.entity.assess;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * 考核名单(AssessStaff)表实体类
 *
 * @author tistzach
 * @since 2023-08-17 16:39:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("assess_staff")
public class AssessStaff extends BaseEntity implements Serializable {
    @TableId
    private Integer id;

    // 人员类型：1-受考核人员；2-有审批权限人员
    private String type;
    // 员工编号
    private Integer employeeId;
    // 考核计划id
    private Integer assessId;
    // 创建日期
    private String createDate;
    // 更新日期
    private String updateDate;

    @TableField(exist = false)
    private String employeeName;
    /**
     * 部门id
     */
    @TableField(exist = false)
    private Integer deptId;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String assessTitle;

    public AssessStaff(AssessStaff entity) {
        Optional.ofNullable ( entity ).ifPresent ( e -> {
            this.id = e.getId ();
            this.type = e.getType ();
            this.employeeId = e.getEmployeeId ();
            this.assessId = e.getAssessId ();
            this.createDate = e.getCreateDate ();
            this.updateDate = e.getUpdateDate ();
            this.employeeName = e.getEmployeeName ();
            this.deptId = e.getDeptId ();
            this.deptName = e.getDeptName ();
            this.assessTitle = e.getAssessTitle ();
        } );
    }
}
