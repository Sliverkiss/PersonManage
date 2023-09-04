package io.github.sliverkiss.domain.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Optional;

/**
 * 员工表(Employee)表实体类
 *
 * @author tistzach
 * @since 2023-07-05 11:45:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("employee_work")
public class Employee extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    @ExcelProperty(value = "员工编号", index = 0)
    private Integer id;

    // 人员信息id
    private Integer personalId;
    // 入职日期
    @ExcelProperty(value = "入职日期", index = 16)
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String hireDate;
    // 所属部门
    private Integer departmentId;
    // 职务
    @ExcelProperty(value = "职务", index = 17)
    private String post;
    // 职称
    @ExcelProperty(value = "职称", index = 18)
    private String level;
    // 在职状态
    @ExcelProperty(value = "邮箱", index = 19)
    private String workState;
    // 合同起始日期
    @ExcelProperty(value = "合同起始日期", index = 20)
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startContract;
    // 合同终止日期
    @ExcelProperty(value = "合同终止日期", index = 21)
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endContract;
    // 合同期限
    @ExcelProperty(value = "合同期限（年）", index = 22)
    private Integer contractTerm;
    // 聘用形式
    @ExcelProperty(value = "聘用形式", index = 23)
    private String engageForm;
    @TableField(exist = false)
    private Personal personal;

    public Employee(Employee employee) {
        Optional.ofNullable ( employee ).ifPresent ( e -> {
            this.id = e.getId ();
            this.personalId = e.getPersonalId ();
            this.hireDate = e.getHireDate ();
            this.departmentId = e.getDepartmentId ();
            this.post = e.getPost ();
            this.personal = e.getPersonal ();
            this.level = e.getLevel ();
            this.workState = e.getWorkState ();
            this.startContract = e.getStartContract ();
            this.endContract = e.getEndContract ();
            this.contractTerm = e.getContractTerm ();
            this.engageForm = e.getEngageForm ();
        } );
    }


}
