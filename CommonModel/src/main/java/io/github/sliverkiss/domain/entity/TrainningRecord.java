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
 * 培训记录(TrainningRecord)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:57:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("trainning_record")
public class TrainningRecord  {
    @TableId
    private Integer id;

    //员工编号
    private Integer employeeId;
    //培训计划编号
    private Integer planId;
    //培训成绩
    private Integer score;
    //培训状态
    private Integer planState;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
