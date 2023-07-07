package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.service.RenewalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/renewal/list")
    public ResponseResult selectRenewalPage(){
        return renewalService.selectRenewalPage();
    }
}
