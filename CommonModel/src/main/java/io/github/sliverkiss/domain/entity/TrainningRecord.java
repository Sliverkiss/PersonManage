package io.github.sliverkiss.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * 培训记录(TrainningRecord)表实体类
 *
 * @author tistzach
 * @since 2023-08-05 19:35:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("trainning_record")
@Builder
public class TrainningRecord extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工编号
    private Integer employeeId;
    // 培训计划编号
    private Integer planId;
    // 培训成绩
    private Double score;
    // 培训结果
    private String level;
    // 培训状态
    private String planState;
    @TableField(exist = false)
    private Employee employee;

    public TrainningRecord(TrainningRecord train) {
        Optional.ofNullable ( train ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.planId = e.getPlanId ();
            this.score = e.getScore ();
            this.level = e.getLevel ();
            this.planState = e.getPlanState ();
        } );
    }


}
