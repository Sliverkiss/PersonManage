package io.github.sliverkiss.service;

import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.User;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/3
 */

public interface UserLoginService {
    public ResponseResult login(User user);

}
