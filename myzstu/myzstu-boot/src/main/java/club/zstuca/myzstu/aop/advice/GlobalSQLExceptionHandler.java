package club.zstuca.myzstu.aop.advice;

import club.zstuca.myzstu.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-03-21 16:51
 */
@Slf4j
@RestControllerAdvice
public class GlobalSQLExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public R<?> sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return R.builder()
                    .code(50000)
                    .msg("该数据有关联数据，操作失败!")
                    .build();
        }
        return R.builder()
                .code(50000)
                .msg("数据库异常，操作失败!")
                .build();
    }
}