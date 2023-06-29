package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.ResignationDao;
import io.github.sliverkiss.domain.entity.Resignation;
import io.github.sliverkiss.service.ResignationService;
import org.springframework.stereotype.Service;

/**
 * 离职记录(Resignation)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:46
 */
@Service("resignationService")
public class ResignationServiceImpl extends ServiceImpl<ResignationDao, Resignation> implements ResignationService {

}

