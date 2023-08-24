package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.AssessDeclareQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.impl.AssessDeclareServiceImpl;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/18
 */
@Slf4j
@RestController
@RequestMapping("/admin/assess/declare")
public class AssessDeclareController extends BaseController<AssessDeclareServiceImpl, AssessDeclare> {

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AssessDeclare declare) {
        declare.setCreateDate ( DateUtil.currentDateFormat () );
        // 将每一个考核项插入数据库
        List<AssessDeclare> declareList = declare.getDeclareList ();
        // 判断是否重复申报
        if (service.existDeclare ( declare )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "已申报无需重复申报" );
        }
        // 计算自评成绩
        Integer score = 0;
        for (AssessDeclare d : declareList) {
            score += d.getScore ();
        }
        service.saveBatch ( declareList );
        // 更新declare
        AssessDeclare res = service.getDeclare ( declare.getAssessId (), declare.getEmployeeId (), 0 );
        res.setStatus ( "已申报，等待审批" ).setScore ( score );
        service.updateById ( res );
        return ResponseResult.okResult ();
    }

    @GetMapping("/page")
    public ResponseResult selectPage(AssessDeclareQueryDTO dto) {
        return service.selectPage ( dto );
    }


}
