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
 * 考勤记录(Attendance)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:47:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("attendance")
public class Attendance  {
    @TableId
    private Integer id;

    //考勤日期
    private Date attendanceDate;
    //上班时间
    private Date startTime;
    //下班时间
    private Date endTime;
    //考勤情况
    private Integer status;
    //员工编号
    private Integer employeeId;

    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;

}
