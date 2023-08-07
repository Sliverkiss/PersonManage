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
 * 培训计划(TrainingPlan)表实体类
 *
 * @author tistzach
 * @since 2023-08-05 19:17:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("training_plan")
public class TrainingPlan extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 培训计划名称
    private String planName;
    // 开始时间
    private String startDate;
    // 结束时间
    private String endDate;
    // 教练
    private String trainer;
    // 培训地点
    private String location;
    // 培训内容
    private String content;
    // 培训状态
    private String status;

    public TrainingPlan(TrainingPlan train) {
        Optional.ofNullable ( train ).ifPresent ( e -> {
            this.id = e.getId ();
            this.planName = e.getPlanName ();
            this.startDate = e.getStartDate ();
            this.endDate = e.getEndDate ();
            this.trainer = e.getTrainer ();
            this.location = e.getLocation ();
            this.content = e.getContent ();
            this.status = e.getStatus ();
        } );
    }


}
