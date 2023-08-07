package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.TrainingPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TrainingPlanVo extends TrainingPlan {

    private List<TrainningRecordVo> recordList;

    public TrainingPlanVo(TrainingPlan train) {
        super ( train );
    }
}
