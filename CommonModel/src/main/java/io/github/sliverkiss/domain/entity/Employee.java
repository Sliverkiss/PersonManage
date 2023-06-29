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
 * 员工(Employee)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:55:32
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("employee")
public class Employee  {
    @TableId
    private Integer id;

    //姓名
    private String name;
    //职务
    private String position;
    //所属部门
    private Integer departmentId;
    //入职日期
    private Date hireDate;
    //联系电话
    private String phone;
    //邮箱
    private String email;
    //地址
    private String address;
    //在职状态
    private String state;

    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;

}
