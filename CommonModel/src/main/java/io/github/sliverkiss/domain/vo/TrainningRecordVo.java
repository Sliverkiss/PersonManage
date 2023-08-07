package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.TrainningRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TrainningRecordVo extends TrainningRecord {
    private String employeeName;
    private String planName;

    public TrainningRecordVo(TrainningRecord train) {
        super ( train );
    }
}
