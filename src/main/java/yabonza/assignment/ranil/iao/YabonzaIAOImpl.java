/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.iao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import yabonza.assignment.ranil.vo.DogBreedVo;

/**
 * This is an Integration Access Object (IAO) to access the Yabonza REST
 * API end-point and de-serialize the resulting JSON content into POJO 
 * object instances
 * 
 * @author RANIL L.A
 */
public class YabonzaIAOImpl implements YabonzaIAO {

	private Logger logger = LoggerFactory.getLogger(YabonzaIAOImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public DogBreedVo retriveBreed(String api) {
		
		logger.info("Attempting to retrive dog breeds from the given Api endpoint : [" + api + "]");
		DogBreedVo dogBreedVo = null;
		
		try{
		
			HttpEntity<String> httpEntity = new HttpEntity<String>("");
			ResponseEntity<DogBreedVo> breedVoResp = restTemplate.exchange(
					api, HttpMethod.GET, httpEntity, DogBreedVo.class);
			dogBreedVo = breedVoResp.getBody();
		
			logger.info("Attempt to print the breed name");
			if(dogBreedVo != null && dogBreedVo.healthy()){
				logger.info(dogBreedVo.getBreedName());
				logger.info(dogBreedVo.getImageURL());
			}
			
		}catch(Exception ex){
			logger.error("An exception occurred when attemtping to find a "
					+ "random dog breed from the Api : [" + api + "]");
		}
		
		return dogBreedVo;
	}
	
}
