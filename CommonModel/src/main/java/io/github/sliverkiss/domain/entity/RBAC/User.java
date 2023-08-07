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
import java.util.Optional;
import java.util.Set;

/**
 * 用户(User)表实体类
 *
 * @author tistzach
 * @since 2023-07-03 13:12:51
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 头像
    private String img;
    // 绑定员工编号
    private Integer employeeId;

    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private Set<Permission> permissions;

    // 单例模式构造器
    public User(User user) {
        Optional.ofNullable ( user ).ifPresent ( e -> {
            this.id = e.getId ();
            this.username = e.getUsername ();
            this.password = e.getPassword ();
            this.img = e.getImg ();
            this.employeeId = e.getEmployeeId ();
            this.token = e.getToken ();
            this.permissions = getPermissions ();
        } );
    }

}
