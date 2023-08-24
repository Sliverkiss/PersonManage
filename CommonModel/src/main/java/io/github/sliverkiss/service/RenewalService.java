package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Renewal;

/**
 * 续约申请表(Renewal)表服务接口
 *
 * @author tistzach
 * @since 2023-07-07 11:44:20
 */
public interface RenewalService extends ICrudService<Renewal> {

     ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO);

}

