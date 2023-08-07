package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.ResignationDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Resignation;
import io.github.sliverkiss.service.impl.ResignationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/3
 */
@RestController
@RequestMapping("admin/department/resignation")
public class ResignationController extends BaseController<ResignationServiceImpl, Resignation> {
    @GetMapping("/page")
    public ResponseResult selectPage(ResignationDTO resignationDTO) {
        return service.selectPage ( resignationDTO );
    }

}
