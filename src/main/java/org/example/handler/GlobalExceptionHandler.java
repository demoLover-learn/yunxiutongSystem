package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //业务异常：原样返回
    @ExceptionHandler(BusinessException.class)
    public Result<String> handlderBusiness(BusinessException e){
        log.info("业务异常:{}",e.getMessage());
        return Result.error(e.getMessage());
    }



    //系统异常：npe,参数,sql,包装异常类
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRunTimeException(RuntimeException e){
        log.error("系统异常", e);
        return Result.error("服务器内部异常，请联系管理员");
    }

    //兜底
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e){
        log.error("系统异常:{}",e.getMessage());
        return Result.error("服务器内部异常，请联系管理员");
    }

}
