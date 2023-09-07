package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.TrainningRecordDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.service.impl.TrainningRecordServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@RestController
@RequestMapping("admin/training/record")
public class TrainningRecordController extends BaseController<TrainningRecordServiceImpl, TrainningRecord> {
    @GetMapping("/page")
    public ResponseResult selectPage(TrainningRecordDTO trainningRecordDTO) {
        return service.selectPage ( trainningRecordDTO );
    }

    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult ( service.list () );
    }


    @Override
    public void beforeSave(TrainningRecord trainningRecord) throws Exception {
        // 如果存在分数，则注入培训结果
        if (trainningRecord.getScore () != null) {
            trainningRecord.setLevel ( this.getLevel ( trainningRecord ) );
        }
    }

    public String getLevel(TrainningRecord trainningRecord) {
        Double score = trainningRecord.getScore ();
        return score >= 80 ? "优秀" : score >= 60 ? "合格" : "不合格";

    }
}
