package cn.edu.zstu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ZstuHelperTransferApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ZstuHelperTransferApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ZstuHelperTransferApplication.class, args);
	}

}
