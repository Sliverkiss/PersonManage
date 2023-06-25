package io.github.sliverkiss.exception;

/**
 * IntelliJ IDEA
 *
 * @author 谭礼赞 2038940123
 * @since 2023-01-04-2023/1/4
 */


import io.github.sliverkiss.enums.AppHttpCodeEnum;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义异常的相关属性
 */
public class SystemException extends RuntimeException {

    /** 代码 */
    private int code;

    /** 异常信息 */
    private String msg;

    /**
     * 获取code
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取信息
     *
     * @return {@link String}
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 系统异常
     *
     * @param httpCodeEnum http代码枚举
     */
    public SystemException(@NotNull AppHttpCodeEnum httpCodeEnum) {
        super ( httpCodeEnum.getMsg () );
        this.code = httpCodeEnum.getCode ();
        this.msg = httpCodeEnum.getMsg ();
    }
}
