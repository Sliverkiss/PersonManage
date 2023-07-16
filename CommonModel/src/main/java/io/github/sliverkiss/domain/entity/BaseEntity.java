package io.github.sliverkiss.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
@Data
public class BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 3363479821720052532L;
}
