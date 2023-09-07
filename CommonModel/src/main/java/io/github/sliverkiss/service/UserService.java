package io.github.sliverkiss.service;


import io.github.sliverkiss.controller.DTO.UserQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.RBAC.User;

/**
 * 用户(User)表服务接口
 *
 * @author tistzach
 * @since 2023-07-03 13:12:53
 */
public interface UserService extends ICrudService<User> {
    public ResponseResult login(User user);

    ResponseResult selectPage(UserQueryDTO dto);

    ResponseResult changePassword(UserQueryDTO dto);

    public User getUser(Integer employeeId);
}

