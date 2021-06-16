package club.zstuca.myzstu.robot;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2021-05-07 19:02
 */
@EnableSimbot
@SpringBootApplication
public class MyZSTURobotApplication {

    public static void main(String[] args){
        SpringApplication.run(MyZSTURobotApplication.class, args);
    }

}
