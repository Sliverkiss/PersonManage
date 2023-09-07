package io.github.sliverkiss.controller;

import io.github.sliverkiss.constants.PieContants;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.service.AssessApprovalService;
import io.github.sliverkiss.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/1
 */

@Slf4j
@RestController
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService service;
    @Autowired
    private AssessApprovalService approvalService;

    /**
     * 员工性别统计
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/gender")
    public ResponseResult genderPie() {
        return ResponseResult.okResult ( service.personalPie ( PieContants.PERSONAL_GENDER ) );
    }

    /**
     * 员工
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/workState")
    public ResponseResult workStatePie() {
        return ResponseResult.okResult ( service.EmployeePie ( PieContants.EMPLOYEE_WORKSTATE ) );
    }

    /**
     * 员工政治面貌统计
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/politic")
    public ResponseResult personalPoliticPie() {
        return ResponseResult.okResult ( service.personalPie ( PieContants.PERSONAL_POLITIC ) );
    }

    /**
     * 员工学历统计
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/tiptopDegree")
    public ResponseResult personalTipTopDegreePie() {
        return ResponseResult.okResult ( service.personalPie ( PieContants.PERSONAL_TIPTOPDEGREE ) );
    }

    /**
     * 员工用工类别统计
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/engageForm")
    public ResponseResult engageFormPie() {
        return ResponseResult.okResult ( service.EmployeePie ( PieContants.EMPLOYEE_ENGARE ) );
    }

    /**
     * 各部门员工人数统计
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/department")
    public ResponseResult departmentBar() {
        return ResponseResult.okResult ( service.DepartmentBar () );
    }

    @GetMapping("/assess/{assessId}")
    public ResponseResult assessPointBar(@PathVariable Integer assessId) {
        return ResponseResult.okResult ( service.assessPointBar ( assessId ) );

    }


}
