package io.github.sliverkiss.domain.entity.assess;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.sliverkiss.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Optional;

/**
 * 考核项(AssessItem)表实体类
 *
 * @author tistzach
 * @since 2023-08-17 16:39:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("assess_item")
public class AssessItem extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 考核计划编号
    private Integer assessId;
    // 考核项名称
    private String name;
    // 评分标准
    private String standard;
    // 考核项说明
    private String remark;
    // 总分值
    private Integer totalScore;
    // 评分占比
    private Integer ratio;
    // 创建日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createDate;
    // 更新日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String updateDate;

    public AssessItem(AssessItem assessItem) {
        Optional.ofNullable ( assessItem ).ifPresent ( e -> {
            this.id = e.getId ();
            this.assessId = e.getAssessId ();
            this.name = e.getName ();
            this.standard = e.getStandard ();
            this.remark = e.getRemark ();
            this.totalScore = e.getTotalScore ();
            this.ratio = e.getRatio ();
            this.createDate = e.getCreateDate ();
            this.updateDate = e.getUpdateDate ();
        } );
    }
}
