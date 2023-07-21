package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.RenewalQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Renewal;
import io.github.sliverkiss.service.impl.RenewalServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/7
 */
@RestController
@RequestMapping("/admin/employee/renewal")
public class RenewController extends BaseController<RenewalServiceImpl, Renewal> {

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(RenewalQueryDTO renewalQueryDTO) {
        return service.selectRenewalPage ( renewalQueryDTO );
    }

    @Override
    public void beforeSave(Renewal renewal) throws Exception {
        // 获取当前日期并注入renewal
        String approvedDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Optional.ofNullable ( renewal ).ifPresent ( e -> e.setApprovedDate ( approvedDate ) );
    }

    @Override
    public void beforeUpdate(Renewal renewal) throws Exception {
        beforeSave ( renewal );
    }


}
