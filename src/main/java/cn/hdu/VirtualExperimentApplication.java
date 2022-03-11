package cn.hdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"cn.hdu.virtual_experiment.*"})
public class VirtualExperimentApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualExperimentApplication.class, args);
	}


}
