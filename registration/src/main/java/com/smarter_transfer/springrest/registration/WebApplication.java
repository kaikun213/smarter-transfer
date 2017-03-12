package com.smarter_transfer.springrest.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import common.app.web.config.JsonConfiguration;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class WebApplication {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Object[]{WebApplication.class, "registration.xml",JsonConfiguration.class}, args);
	}

}
