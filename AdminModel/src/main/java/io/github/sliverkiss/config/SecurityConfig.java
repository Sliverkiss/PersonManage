// package io.github.sliverkiss.config;
//
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
// /**
//  * @author 谭礼赞 2038940123
//  * @apiNote
//  * @date 2023/1/6
//  */
// @Configuration
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder ();
//     }
//
//     /**
//      * 身份验证管理器bean
//      *
//      * @return {@link AuthenticationManager}
//      *
//      * @throws Exception 异常
//      */
//     @Override
//     @Bean
//     public AuthenticationManager authenticationManagerBean() throws Exception {
//         return super.authenticationManagerBean ();
//     }
//
//     @Autowired
//     private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//     @Autowired
//     private AuthenticationEntryPoint authenticationEntryPoint;
//     @Autowired
//     private AccessDeniedHandler accessDeniedHandler;
//
//     /**
//      * 配置
//      *
//      * @param http http
//      *
//      * @throws Exception 异常
//      */
//     @Override
//     public void configure(HttpSecurity http) throws Exception {
//         http
//                 // 关闭csrf
//                 .csrf ().disable ()
//                 // 不通过Session获取SecurityContext
//                 .sessionManagement ().sessionCreationPolicy ( SessionCreationPolicy.STATELESS )
//                 .and ()
//                 .authorizeRequests ()
//                 // 对于登录接口，允许匿名访问
//                 .antMatchers ( "/login" ).anonymous ()
//                 // jwt过滤测试
//                 .antMatchers ( "/link/getAllLink" ).authenticated ()
//                 // 除上面外的所有请求全部不需要认证即可访问
//                 .anyRequest ().permitAll ();
//         // 配置异常处理器
//         http.exceptionHandling ()
//                 .authenticationEntryPoint ( authenticationEntryPoint )
//                 .accessDeniedHandler ( accessDeniedHandler );
//         http.logout ().disable ();
//
//         http.addFilterBefore ( jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class );
//         // 允许跨域
//         http.cors ();
//     }
//
// }
