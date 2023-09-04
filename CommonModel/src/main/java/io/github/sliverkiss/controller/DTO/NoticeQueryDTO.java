package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/14
 */
@Data
public class NoticeQueryDTO extends Page {
    private String title;
    private String createDate;
    private String director;
}
