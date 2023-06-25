package io.github.sliverkiss.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jwt工具类
 *
 * @author 谭礼赞 2038940123
 * @apiNote
 * @date 2023/1/6
 */

@Component
public class JwtUtil {

    /**
     * 签名用的密钥
     */
    private static final String SIGNING_KEY = "tistzach";

    private static final Long SIGNING_TTL = 24 * 60 * 60 * 1000L;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建jwt
     * 用户登录成功后生成Jwt
     * 使用Hs256算法
     *
     * @param userId 用户id
     *
     * @return token字符串
     */
    public String createJWT(String userId) {
        // 指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis ();
        Date now = new Date ( nowMillis );
        // 根据id创建map
        Map<String, Object> claims = new HashMap<> ();
        claims.put ( "userId", userId );
        // 创建一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder ()
                // 保存在Payload（有效载荷）中的内容
                .setClaims ( claims )
                // iat: jwt的签发时间
                .setIssuedAt ( now )
                // 设置过期时间
                .setExpiration ( new Date ( System.currentTimeMillis () + SIGNING_TTL ) )
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith ( signatureAlgorithm, SIGNING_KEY );
        redisTemplate.opsForValue ().set ( builder.compact (), 1, 7, TimeUnit.DAYS );
        return builder.compact ();
    }

    /**
     * 解析token，获取到Payload（有效载荷）中的内容，包括验证签名，判断是否过期
     *
     * @param token
     *
     * @return
     */
    public Claims parseJWT(String token) {
        // 得到DefaultJwtParser
        // 先验证是否有token
        Boolean flag = redisTemplate.hasKey ( token );
        if (!flag) {
            return null;
        }
        Claims claims = Jwts.parser ()
                // 设置签名的秘钥
                .setSigningKey ( SIGNING_KEY )
                // 设置需要解析的token
                .parseClaimsJws ( token ).getBody ();
        return claims;
    }

}
