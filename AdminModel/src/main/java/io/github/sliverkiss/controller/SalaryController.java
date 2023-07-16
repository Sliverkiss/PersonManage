package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.SalaryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Salary;
import io.github.sliverkiss.service.SalaryService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/employee/salary/save")
    public ResponseResult ResponseResult(@RequestBody Salary salary) {
        return salaryService.saveSalary ( salary );
    }

}
