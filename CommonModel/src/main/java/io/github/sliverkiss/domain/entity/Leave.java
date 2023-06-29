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
 * 请假记录(Leave)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("leave")
public class Leave  {
    @TableId
    private Integer id;

    
    private Integer employeeId;
    //开始时间
    private Date startDate;
    //结束时间
    private Date endDate;
    //请假类型
    private String leaveType;
    //请假理由
    private String reason;
    //请假状态
    private Integer status;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
