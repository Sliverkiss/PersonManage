package io.github.sliverkiss.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 续约申请表(Renewal)表实体类
 *
 * @author tistzach
 * @since 2023-07-07 11:44:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("renewal")
public class Renewal  {
    @TableId
    private Integer id;

    //员工编号
    private Integer personalId;
    //续约年数
    private Integer renewalAge;
    //部门意见
    private String departmentComment;
    //审核日期
    private Date approvedDate;
    //审核负责人
    private String director;
    //审核状态
    private String state;
    
    private Integer delFlag;

    public Renewal(Renewal renewal) {
        Optional.ofNullable ( renewal ).ifPresent ( e -> {
            this.id = e.getId ();
            this.personalId = e.getPersonalId ();
           this.renewalAge = e.getRenewalAge();
           this.departmentComment = e.getDepartmentComment();
           this.approvedDate = e.getApprovedDate();
           this.director = e.getDirector();
           this.state = e.getState();
        } );
    }

}
