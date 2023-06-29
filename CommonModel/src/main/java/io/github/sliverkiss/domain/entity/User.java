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
 * 用户(User)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:57:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User  {
    @TableId
    private Integer id;

    //用户名
    private String username;
    //密码
    private String password;
    //绑定员工编号
    private Integer employeeId;
    //权限
    private Integer role;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
