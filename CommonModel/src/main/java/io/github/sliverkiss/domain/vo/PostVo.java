package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.Post;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/4
 */

@Data
public class PostVo extends Post {
    public PostVo(Post post) {
        super ( post );
    }
}
