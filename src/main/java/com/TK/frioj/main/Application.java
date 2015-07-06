package com.TK.frioj.main;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@ComponentScan(basePackages = "com")
@PropertySource({"file:/opt/frioj/friojFiles/settings/jdbc.properties","file:/opt/frioj/friojFiles/settings/mail.properties"})
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate(){
		return new NamedParameterJdbcTemplate(this.dataSource());
	}
	
	
	@Value( "${jdbc.url}" )
	private String jdbcUrl;
	
	@Value( "${jdbc.username}" )
	private String jdbcUsername;
	
	@Value( "${jdbc.password}" )
	private String jdbcPassword;
	
	@Bean 
	public DataSource dataSource(){
		BasicDataSource datasource = new BasicDataSource();
		
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl(jdbcUrl);
		datasource.setPassword(jdbcPassword);
		datasource.setUsername(jdbcUsername);
		datasource.setInitialSize(100);
		datasource.setMaxActive(400);
		
		return datasource;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	
	@Value( "${mail.host}" )
	private String mailHost;
	
	@Value( "${mail.port}" )
	private int mailPort;
	
	@Value( "${mail.username}" )
	private String mailUserName;
	
	@Value( "${mail.password}" )
	private String mailPassword;
	
	@Bean
	public MailSender mailSender(){
		
		JavaMailSenderImpl ms = new org.springframework.mail.javamail.JavaMailSenderImpl();
		ms.setHost(mailHost);
		ms.setPort(mailPort);
		ms.setUsername(mailUserName);
		ms.setPassword(mailPassword);
		Properties pr = new Properties();
		pr.put("mail.smtp.auth", true);
		pr.put("mail.smtp.starttls.enable", true);
		pr.put("mail.transport.protocol", "smtp");
		ms.setJavaMailProperties(pr);
		
		return ms;
	}
	

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	 
	
}
