package io.github.sliverkiss.handler.exception;


import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理程序
 *
 * @author SliverKiss
 * @apiNote
 * @date 2023/2/8
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 系统异常处理程序
     *
     * @param e e
     *
     * @return {@link ResponseResult}
     */
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error ( "出现异常", e );
        // 从异常对象中获取提示信息，封装返回
        return ResponseResult.errorResult ( e.getCode (), e.getMessage () );
    }

    /**
     * 异常处理程序
     *
     * @param e 异常
     *
     * @return {@link ResponseResult}
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        // 打印异常信息
        log.error ( "出现异常", e );
        // 从异常对象中获取提示信息，封装返回
        return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), e.getMessage () );
    }
}
