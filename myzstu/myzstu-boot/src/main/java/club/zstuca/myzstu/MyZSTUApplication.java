package club.zstuca.myzstu;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ShenTuZhiGang
 */
@EnableSimbot
@EnableScheduling
@MapperScan(basePackages = "club.zstuca.myzstu.**.mapper")
@SpringBootApplication
public class MyZSTUApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyZSTUApplication.class, args);
    }

}
