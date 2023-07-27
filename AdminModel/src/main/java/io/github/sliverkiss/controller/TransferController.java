package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.TransferQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Transfer;
import io.github.sliverkiss.service.impl.TransferServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/27
 */
@RestController
@RequestMapping("admin/department/transfer")
public class TransferController extends BaseController<TransferServiceImpl, Transfer> {

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(TransferQueryDTO transferQueryDTO) {
        return service.selectPage ( transferQueryDTO );
    }
}
