package io.github.sliverkiss.domain.entity;


import java.io.Serializable;
import java.util.Optional;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

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
public class User extends Model<User> {
    @TableId
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 头像
    private String img;
    // 绑定员工编号
    private Integer employeeId;
    // 权限
    private Integer role;
    // 删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;

    //单例模式构造器
    public User(User user){
        Optional.ofNullable ( user ).ifPresent ( e->{
            this.id=e.getId();
            this.username = e.getUsername();
            this.password = e.getPassword();
            this.img = e.getImg();
            this.employeeId = e.getEmployeeId();
            this.role = e.getRole();
        } );
    }

}
