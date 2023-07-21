package io.github.sliverkiss.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * 部门(Department)表实体类
 *
 * @author tistzach
 * @since 2023-07-06 12:50:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("department")
public class Department extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 部门名称
    private String departmentName;

    // 部门职能
    private String contect;
    // 上级部门编号
    private Integer parentId;
    // 部门位置
    private String location;
    // 负责人
    private String manager;
    // 部门电话
    private String phone;

    public Department(Department department) {
        Optional.ofNullable ( department ).ifPresent ( e -> {
            this.id = e.getId ();
            this.departmentName = e.getDepartmentName ();
            this.contect = e.getContect ();
            this.parentId = e.getParentId ();
            this.location = e.getLocation();
            this.manager = e.getManager();
            this.phone = e.getPhone();
        } );
    }

}
