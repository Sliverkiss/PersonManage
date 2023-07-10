package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Renewal;

/**
 * 续约申请表(Renewal)表服务接口
 *
 * @author tistzach
 * @since 2023-07-07 11:44:20
 */
public interface RenewalService extends IService<Renewal> {

    public ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO);

    public ResponseResult saveRenewal(Renewal renewal);

    public ResponseResult deleteRenewal(Integer id);

    public ResponseResult updateRenewal(Renewal renewal);
}

