package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //业务异常-消息返回前端
    @ExceptionHandler(RuntimeException.class)
    public Result handleRunTimeException(RuntimeException e){
        log.info("业务异常:{}"+e.getMessage());
        return Result.error(e.getMessage());
    }

    //兜底
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("系统异常:{}"+e.getMessage());
        return Result.error("服务器内部异常，请联系管理员");
    }

}
