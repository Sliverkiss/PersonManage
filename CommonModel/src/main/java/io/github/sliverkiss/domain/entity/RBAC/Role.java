package io.github.sliverkiss.domain.entity.RBAC;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Role)表实体类
 *
 * @author tistzach
 * @since 2023-08-07 16:35:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("role")
public class Role extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 角色
    private String name;
    // 备注
    private String comment;


}
