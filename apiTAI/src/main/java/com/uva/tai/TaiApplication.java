package com.uva.tai;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
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
			//registry.addResourceHandler("/**")
			//	.addResourceLocations("classpath:/static/");
			registry.addResourceHandler("/uploads/**").addResourceLocations("classpath:/uploads/")
					.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		}
	}

}
