// package io.github.sliverkiss.config;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.SecurityFilterChain;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @author SliverKiss
//  * @apiNote
//  * @date 2023/6/28
//  */
//
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig  {
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         return http
//                 .authorizeHttpRequests ()
//                 .anyRequest().authenticated ()
//                 .and ()
//                 .formLogin ()
//                 .successHandler ( this::onAuthenticationSuccess )
//                 .failureHandler ( this::onAuthenticationFailure )
//                 .loginProcessingUrl ( "api/auth/login" )
//                 .and ()
//                 .logout ()
//                 .logoutSuccessHandler ( this::onLogoutSuccess )
//                 .logoutUrl("/api/auth/logout")
//                 .and ()
//                 .csrf().disable()
//                 .build();
//     }
//
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//         Map<String, Object> result = new HashMap<String, Object> ();
//         result.put("msg", "登录成功");
//         result.put("status", 200);
//         response.setContentType("application/json;charset=UTF-8");
//         String s = new ObjectMapper ().writeValueAsString(result);
//         response.getWriter().println(s);
//     }
//     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//         Map<String, Object> result = new HashMap<String, Object>();
//         result.put("msg", "登录失败: "+exception.getMessage());
//         result.put("status", 500);
//         response.setContentType("application/json;charset=UTF-8");
//         String s = new ObjectMapper().writeValueAsString(result);
//         response.getWriter().println(s);
//     }
//     public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//         Map<String, Object> result = new HashMap<String, Object>();
//         result.put("msg", "注销成功");
//         result.put("status", 200);
//         response.setContentType("application/json;charset=UTF-8");
//         String s = new ObjectMapper().writeValueAsString(result);
//         response.getWriter().println(s);
//     }
// }
