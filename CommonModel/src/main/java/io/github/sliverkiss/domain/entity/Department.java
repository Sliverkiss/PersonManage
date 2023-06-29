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
 * 部门(Department)表实体类
 *
 * @author tistzach
 * @since 2023-06-27 14:48:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("department")
public class Department  {
    @TableId
    private Integer id;

    //部门名称
    private String departmentName;
    //上级部门编号
    private Integer managerId;
    //删除标志（0代表未删除）
    @TableLogic
    private Integer delFlag;


}
