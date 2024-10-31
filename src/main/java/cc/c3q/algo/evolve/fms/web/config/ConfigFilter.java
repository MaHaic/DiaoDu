package cc.c3q.algo.evolve.fms.web.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import cc.c3q.algo.evolve.fms.web.filter.AcrossAjaxFilter;

@Configuration
public class ConfigFilter
{
	@Bean
	public FilterRegistrationBean<Filter> characterEncodingFilter()
	{
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
		bean.setFilter(new CharacterEncodingFilter("UTF-8"));
		bean.addUrlPatterns("/*");
		bean.setOrder(1);
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> acrossAjaxFilter()
	{
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
		bean.setFilter(new AcrossAjaxFilter());
		bean.addUrlPatterns("/api/*");
		bean.setOrder(2);
		return bean;
	}
}
