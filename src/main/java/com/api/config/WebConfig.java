package com.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api.interceptor.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Autowired
	private LoggingInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(interceptor)
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/auth/**");
			
	}

}
