package io.github.sliverkiss.domain.entity.assess;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import io.github.sliverkiss.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 绩效审批(AssessApproval)表实体类
 *
 * @author tistzach
 * @since 2023-08-17 16:40:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("assess_approval")
public class AssessApproval extends BaseEntity implements Serializable {
    @TableId
    private Integer id;

    // 审批人编号
    private Integer employeeId;
    // 绩效申报编号
    private Integer declareId;
    // 审批评分
    private Integer score;
    // 审批意见
    private String comment;
    // 审批状态
    private String status;
    // 创建时间
    private String createDate;
    // 更新时间
    private String updateDate;
    @TableField(exist = false)
    private Employee employee;
    @TableField(exist = false)
    private AssessDeclare declare;

}
