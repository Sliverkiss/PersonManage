package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.TrainingPlanDao;
import io.github.sliverkiss.domain.DTO.TrainingPlanDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.TrainingPlan;
import io.github.sliverkiss.domain.vo.TrainingPlanVo;
import io.github.sliverkiss.service.TrainingPlanService;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

/**
 * 培训计划(TrainingPlan)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-05 19:17:27
 */
@Service("trainingPlanService")
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanDao, TrainingPlan> implements TrainingPlanService {

    @Override
    public ResponseResult selectPage(TrainingPlanDTO trainingPlanDTO) {
        Page<TrainingPlan> page = this.toPage ( trainingPlanDTO );
        String planName = trainingPlanDTO.getPlanName ();
        String status = trainingPlanDTO.getStatus ();
        try {
            // 模糊查询
            LambdaQueryWrapper<TrainingPlan> wrapper = Wrappers.lambdaQuery ( TrainingPlan.class )
                    .like ( StringUtils.isNotBlank ( planName ), TrainingPlan::getPlanName, planName )
                    .like ( StringUtils.isNotBlank ( status ), TrainingPlan::getStatus, status );
            Page<TrainingPlan> trainingPlanPage = this.page ( page, wrapper );
            IPage<TrainingPlanVo> trainingPlanVoIPage = EntityUtils.toPage ( trainingPlanPage, TrainingPlanVo::new );
            // 属性注入
            return ResponseResult.okResult ( trainingPlanVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }
}

