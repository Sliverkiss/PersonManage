package io.github.sliverkiss.domain.entity.assess;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 绩效申报(AssessDeclare)表实体类
 *
 * @author tistzach
 * @since 2023-08-17 16:40:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@TableName("assess_declare")
public class AssessDeclare extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工编号
    private Integer employeeId;
    // 考核计划编号
    private Integer assessId;
    @TableField(exist = false)
    private AssessSet assessSet;
    @TableField(exist = false)
    private List<AssessItem> items;
    // 考核项编号
    private Integer itemId;
    @TableField(exist = false)
    private String itemName;
    @TableField(exist = false)
    private List<AssessDeclare> declareList;
    @TableField(exist = false)
    private AssessApproval approval;
    // 类型：0-本次考核整体；1-单个考核项记录
    private Integer type;
    // 考核项内容
    private String content;
    // 自评分
    private Integer score;
    // 审批总分
    private Integer approvalScore;
    // 状态
    private String status;
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

    public AssessDeclare(AssessDeclare declare) {
        Optional.ofNullable ( declare ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.assessId = e.getAssessId ();
            this.assessSet = e.getAssessSet ();
            this.items = e.getItems ();
            this.itemId = e.getItemId ();
            this.itemName = e.getItemName ();
            this.declareList = e.getDeclareList ();
            this.approval = e.getApproval ();
            this.type = e.getType ();
            this.content = e.getContent ();
            this.score = e.getScore ();
            this.approvalScore = e.getApprovalScore ();
            this.status = e.getStatus ();
            this.createDate = e.getCreateDate ();
            this.updateDate = e.getUpdateDate ();
            this.employeeName = e.getEmployeeName ();
            this.deptId = e.getDeptId ();
            this.deptName = e.getDeptName ();
            this.assessTitle = e.getAssessTitle ();
        } );
    }
}
