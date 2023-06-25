package io.github.sliverkiss.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * 快json复述,序列化器
 *
 * @author 谭礼赞 2038940123
 * @apiNote
 * @date 2023/1/6
 */

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName ( "UTF-8" );

    private Class<T> clazz;

    static {
        ParserConfig.getGlobalInstance ().setAutoTypeSupport ( true );
    }

    public FastJsonRedisSerializer(Class<T> clazz) {
        super ();
        this.clazz = clazz;
    }

    /**
     * 序列化
     *
     * @param t t
     *
     * @return {@link byte[]}
     *
     * @throws SerializationException 序列化异常
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString ( t, SerializerFeature.WriteClassName ).getBytes ( DEFAULT_CHARSET );
    }

    /**
     * 反序列化
     *
     * @param bytes 字节
     *
     * @return {@link T}
     *
     * @throws SerializationException 序列化异常
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return (T) JSON.parseObject ( new String ( bytes, DEFAULT_CHARSET ), clazz );
    }


}
