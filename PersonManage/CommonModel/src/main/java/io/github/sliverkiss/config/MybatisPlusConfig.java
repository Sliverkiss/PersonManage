package io.github.sliverkiss.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis +配置
 *
 * @author 谭礼赞 2038940123
 * @date 2023/01/05
 * @since 2023-01-05-2023/1/5
 */

@Configuration
// @MapperScan("com.tistzach.dao")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor ();
        mybatisPlusInterceptor.addInnerInterceptor ( new PaginationInnerInterceptor () );
        return mybatisPlusInterceptor;
    }
}
