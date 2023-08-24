package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.AssessStaffQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessStaff;
import io.github.sliverkiss.service.impl.AssessStaffServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@RestController
@RequestMapping("/admin/assess/staff")
public class AssessStaffController extends BaseController<AssessStaffServiceImpl, AssessStaff> {

    @GetMapping("/page")
    public ResponseResult selectPage(AssessStaffQueryDTO dto) {
        return service.selectPage ( dto );
    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(AssessStaff staff) {
        return service.saveStaffAndDeclare ( staff );
    }
}
