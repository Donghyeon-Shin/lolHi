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
	
	// needToLoginInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("needToLoginInterceptor")
	HandlerInterceptor needToLoginInterceptor;


	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**").excludePathPatterns("/error");
		
		// 로그인 없이도 접속할 수 있는 URI 전부 기술
				registry.addInterceptor(needToLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/")
						.excludePathPatterns("/resource/**").excludePathPatterns("/usr/home/main")
						.excludePathPatterns("/usr/member/login").excludePathPatterns("/usr/member/doLogin")
						.excludePathPatterns("/usr/member/join").excludePathPatterns("/usr/member/doJoin")
						.excludePathPatterns("/usr/article/list").excludePathPatterns("/usr/article-*/list")
						.excludePathPatterns("/usr/article-*/detail")
						.excludePathPatterns("/usr/member/find*").excludePathPatterns("/usr/member/doFind*")
						.excludePathPatterns("/error");

	
	}
	
}