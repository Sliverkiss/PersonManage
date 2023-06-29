package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.IndicatorDao;
import io.github.sliverkiss.domain.entity.Indicator;
import io.github.sliverkiss.service.IndicatorService;
import org.springframework.stereotype.Service;

/**
 * 绩效考核指标(Indicator)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:03
 */
@Service("indicatorService")
public class IndicatorServiceImpl extends ServiceImpl<IndicatorDao, Indicator> implements IndicatorService {

}

