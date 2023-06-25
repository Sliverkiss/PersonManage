package io.github.sliverkiss.handler.security;


import com.alibaba.fastjson.JSON;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.utils.WebUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证授权异常处理
 *
 * @author SliverKiss
 * @apiNote
 * @date 2023/2/8
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace ();
        ResponseResult result = null;
        if (authException instanceof AuthenticationException) {
            result = ResponseResult.errorResult ( AppHttpCodeEnum.LOGIN_ERROR.getCode (), authException.getMessage () );
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult ( AppHttpCodeEnum.NEED_LOGIN );
        } else {
            result = ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "认证或授权失败！" );
        }
        // 响应前端
        WebUtils.renderString ( response, JSON.toJSONString ( result ) );
    }
}
