package io.github.sliverkiss.domain.vo;

import io.github.sliverkiss.domain.entity.RBAC.User;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/26
 */
@Data
public class UserVo extends User {
    public UserVo(User user) {
        super ( user );
    }
}
