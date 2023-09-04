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
 * 公告(Notice)表实体类
 *
 * @author tistzach
 * @since 2023-08-14 15:35:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("notice")
public class Notice extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 发布时间
    private String createDate;
    // 发布人
    private String director;
    // 封面
    private String img;
    // 公告类型
    private Integer type;

    public Notice(Notice notice) {
        Optional.ofNullable ( notice ).ifPresent ( e -> {
            this.id = e.getId ();
            this.title = e.getTitle ();
            this.content = e.getContent ();
            this.type = e.getType ();
            this.img = e.getImg ();
            this.director = e.getDirector ();
            this.createDate = e.getCreateDate ();
        } );
    }
}
