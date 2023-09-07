package io.github.sliverkiss.controller.DTO;

import io.github.sliverkiss.domain.entity.Page;
import lombok.Data;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/26
 */
@Data
public class UserQueryDTO extends Page {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String secNewPassword;
}
