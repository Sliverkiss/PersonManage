// package io.github.sliverkiss.filter;
//
//
// import com.alibaba.fastjson.JSON;
// import io.github.sliverkiss.domain.ResponseResult;
// import io.github.sliverkiss.enums.AppHttpCodeEnum;
// import io.github.sliverkiss.utils.JwtUtil;
// import io.github.sliverkiss.utils.RedisCache;
// import io.github.sliverkiss.utils.WebUtils;
// import io.jsonwebtoken.Claims;
// import io.swagger.v3.oas.annotations.media.Schema;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.Objects;
//
// /**
//  * @author SliverKiss
//  * @apiNote
//  * @date 2023/2/8
//  */
// @Schema(description = "")
// @Component
// public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//     @Schema(hidden = true)
//     @Autowired
//     private JwtUtil jwtUtil;
//     @Autowired
//     private RedisCache redisCache;
//
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         // 获取请求头中的token
//         String token = request.getHeader ( "token" );
//         if (!StringUtils.hasText ( token )) {
//             // 说明该接口不育登录，直接放行
//             filterChain.doFilter ( request, response );
//             return;
//         }
//         // 解析获取userId
//         Claims claims = null;
//         try {
//             claims = jwtUtil.parseJWT ( token );
//             String userId = claims.getSubject ();
//         } catch (Exception e) {
//             // token超时｜非法
//             e.printStackTrace ();
//             // 响应前端，需要重新登录
//             ResponseResult result = ResponseResult.errorResult ( AppHttpCodeEnum.NEED_LOGIN );
//             WebUtils.renderString ( response, JSON.toJSONString ( result ) );
//             return;
//         }
//         String userId = claims.getSubject ();
//         // 从redis中获取用户信息
//         LoginUser loginUser = redisCache.getCacheObject ( "bloglogin:" + userId );
//         if (Objects.isNull ( loginUser )) {
//             // 登录过期，需要重新登录
//             ResponseResult result = ResponseResult.errorResult ( AppHttpCodeEnum.NEED_LOGIN );
//             WebUtils.renderString ( response, JSON.toJSONString ( result ) );
//             return;
//         }
//         // 存入securityCOntextHolder
//         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken ( loginUser, null, null );
//         SecurityContextHolder.getContext ().setAuthentication ( authenticationToken );
//
//         filterChain.doFilter ( request, response );
//
//     }
// }
