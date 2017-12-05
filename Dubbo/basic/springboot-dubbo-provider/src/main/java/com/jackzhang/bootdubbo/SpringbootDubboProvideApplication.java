package com.jackzhang.bootdubbo;

import com.jackzhang.bootdubboapi.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource(value = { "classpath:dubbo/providers.xml" })
public class SpringbootDubboProvideApplication {

	private DemoService demoService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboProvideApplication.class, args);
	}
}
