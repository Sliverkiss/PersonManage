package io.github.sliverkiss.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 薪资(Salary)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:58
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("salary")
public class Salary  {
    @TableId
    private Integer id;

    //员工编号
    private Integer employeeId;
    //基础工资
    private Integer baseSalary;
    //奖金
    private Integer bonus;
    //扣除工资
    private Integer deductions;
    //净工资
    private Integer netSalary;
    //支付时间
    private Date payDate;
    //支付状态
    private Integer status;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
