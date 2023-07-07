package io.github.sliverkiss.domain.entity;



import java.io.Serializable;

import java.util.Date;
import java.util.Optional;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.sliverkiss.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("employee")
public class Employee {
    @TableId
    private Integer id;

    // 人员信息id
    private Integer personalId;
    // 入职日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String hireDate;
    // 所属部门
    private Integer departmentId;
    // 职务
    private String post;
    // 职称
    private String level;
    // 在职状态
    private String workState;
    // 合同起始日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startContract;
    // 合同终止日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endContract;
    // 合同期限
    private Integer contractTerm;
    // 聘用形式
    private String engageForm;
    // 删除标记
    @TableLogic
    private Integer delFlag;


    public Employee(Employee employee) {
        Optional.ofNullable ( employee ).ifPresent ( e -> {
            this.id = e.getId ();
            this.personalId = e.getPersonalId ();
            this.hireDate = e.getHireDate ();
            this.departmentId = e.getDepartmentId ();
            this.post = e.getPost ();
            this.level = e.getLevel ();
            this.workState = e.getWorkState ();
            this.startContract = e.getStartContract ();
            this.endContract = e.getEndContract ();
            this.contractTerm = e.getContractTerm ();
            this.engageForm = e.getEngageForm ();
        } );
    }


}
