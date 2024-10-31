package cc.c3q.algo.evolve.fms.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cc.c3q.algo.evolve.fms.web.interceptor.LoginInterceptor;

@Configuration
public class ConfigInterceptor implements WebMvcConfigurer
{
	@Autowired
	public LoginInterceptor loginInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry)
	{
//		registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/session/**");
	}
}
