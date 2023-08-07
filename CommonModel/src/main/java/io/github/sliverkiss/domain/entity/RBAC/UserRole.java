package io.github.sliverkiss.domain.entity.RBAC;


import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (UserRole)表实体类
 *
 * @author tistzach
 * @since 2023-08-07 16:36:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_role")
public class UserRole extends BaseEntity implements Serializable {
    private Integer userId;
    private Integer roleId;


}
