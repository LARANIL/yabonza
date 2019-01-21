/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author RANIL L.A
 * 
 * This is the main spring boot application which starts the PetDogs REST API end points
 */
@EnableAutoConfiguration
@ComponentScan
@PropertySources({@PropertySource("classpath:petdogs.properties"),
	@PropertySource("classpath:petdogs-db.properties")})
public class PetDogsApplication {

	public static void main(String[] params){
		SpringApplication.run(PetDogsApplication.class, params);
	}
	
}
