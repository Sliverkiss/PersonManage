package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.LeaveDao;
import io.github.sliverkiss.domain.entity.Leave;
import io.github.sliverkiss.service.LeaveService;
import org.springframework.stereotype.Service;

/**
 * 请假记录(Leave)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:21
 */
@Service("leaveService")
public class LeaveServiceImpl extends ServiceImpl<LeaveDao, Leave> implements LeaveService {

}

