package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Renewal;
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
    public ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO) {
        return renewalService.selectRenewalPage ( renewalQueryDTO );
    }

    @PostMapping("/renewal/save")
    public ResponseResult saveRenewal(@RequestBody Renewal renewal) {
        return renewalService.saveRenewal ( renewal );
    }

    @PutMapping("/renewal/update")
    public ResponseResult updateRenewal(@RequestBody Renewal renewal) {
        return renewalService.updateRenewal ( renewal );
    }

    @DeleteMapping("/renewal/delete/{id}")
    public ResponseResult deleteRenewal(@PathVariable("id") Integer id) {
        return renewalService.deleteRenewal ( id );
    }
}
