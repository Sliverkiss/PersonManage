package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.AssessItemQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessItem;
import io.github.sliverkiss.service.impl.AssessItemServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@RestController
@RequestMapping("/admin/assess/item")
public class AssessItemController extends BaseController<AssessItemServiceImpl, AssessItem> {

    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult ( service.list () );
    }

    @GetMapping("/page")
    public ResponseResult selectPage(AssessItemQueryDTO dto) {
        return service.selectPage ( dto );
    }
}
