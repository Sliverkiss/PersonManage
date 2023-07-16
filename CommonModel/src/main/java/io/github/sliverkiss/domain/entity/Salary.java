package io.github.sliverkiss.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * 工资表(Salary)表实体类
 *
 * @author tistzach
 * @since 2023-07-16 08:21:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("salary")
public class Salary {
    @TableId
    private Integer id;

    // 员工编号
    private Integer employeeId;
    // 工资月份
    private String salaryDate;
    // 基础工资
    private Integer baseSalary;
    // 绩效奖金
    private Integer performance;
    // 请假扣款
    private Integer deduLeave;
    // 迟到扣款
    private Integer deduLate;
    // 五险一金
    private Integer insure;
    // 净工资
    private Integer netSalary;
    // 创建时间
    private Integer payDate;
    // 发放状态
    private Integer status;
    @TableLogic
    private Integer delFlag;

    public Salary(Salary salary) {
        Optional.ofNullable ( salary ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.baseSalary = e.getBaseSalary ();
            this.performance = e.getPerformance ();
            this.deduLeave = e.getDeduLeave ();
            this.deduLeave = e.getDeduLate ();
            this.insure = e.getInsure ();
            this.netSalary = e.getNetSalary ();
            this.payDate = e.getPayDate ();
            this.status = e.getStatus ();
        } );
    }

}
