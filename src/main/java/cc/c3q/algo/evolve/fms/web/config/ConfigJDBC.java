package cc.c3q.algo.evolve.fms.web.config;

import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cc.c3q.mysql.sqlControl.SqlControl;

@Configuration
public class ConfigJDBC
{
	@Bean
	public DataSource dataSource() throws Exception
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream("jdbc.properties"));
		
		return BasicDataSourceFactory.createDataSource(properties);
	}
	
	@Bean
	public SqlControl sqlControl(DataSource dataSource)
	{
		return new SqlControl(dataSource);
	}
}
