package io.github.sliverkiss.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 网络配置的相关属性
 *
 * @author 谭礼赞 2038940123
 * @date 2023/01/05
 * @since 2023-01-05-2023/1/5
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加Cors映射
     *
     * @param registry 注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping ( "/**" )
                // 设置允许跨域请求的域名
                .allowedOriginPatterns ( "*" )
                // 是否允许cookie
                .allowCredentials ( true )
                // 设置允许的请求方式
                .allowedMethods ( "GET", "POST", "DELETE", "PUT" )
                //设置允许的header属性
                .allowedHeaders ( "*" )
                //跨域允许时间
                .maxAge ( 3600 );

    }

    /**
     * 快json http消息转换器
     *
     * @return {@link HttpMessageConverter}
     */
    @Bean//使用@Bean注入fastJsonHttpMessageConverter
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //定义Convert转换消息对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter ();
        FastJsonConfig fastJsonConfig = new FastJsonConfig ();
        fastJsonConfig.setSerializerFeatures ( SerializerFeature.PrettyFormat );
        fastJsonConfig.setDateFormat ( "yyyy-MM-dd HH:mm:ss" );

        SerializeConfig.globalInstance.put ( Long.class, ToStringSerializer.instance );

        fastJsonConfig.setSerializeConfig ( SerializeConfig.globalInstance );
        fastConverter.setFastJsonConfig ( fastJsonConfig );
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    /**
     * 配置消息转换器
     *
     * @param converters 转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add ( fastJsonHttpMessageConverters () );
    }
}
