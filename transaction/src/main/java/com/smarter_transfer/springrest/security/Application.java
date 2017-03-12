package com.smarter_transfer.springrest.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import common.app.web.config.JsonConfiguration;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Object[]{Application.class, "transaction.xml",JsonConfiguration.class}, args);
	}

}