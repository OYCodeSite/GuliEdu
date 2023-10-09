package com.oy.guli.common.exceptionhandler;

import com.oy.guli.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlers {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理..");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody //为了返回数据
    public Result error(GuliException e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
