package cc.c3q.algo.evolve.fms.web;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan
public class WebApp
{
	public static void main(String[] args) throws IOException
	{
		SpringApplication.run(WebApp.class, args);
	}
}
