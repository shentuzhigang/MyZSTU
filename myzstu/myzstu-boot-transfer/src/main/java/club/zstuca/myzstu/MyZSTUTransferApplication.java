package club.zstuca.myzstu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author ShenTuZhiGang
 */
@SpringBootApplication
public class MyZSTUTransferApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MyZSTUTransferApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyZSTUTransferApplication.class);
    }

}
