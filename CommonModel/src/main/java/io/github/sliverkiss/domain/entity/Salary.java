package io.github.sliverkiss.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * 工资表(Salary)表实体类
 *
 * @author tistzach
 * @since 2023-07-16 15:42:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("salary")
public class Salary extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 员工编号
    private Integer employeeId;
    // 工资月份
    private String salaryDate;
    // 基础工资
    private Double baseSalary;
    // 绩效奖金
    private Double performance;
    // 请假扣款
    private Double deduLeave;
    // 迟到扣款
    private Double deduLate;
    // 五险一金
    private Double insure;
    // 净工资
    private Double netSalary;
    // 创建时间
    private String payDate;
    // 发放状态
    private String status;
    @TableField(exist = false)
    private Employee employee;

    public Salary(Salary salary) {
        Optional.ofNullable ( salary ).ifPresent ( e -> {
            this.id = e.getId ();
            this.employeeId = e.getEmployeeId ();
            this.salaryDate = e.getSalaryDate ();
            this.baseSalary = e.getBaseSalary ();
            this.performance = e.getPerformance ();
            this.deduLeave = e.getDeduLeave ();
            this.deduLate = e.getDeduLate ();
            this.insure = e.getInsure ();
            this.netSalary = e.getNetSalary ();
            this.payDate = e.getPayDate ();
            this.status = e.getStatus ();
            this.employee = e.getEmployee ();
        } );
    }

}
