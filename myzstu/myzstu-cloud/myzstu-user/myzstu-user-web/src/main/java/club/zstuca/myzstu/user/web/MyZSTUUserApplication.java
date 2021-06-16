package club.zstuca.myzstu.user.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-22 20:48
 */
@MapperScan(basePackages = "club.zstuca.myzstu.user")
@SpringCloudApplication
public class MyZSTUUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyZSTUUserApplication.class, args);
    }
}
