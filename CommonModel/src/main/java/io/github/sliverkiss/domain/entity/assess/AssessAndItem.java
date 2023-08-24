package io.github.sliverkiss.domain.entity.assess;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 考核计划中间表(AssessAndItem)表实体类
 *
 * @author tistzach
 * @since 2023-08-17 16:40:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("assess_and_item")
public class AssessAndItem extends BaseEntity implements Serializable {
    @TableId
    private Integer id;

    // 考核计划id
    private Integer assessId;
    // 考核项id
    private Integer itemId;


}
