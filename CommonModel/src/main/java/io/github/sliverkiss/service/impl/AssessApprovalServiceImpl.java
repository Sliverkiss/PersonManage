package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.AssessApprovalDao;
import io.github.sliverkiss.domain.entity.assess.AssessApproval;
import io.github.sliverkiss.service.AssessApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 绩效审批(AssessApproval)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-17 16:40:18
 */
@Service("assessApprovalService")
public class AssessApprovalServiceImpl extends ServiceImpl<AssessApprovalDao, AssessApproval> implements AssessApprovalService {
    @Autowired
    private AssessApprovalDao approvalDao;

    @Override
    public List<AssessApproval> getList() {
        return approvalDao.getList ();
    }

}

