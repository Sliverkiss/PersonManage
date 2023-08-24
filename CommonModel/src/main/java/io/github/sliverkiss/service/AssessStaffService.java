package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.AssessStaffQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessStaff;

import java.util.List;

/**
 * 考核名单(AssessStaff)表服务接口
 *
 * @author tistzach
 * @since 2023-08-17 16:39:46
 */
public interface AssessStaffService extends ICrudService<AssessStaff> {

    ResponseResult selectPage(AssessStaffQueryDTO dto);

    List<AssessStaff> getByAssessId(Integer assessId, Integer employeeId);

    ResponseResult saveStaffAndDeclare(AssessStaff staff);
}

