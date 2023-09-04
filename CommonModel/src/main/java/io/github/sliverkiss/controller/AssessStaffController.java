package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.DeclareContants;
import io.github.sliverkiss.constants.SystemConstants;
import io.github.sliverkiss.controller.DTO.AssessStaffQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.domain.entity.assess.AssessStaff;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.AssessDeclareService;
import io.github.sliverkiss.service.impl.AssessStaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@RestController
@RequestMapping("/admin/assess/staff")
public class AssessStaffController extends BaseController<AssessStaffServiceImpl, AssessStaff> {

    @Autowired
    private AssessDeclareService declareService;

    @GetMapping("/page")
    public ResponseResult selectPage(AssessStaffQueryDTO dto) {
        return service.selectPage ( dto );
    }

    @Override
    @PostMapping("/save")
    public ResponseResult save(@RequestBody AssessStaff staff) {
        return service.saveStaffAndDeclare ( staff );
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        AssessStaff staff = service.getById ( id );
        Integer assessId = staff.getAssessId ();
        Integer employeeId = staff.getEmployeeId ();
        AssessDeclare declare = declareService.getOne ( Wrappers.lambdaQuery ( AssessDeclare.class )
                .eq ( AssessDeclare::getType, SystemConstants.ASSESSSTAF_TYPE_REVIEWED )
                .eq ( AssessDeclare::getAssessId, assessId )
                .eq ( AssessDeclare::getEmployeeId, employeeId ) );
        if (!declare.getStatus ().equals ( DeclareContants.STATUS_WAIT )) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR, "该员工已进行该考核的申报，请先删除考核申报信息后在进行删除\"" );
        }
        // 删除员工考核申报
        service.deleteEntity ( id );
        // 删除考核员工关系
        declareService.removeById ( declare.getId () );
        return ResponseResult.okResult ();
    }
}
