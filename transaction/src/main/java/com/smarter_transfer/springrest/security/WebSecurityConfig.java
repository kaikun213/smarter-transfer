package com.smarter_transfer.springrest.security;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@ComponentScan(value="transaction.xml")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DriverManagerDataSource dataSource;
	 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("userId").passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureAuthentification(AuthenticationManagerBuilder auth) throws Exception {
    	auth.jdbcAuthentication().dataSource(dataSource)
    		.usersByUsernameQuery("SELECT userId, password, isDeleted FROM USER WHERE userId=?")
    		.authoritiesByUsernameQuery("select userId,'ROLE_USER' from USER where userId=?");
    }
}
