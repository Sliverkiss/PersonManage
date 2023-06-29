package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.PerformanceDao;
import io.github.sliverkiss.domain.entity.Performance;
import io.github.sliverkiss.service.PerformanceService;
import org.springframework.stereotype.Service;

/**
 * 员工绩效(Performance)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:35
 */
@Service("performanceService")
public class PerformanceServiceImpl extends ServiceImpl<PerformanceDao, Performance> implements PerformanceService {

}

