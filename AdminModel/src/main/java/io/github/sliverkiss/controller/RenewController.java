package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.EmployeeQueryDTO;
import io.github.sliverkiss.domain.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.service.RenewalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@RestController
@RequestMapping("/admin")
public class RenewController {

    @Resource
    private RenewalService renewalService;

    @GetMapping("/renewal/page")
    public ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO){
        return renewalService.selectRenewalPage(renewalQueryDTO);
    }

    @DeleteMapping("/renewal/delete/{id}")
    public ResponseResult deleteRenewal(@PathVariable("id") Integer id){
        return renewalService.deleteRenewal(id);
    }
}
