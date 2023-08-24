package io.github.sliverkiss.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EmployeeVo extends Employee{
    // 姓名
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
    // 性别
    @ExcelProperty(value = "性别", index = 2)

    private String gender;
    // 出生日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "出生日期", index = 3)
    private String birthday;
    //身份证号
    @ExcelProperty(value = "身份证号", index = 4)
    private String idCard;
    //婚姻状况
    @ExcelProperty(value = "婚姻状况", index = 5)
    private String wedlock;
    //民族
    @ExcelProperty(value = "民族", index = 6)
    private String nation;
    //籍贯
    @ExcelProperty(value = "籍贯", index = 7)
    private String naticePlace;
    //政治面貌
    @ExcelProperty(value = "政治面貌", index = 8)
    private String politic;
    //手机号
    @ExcelProperty(value = "联系电话", index = 9)
    private String phone;
    //邮箱
    @ExcelProperty(value = "邮箱", index = 10)
    private String email;
    //地址
    @ExcelProperty(value = "地址", index = 11)
    private String address;
    //最高学历
    @ExcelProperty(value = "最高学历", index = 12)
    private String tiptopDegree;
    //所属专业
    @ExcelProperty(value = "所属专业", index = 13)
    private String specialty;
    //毕业院校
    @ExcelProperty(value = "毕业院校", index = 14)
    private String school;
    //部门名称
    @ExcelProperty(value = "部门名称", index = 15)
    private String departmentName;
    // 合同列表

    public EmployeeVo(Employee employee) {
        super(employee);
    }

    /**
     * 优化属性注入的代码，添加个人信息
     *
     * @param personal 个人
     */
    public void addPersonalInfo(Personal personal){
        Optional.ofNullable ( personal ).ifPresent ( e -> {
            this.name=e.getName();
            this.gender = e.getGender();
            this.birthday = e.getBirthday();
            this.idCard = e.getIdCard();
            this.wedlock = e.getWedlock();
            this.nation = e.getNation();
            this.naticePlace = e.getNaticePlace();
            this.politic = e.getPolitic();
            this.phone = e.getPhone();
            this.email = e.getEmail();
            this.address = e.getAddress();
            this.tiptopDegree = e.getTiptopDegree();
            this.specialty = e.getSpecialty();
            this.school = e.getSchool();
        });
    }


}
