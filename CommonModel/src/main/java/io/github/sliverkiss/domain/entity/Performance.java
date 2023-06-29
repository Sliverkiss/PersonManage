package io.github.sliverkiss.domain.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 员工绩效(Performance)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("performance")
public class Performance  {
    @TableId
    private Integer id;

    //员工编号
    private Integer employeeId;
    //考核指标编号
    private Integer indicatorId;
    //得分
    private Integer score;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
