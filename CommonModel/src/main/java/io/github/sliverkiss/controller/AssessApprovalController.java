package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.DeclareContants;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessApproval;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.AssessDeclareService;
import io.github.sliverkiss.service.impl.AssessApprovalServiceImpl;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/19
 */
@Slf4j
@RestController
@RequestMapping("/admin/assess/approval")
public class AssessApprovalController extends BaseController<AssessApprovalServiceImpl, AssessApproval> {
    @Autowired
    private AssessDeclareService declareService;

    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult ( service.getList () );
    }

    @GetMapping("/info/{employeeId}/{assesId}")
    public ResponseResult getDeclareList(@PathVariable Integer employeeId, @PathVariable Integer assesId) {
        AssessDeclare declare = declareService.getDeclare ( assesId, employeeId, 0 );
        return ResponseResult.okResult ( declare );

    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AssessApproval approval) {
        approval.setCreateDate ( DateUtil.currentDateFormat () );
        Integer declareId = approval.getDeclareId ();
        AssessDeclare declare = declareService.getById ( declareId );
        if (DeclareContants.STATUS_WAIT.equals ( declare.getStatus () )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "未申报记录不可进行审批" );
        }
        List<AssessApproval> list = service.list ( Wrappers.lambdaQuery ( AssessApproval.class )
                .eq ( AssessApproval::getDeclareId, declareId ) );
        if (list.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "已审批，请勿重复审批" );
        }
        // 1.保存审批记录
        service.save ( approval );
        // 2.更新申报记录信息
        this.afterEdit ( approval );
        return ResponseResult.okResult ();
    }

    @Override
    @PutMapping("/update")
    public ResponseResult update(AssessApproval approval) {
        approval.setUpdateDate ( DateUtil.currentDateFormat () );
        Integer declareId = approval.getDeclareId ();
        AssessDeclare declare = declareService.getById ( declareId );
        if (DeclareContants.STATUS_WAIT.equals ( declare.getStatus () )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "未申报记录不可进行审批" );
        }
        // 1.更新审批信息
        service.saveOrUpdate ( approval );
        // 2.更新申报记录信息
        this.afterEdit ( approval );
        return ResponseResult.okResult ();
    }

    @Override
    public void beforeDelete(Integer id) {
        AssessApproval approval = service.getById ( id );
        AssessDeclare d = declareService.getById ( approval.getDeclareId () );
        d.setApprovalScore ( 0 )
                .setStatus ( DeclareContants.STATUS_NO_APPROVAL )
                .setUpdateDate ( DateUtil.currentDateFormat () );
        declareService.updateById ( d );
    }

    /**
     * 保存或更新后操作
     *
     * @param approval 批准
     */
    public void afterEdit(AssessApproval approval) {
        AssessDeclare declare = declareService.getById ( approval.getDeclareId () );
        Integer score = approval.getScore ();
        String status = approval.getStatus ();
        String finalState = DeclareContants.STATUS_NO_PASS;
        if (DeclareContants.STATUS_PASS.equals ( status )) {
            finalState = DeclareContants.STATUS_PASS;
        }
        declare.setUpdateDate ( DateUtil.currentDateFormat () )
                .setApprovalScore ( score )
                .setStatus ( finalState );
        declareService.updateById ( declare );
    }


}
