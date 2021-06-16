package cn.edu.zstu.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cn.edu.zstu")
@MapperScan(basePackageClasses = cn.edu.zstu.service.StudentDAO.class)
public class ZstuHelperApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ZstuHelperApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ZstuHelperApplication.class, args);
	}
	
	

}
