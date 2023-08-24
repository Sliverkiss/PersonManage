package io.github.sliverkiss.domain.entity.RBAC;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * (Permission)表实体类
 *
 * @author tistzach
 * @since 2023-08-07 16:36:43
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("permission")
public class Permission extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 名称
    private String name;
    // 菜单类型
    private String menuType;
    // 菜单名称
    private String menuName;
    // 资源路径
    private String path;
    // 菜单图标
    private String icon;
    // 父类菜单
    private Integer parentMenu;
    // 子列表
    @TableField(exist = false)
    private List<Permission> children;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Permission that = (Permission) o;
        return path.equals ( that.path );
    }

    @Override
    public int hashCode() {
        return Objects.hash ( path );
    }

}
