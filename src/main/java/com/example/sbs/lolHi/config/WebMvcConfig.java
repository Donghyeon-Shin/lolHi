package com.example.sbs.lolHi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("beforeActionInterceptor")
	HandlerInterceptor beforeActionInterceptor;


	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**");
		//registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/usr/article/list");
	}
	
}