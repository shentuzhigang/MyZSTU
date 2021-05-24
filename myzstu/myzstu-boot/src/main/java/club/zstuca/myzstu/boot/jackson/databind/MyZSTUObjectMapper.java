package club.zstuca.myzstu.boot.jackson.databind;

import club.zstuca.myzstu.common.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-23 23:11
 */
@Component
public class MyZSTUObjectMapper extends ObjectMapper {
    public MyZSTUObjectMapper(){
        // 统一返回数据的输出风格
        // 下划线风格json
        this.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // null的属性不序列化
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化Date日期时以timestamps输出，默认true
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 将日期调整为上下文时区，默认true
        this.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        // 在遇到未知属性的时候不抛出异常，默认true
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //该特性允许parser可以识别"Not-a-Number" (NaN)标识集合作为一个合法的浮点数，默认false
        this.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //Date序列化和反序列化
        this.setDateFormat(new SimpleDateFormat(Constants.DEFAULT_DATE_TIME_PATTERN));
        //注册模块
        this.registerModule(javaTimeModule());
    }

    /**
     * @return JavaTimeModule
     * @see com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
     */
    private JavaTimeModule javaTimeModule() {
        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DEFAULT_TIME_PATTERN)));
        return javaTimeModule;
    }
}
