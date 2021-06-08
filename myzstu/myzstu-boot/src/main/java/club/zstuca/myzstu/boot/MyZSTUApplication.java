package club.zstuca.myzstu.boot;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-23 19:08
 */
@EnableSimbot
@EnableScheduling
@ComponentScan(basePackages = "club.zstuca.myzstu")
@MapperScan(basePackages = "club.zstuca.myzstu.**.mapper")
@SpringBootApplication
public class MyZSTUApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyZSTUApplication.class, args);
    }
}
