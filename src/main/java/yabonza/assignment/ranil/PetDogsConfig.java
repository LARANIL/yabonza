/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import yabonza.assignment.ranil.iao.AwsS3IAO;
import yabonza.assignment.ranil.iao.AwsS3IAOImpl;
import yabonza.assignment.ranil.iao.YabonzaIAO;
import yabonza.assignment.ranil.iao.YabonzaIAOImpl;
import yabonza.assignment.ranil.service.PetDogService;
import yabonza.assignment.ranil.service.PetDogServiceImpl;
import yabonza.assignment.ranil.util.PetDogUtility;

/**
 * @author RANIL L.A
 * 
 * This class produces all the Spring Boot specific configurations that are necessary 
 * for this application to run properly
 */
@Configuration
@EnableJpaRepositories(
		basePackages={"yabonza.assignment.ranil.model",
				"yabonza.assignment.ranil.repo"},
		transactionManagerRef = "petDogsTxnManager",
		entityManagerFactoryRef = "petDogsEntityManager"
)
public class PetDogsConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public PetDogService createPetDogService(){
		return new PetDogServiceImpl();
	}
	
	@Bean(initMethod="getInstance")
	public PetDogUtility createPetDogUtility(){
		return new PetDogUtility();
	}
	
	//====================Integration Access Objects===========
	
	@Bean
	public YabonzaIAO createYabonzaIAO(){
		return new YabonzaIAOImpl();
	}
	
	@Bean
	public AwsS3IAO createAwsS3IAO(){
		return new AwsS3IAOImpl();
	}
			
	//==================Rest / Json specific ====================
	
	@Bean
	public RestTemplate createRestTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	@ConfigurationProperties(prefix="petdogs.datasource")
	public DataSource getDataSource(){
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean petDogsEntityManager(){
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(getDataSource());
		entityManager.setPackagesToScan(new String[]{"yabonza.assignment.ranil.model"});
		
		HibernateJpaVendorAdapter vAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vAdapter); 
		
		Map<String,Object> props = new HashMap<>();
		props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		
		entityManager.setJpaPropertyMap(props); 
		
		return entityManager;
	}
	
	@Bean
	@Primary
	public PlatformTransactionManager petDogsTxnManager(){
		JpaTransactionManager txnMan = new JpaTransactionManager();
		txnMan.setEntityManagerFactory(petDogsEntityManager().getObject());
		return txnMan; 
	}
	
}
