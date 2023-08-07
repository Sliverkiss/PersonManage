package io.github.sliverkiss.domain.entity.RBAC;


import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (RolePermission)表实体类
 *
 * @author tistzach
 * @since 2023-08-07 16:35:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("role_permission")
public class RolePermission extends BaseEntity implements Serializable {
    private Integer roleId;
    private Integer permissionId;


}
