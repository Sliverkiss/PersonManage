package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.DeclareContants;
import io.github.sliverkiss.controller.DTO.AssessDeclareQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessApproval;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.AssessApprovalService;
import io.github.sliverkiss.service.impl.AssessDeclareServiceImpl;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/18
 */
@Slf4j
@RestController
@RequestMapping("/admin/assess/declare")
public class AssessDeclareController extends BaseController<AssessDeclareServiceImpl, AssessDeclare> {

    @Autowired
    private AssessApprovalService approvalService;

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

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        AssessDeclare declare = service.getById ( id );
        if (declare.getStatus ().equals ( DeclareContants.STATUS_WAIT )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "该记录未申报，无需重置" );
        }
        // 获取申报子项
        List<AssessDeclare> list = service.getDeclareChildren ( declare );
        // 移除所有申报子项
        if (list.size () > 0) {
            List<Integer> declareIds = list.stream ().map ( AssessDeclare::getId ).collect ( Collectors.toList () );
            service.removeByIds ( declareIds );
        }
        LambdaQueryWrapper<AssessApproval> approvalWrapper = Wrappers.lambdaQuery ( AssessApproval.class );
        approvalWrapper.eq ( AssessApproval::getDeclareId, declare.getId () );
        // 移除审核记录
        approvalService.remove ( approvalWrapper );
        // 修改申报状态、自评分数、审核评分
        declare.setStatus ( DeclareContants.STATUS_WAIT ).setScore ( 0 ).setApprovalScore ( 0 );
        service.updateById ( declare );
        // 只清除申报记录和审批记录
        return ResponseResult.okResult ();
    }


}
