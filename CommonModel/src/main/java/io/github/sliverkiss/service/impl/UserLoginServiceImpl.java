package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.dao.UserDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.User;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.UserLoginService;
import io.github.sliverkiss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/3
 */

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult login(User user) {

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery ( User.class ).eq ( User::getUsername, user.getUsername () ).eq ( User::getPassword, user.getPassword () );
        User loginUser = userService.getOne ( wrapper );
        if (Objects.nonNull(loginUser)) {
            return ResponseResult.okResult ( loginUser );
        }
        return ResponseResult.errorResult ( AppHttpCodeEnum.LOGIN_ERROR );

    }

    public ResponseResult register(User user) {
        if (StringUtils.isBlank ( user.getPassword () )){
            user.setPassword( UserContants.DEFAULT_PASS);
        }
        // user.setPassword ( SecureUtil.md5(user.getPassword()) )
        userService.save(user);
        return ResponseResult.okResult();
    }
}
