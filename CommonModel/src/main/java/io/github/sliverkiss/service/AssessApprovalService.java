package io.github.sliverkiss.service;

import io.github.sliverkiss.domain.entity.assess.AssessApproval;

import java.util.List;

/**
 * 绩效审批(AssessApproval)表服务接口
 *
 * @author tistzach
 * @since 2023-08-17 16:40:18
 */
public interface AssessApprovalService extends ICrudService<AssessApproval> {
    List<AssessApproval> getList();
}

