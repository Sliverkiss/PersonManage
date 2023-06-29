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
 * 离职记录(Resignation)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("resignation")
public class Resignation  {
    @TableId
    private Integer id;

    //员工编号
    private Integer employeeId;
    //离职日期
    private Date resignationDate;
    //离职原因
    private String reason;
    //离职状态
    private Integer status;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
