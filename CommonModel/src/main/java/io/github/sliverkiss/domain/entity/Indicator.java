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
 * 绩效考核指标(Indicator)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("indicator")
public class Indicator  {
    @TableId
    private Integer id;

    //指标名称
    private String indicatorName;
    //指标权重
    private Integer indicatorWeight;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
