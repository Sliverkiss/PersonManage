package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@RestController
public class ContractController {

    @Resource
    private EmployeeService employeeService;

    @GetMapping("/contract/list")
    public ResponseResult selectContractPage(){
        return employeeService.selectContractPage();
    }
}
