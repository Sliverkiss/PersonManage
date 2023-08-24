package io.github.sliverkiss.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 人员信息表(Personal)表实体类
 *
 * @author tistzach
 * @since 2023-07-05 11:45:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("employee_info")
public class Personal extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 姓名
    private String name;
    // 性别
    private String gender;
    // 出生日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;
    //身份证号
    private String idCard;
    //婚姻状况
    private String wedlock;
    //民族
    private String nation;
    //籍贯
    private String naticePlace;
    //政治面貌
    private String politic;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //地址
    private String address;
    //最高学历
    private String tiptopDegree;
    //所属专业
    private String specialty;
    //毕业院校
    private String school;

}
