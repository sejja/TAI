package com.uva.microservicio;

import com.uva.microservicio.security.JWTAuthorizationFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UsersApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(UsersApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.cors().and().csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/**").authenticated()
					.antMatchers(HttpMethod.PUT, "/**").authenticated()
					.antMatchers(HttpMethod.DELETE, "/**").authenticated()
					.antMatchers(HttpMethod.GET, "/**").authenticated()
					.anyRequest().authenticated();
					
		}
	}
}