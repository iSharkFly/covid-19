package com.ossez.covid19.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//import org.apache.log4j.Level;

@EnableScheduling
@ComponentScan("com.ossez.covid19")
@SpringBootApplication
public class Application extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(new RequestContextListener());
	}

	/**
	 * Main function for service application
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		SpringApplication.run(Application.class, args);

	}

}
