package io.github.sliverkiss.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeVo extends Employee{
    //姓名
    private String name;
    //性别
    private String gender;
    //出生日期
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
    //部门名称
    private String departmentName;

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
