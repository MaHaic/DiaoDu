package cc.c3q.algo.evolve.fms.web.config;


import javax.servlet.Servlet;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class ConfigEnv
{
	public final int serverPort = 38000;
	
	@Bean
	public ServletWebServerFactory tomcatServletWebServerFactory()
	{
		return new TomcatServletWebServerFactory(serverPort);
	}
	
	@Bean
	public ServletRegistrationBean<Servlet> dispatcherServlet(WebApplicationContext app)
	{
		ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<Servlet>();
		bean.setServlet(new DispatcherServlet(app));
		bean.addUrlMappings("/");
		return bean;
	}
}
