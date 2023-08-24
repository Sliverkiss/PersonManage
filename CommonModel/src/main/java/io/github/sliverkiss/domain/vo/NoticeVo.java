package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Notice;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/14
 */
@Data
public class NoticeVo extends Notice {
    public NoticeVo(Notice notice) {
        super ( notice );
    }
}
