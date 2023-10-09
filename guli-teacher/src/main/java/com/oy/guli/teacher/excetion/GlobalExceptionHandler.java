package com.oy.guli.teacher.excetion;

import com.oy.guli.common.result.ExceptionUtil;
import com.oy.guli.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @Author OY
 * @Date 2021/2/28
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 1.全局异常类型 Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("出错了");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result ArithmeticExceptionError(EduException e){
        e.printStackTrace();
        return Result.error().message("除数不能为0");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e){
        e.printStackTrace();
        //log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
