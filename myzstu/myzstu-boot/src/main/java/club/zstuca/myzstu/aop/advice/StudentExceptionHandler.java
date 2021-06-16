package club.zstuca.myzstu.aop.advice;

import club.zstuca.myzstu.common.R;
import club.zstuca.myzstu.exception.StudentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-16 16:37
 */
@Slf4j
@ControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(StudentException.class)
    @ResponseBody
    public R<?> handle(HttpServletRequest request, HttpServletResponse response,StudentException ex){
        return R.builder().code(ex.getCode()).msg(ex.getMessage()).build();
    }
}
