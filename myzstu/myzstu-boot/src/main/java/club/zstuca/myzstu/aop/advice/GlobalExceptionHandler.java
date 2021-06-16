package club.zstuca.myzstu.aop.advice;


import club.zstuca.myzstu.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-03-21 16:51
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        HttpStatus status = getStatus(request);
        log.warn(ex.getMessage(), ex);
        return R.builder()
                .code(status.value())
                .msg(ex.getMessage())
                .build();
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
