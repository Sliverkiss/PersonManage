package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.SalaryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.service.SalaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
@RestController
@RequestMapping("/admin")
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    @GetMapping("/employee/salary/page")
    public ResponseResult selectRenewalPage(SalaryDTO salaryDTO) {
        return salaryService.selectSalaryPage ( salaryDTO );
    }
}
