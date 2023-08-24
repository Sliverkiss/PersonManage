package io.github.sliverkiss.domain.entity.assess;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.util.List;
import java.util.Optional;

/**
 * 绩效考核计划(AssessSet)表实体类
 *
 * @author tistzach
 * @since 2023-08-17 16:39:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("assess_set")
public class AssessSet extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 标题
    private String title;
    // 考核开始日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    // 考核结束日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate;
    // 考核说明
    private String remark;
    // 考核总分
    private Integer socre;
    // 评分标准
    private String standard;
    // 创建时间
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String crateDate;
    // 更新日期
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String updateDate;
    /**
     * 考核项id集合
     */
    @TableField(exist = false)
    private List<Integer> itemIdList;
    /**
     * 考核项集合
     */
    @TableField(exist = false)
    private List<AssessItem> items;


    public AssessSet(AssessSet assessSet) {
        Optional.ofNullable ( assessSet ).ifPresent ( e -> {
            this.id = e.getId ();
            this.title = e.getTitle ();
            ;
            this.startDate = e.getStartDate ();
            this.endDate = e.getEndDate ();
            this.remark = e.getRemark ();
            this.socre = e.getSocre ();
            this.standard = e.getStandard ();
            this.crateDate = e.getCrateDate ();
            this.updateDate = e.getUpdateDate ();
            this.itemIdList = e.getItemIdList ();
            this.items = e.getItems ();
        } );
    }


}
