package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.IndicatorDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Indicator;
import io.github.sliverkiss.service.impl.IndicatorServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/5
 */
@RestController
@RequestMapping("/admin/indicator")
public class IndicatorController extends BaseController<IndicatorServiceImpl, Indicator> {


    @GetMapping("/page")
    public ResponseResult selectPage(IndicatorDTO indicatorDTO) {
        return service.selectPage ( indicatorDTO );
    }

}
