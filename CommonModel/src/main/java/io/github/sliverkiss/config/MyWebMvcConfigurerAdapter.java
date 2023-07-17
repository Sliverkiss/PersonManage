package io.github.sliverkiss.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/16
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurationSupport {
    /**
     * @Description 注册到spring容器中
     * @Author phacxj
     * @Createtime 2019/1/31 11:03
     * @Param
     * @Return
     */
    @Resource
    private EntityParamHandlerMethodArgumentResolver handlerParameterResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add ( handlerParameterResolver );
        super.addArgumentResolvers ( argumentResolvers );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler ( "/static/**" ).addResourceLocations ( "classpath:/static/" );
    }
}
