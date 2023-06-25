package io.github.sliverkiss.handler.security;


import com.alibaba.fastjson.JSON;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证授权失败处理
 *
 * @author SliverKiss
 * @apiNote
 * @date 2023/2/8
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    /**
     * 处理
     *
     * @param request               请求
     * @param response              响应
     * @param accessDeniedException 拒绝访问异常
     *
     * @throws IOException      ioexception
     * @throws ServletException servlet异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace ();
        // 响应前端
        ResponseResult result = ResponseResult.errorResult ( AppHttpCodeEnum.NO_OPERATOR_AUTH );
        WebUtils.renderString ( response, JSON.toJSONString ( result ) );
    }
}
