package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.TrainningRecordDao;
import io.github.sliverkiss.domain.entity.TrainningRecord;
import io.github.sliverkiss.service.TrainningRecordService;
import org.springframework.stereotype.Service;

/**
 * 培训记录(TrainningRecord)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:57:24
 */
@Service("trainningRecordService")
public class TrainningRecordServiceImpl extends ServiceImpl<TrainningRecordDao, TrainningRecord> implements TrainningRecordService {

}

