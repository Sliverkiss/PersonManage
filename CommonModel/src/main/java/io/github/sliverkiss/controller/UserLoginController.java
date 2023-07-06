package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.User;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import io.github.sliverkiss.service.UserLoginService;
import io.github.sliverkiss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/3
 */

@RestController
@RequestMapping("/user")
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        //合法行为判断
        if(!StringUtils.hasText (user.getUsername ())){
            throw new SystemException (  AppHttpCodeEnum.REQUIRE_USERNAME );
        }
        return userLoginService.login(user);
    }


}
