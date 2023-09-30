package io.github.sliverkiss.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 岗位信息(Post)表实体类
 *
 * @author tistzach
 * @since 2023-09-04 19:59:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("department_post")
public class Post extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 岗位名称
    private String name;
    // 所属部门
    private Integer departmentId;
    @TableField(exist = false)
    private Department department;
    @TableField(exist = false)
    private List<Employee> employeeList;

    public Post(Post post) {
        Optional.ofNullable ( post ).ifPresent ( e -> {
            this.id = e.getId ();
            this.name = e.getName ();
            this.departmentId = e.getDepartmentId ();
            this.department = e.getDepartment ();
            this.employeeList = e.getEmployeeList ();
        } );
    }
}
