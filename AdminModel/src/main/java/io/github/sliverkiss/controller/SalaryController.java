package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.DTO.SalaryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Salary;
import io.github.sliverkiss.service.impl.SalaryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
@RestController
@RequestMapping("admin/employee/salary")
public class SalaryController extends BaseController<SalaryServiceImpl, Salary> {

    @GetMapping("/page")
    public ResponseResult selectRenewalPage(SalaryDTO salaryDTO) {
        return service.selectSalaryPage ( salaryDTO );
    }

    @Override
    public void beforeSave(Salary salary) throws Exception {
        // 获取当前日期并注入renewal
        String payDate = new SimpleDateFormat ( "yyy-MM-dd" ).format ( new Date () );
        Double netSalary = this.netSalary ( salary );
        // 注入属性：支付日期、净工资
        Optional.ofNullable ( salary ).ifPresent ( e -> {
            e.setPayDate ( payDate ).setNetSalary ( netSalary );
        } );
    }

    @Override
    public void beforeUpdate(Salary salary) throws Exception {
        beforeSave ( salary );
    }

    /**
     * 计算净工资
     *
     * @param salary 工资
     *
     * @return {@link Double}
     */
    public Double netSalary(Salary salary) {
        return salary.getBaseSalary () + salary.getPerformance () - salary.getDeduLeave () - salary.getDeduLate () - salary.getInsure ();
    }
}
