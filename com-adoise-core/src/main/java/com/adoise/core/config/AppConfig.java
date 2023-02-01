package com.adoise.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class AppConfig {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error/403");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { cl.lasdelicias.webapp.feature.view.xml.ClienteList.class });
		return marshaller;
	}
	*/
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setIgnoreUnresolvablePlaceholders(true);
		return c;
	}
	
	/*
	@Bean
	public Product productPlate() {
		Product plate = new Plate();
		return plate;
	}

	@Bean
	public Product drinkProduct() {
		Product drink = new Drink();
		return drink;
	}

	@Bean
	public FileModel productFile() {
		FileModel productFile = new FileProduct();
		return productFile;
	}

	@Bean
	public FileModel clientFile() {
		FileModel clientFile = new FileClient();
		return clientFile;
	}
	
	@Bean
	public FileModel portalFile() {
		FileModel portalFile = new FilePortal();
		return portalFile;
	}
	*/
}
