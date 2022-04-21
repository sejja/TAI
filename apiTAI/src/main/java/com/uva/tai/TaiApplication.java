package com.uva.tai;

import java.util.concurrent.TimeUnit;

import com.uva.tai.security.JWTAuthorizationFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class TaiApplication {


	public static void main(String[] args) {
		SpringApplication.run(TaiApplication.class, args);
	}

	@Configuration
	public class WebConfiguration extends WebMvcConfigurationSupport {

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) { 
			registry.addResourceHandler("/uploads/**").addResourceLocations("file:///uploads/",
					"file:uploads/")//modificacion para Docker
					.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		}
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.cors().and().csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.GET, "/tai/code").permitAll()
					.antMatchers(HttpMethod.GET, "/tai").permitAll()
					.antMatchers(HttpMethod.GET, "/tai/{id}").permitAll()
					.antMatchers(HttpMethod.GET, "/uploads/**").permitAll()
					.antMatchers(HttpMethod.POST, "/tai/{id}").permitAll()
					.antMatchers(HttpMethod.GET, "/**").authenticated()
					.antMatchers(HttpMethod.POST, "/**").authenticated()
					.antMatchers(HttpMethod.PUT, "/**").authenticated()
					.antMatchers(HttpMethod.DELETE, "/**").authenticated()
					.antMatchers(HttpMethod.OPTIONS, "/**").authenticated()
					.anyRequest().authenticated();
		}
	}

}
