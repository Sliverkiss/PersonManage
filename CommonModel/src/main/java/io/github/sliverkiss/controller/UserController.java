package io.github.sliverkiss.controller;


import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.RBAC.User;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.impl.UserServiceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/7
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserServiceImpl, User> {

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        // 合法行为判断
        if (!StringUtils.hasText ( user.getUsername () )) {
            throw new SystemException ( AppHttpCodeEnum.REQUIRE_USERNAME );
        }
        return service.login ( user );
    }

}
