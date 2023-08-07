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
 * 绩效考核标准表(Indicator)表实体类
 *
 * @author tistzach
 * @since 2023-08-05 15:53:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("indicator")
public class Indicator extends BaseEntity implements Serializable {
    @TableId
    private Integer id;

    // 指标名称
    private String name;
    // 指标分类
    private String type;
    // 等级A
    private String levelA;
    // 等级B
    private String levelB;
    // 等级C
    private String levelC;
    // 等级D
    private String levelD;
    // 考核项分值（0~3为D，4～5为C，6～7为B，8～10为A）
    private Integer point;
    // 考核部门
    private Integer departmentId;

    public Indicator(Indicator indicator) {
        Optional.ofNullable ( indicator ).ifPresent ( e -> {
            this.id = e.getId ();
            this.name = e.getName ();
            this.levelA = e.getLevelA ();
            this.levelB = e.getLevelB ();
            this.levelC = e.getLevelC ();
            this.levelD = e.getLevelD ();
            this.type = e.getType ();
            this.point = e.getPoint ();
            this.departmentId = e.getDepartmentId ();
        } );
    }
}
