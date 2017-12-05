package com.jackzhang.bootdubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource({ "classpath:dubbo/*.xml" })
public class SpringbootDubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboConsumerApplication.class, args);
	}
}
