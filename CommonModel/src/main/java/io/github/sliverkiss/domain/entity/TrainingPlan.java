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
 * 培训计划(TrainingPlan)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:57:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("training_plan")
public class TrainingPlan  {
    @TableId
    private Integer id;

    //培训计划名称
    private String planName;
    //开始时间
    private Date startDate;
    //结束时间
    private Date endtDate;
    //教练
    private String trainer;
    //培训地点
    private String location;
    //培训内容
    private String content;
    //培训状态
    private Integer status;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
