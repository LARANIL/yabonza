/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import yabonza.assignment.ranil.iao.AwsS3IAO;
import yabonza.assignment.ranil.iao.YabonzaIAO;
import yabonza.assignment.ranil.model.DogBreed;
import yabonza.assignment.ranil.repo.PetDogsRepository;
import yabonza.assignment.ranil.util.PetDogUtility;
import yabonza.assignment.ranil.vo.DogBreedVo;

/**
 * @author RANIL L.A
 */
public class PetDogServiceImpl implements PetDogService {

	private Logger logger = LoggerFactory.getLogger(PetDogServiceImpl.class);
	
	@Autowired
	private PetDogUtility petDogUtility;
	
	@Autowired
	private YabonzaIAO yabonzaIAO;
		
	@Autowired
	private AwsS3IAO awsS3IAO;
	
	@Autowired
	private PetDogsRepository petDogsRepository;
	

	/**
	 * This method attempts to create a new dog breed record in the back 
	 * end database. When invoked following actions would be executed.
	 * <p>
	 * <ol>
	 * <li>Access Yabonza REST API end point and retrieve 
	 * a random dog breed</li>
	 *<li>Upload a successfully retrieve random dog breed image to
	 *a pre-configured AWS S3 bucket</li>
	 *<li>Once the S3 upload is successful, create a record in the
	 *back end dog_breed table with the uploaded location URL ,
	 *time stamp (UTC) and the dog breed</li>
	 * </ol><p>
	 *<br> 
	 *Transactions annotation is only expected when a dog breed 
	 *is saved to the back end MYSQL database. 
	 *<br>
	 *Retrieval from Yabonza API or uploading to AWS S3 bucket does not 
	 *participated  in this transaction and doing so would make no sense as 
	 *Yabonza API and AWS S3 bucket areNOT transactional data stores.
	 **/
	@Override
	@Transactional
	public DogBreed createDogBreed() {
		
		logger.info("Invoking createDogBreed now ... ");
		DogBreed dogBreed = null;
		
		String yabonzaURL = petDogUtility.withYabonzaApiURL();
		String s3BucketName = petDogUtility.withWSS3BucketName();
		String s3BucketFullURL = petDogUtility.withFullAWSS3BucketURL();
		
		//retrieve dog breed from the API end point
		DogBreedVo dogBreedVo = yabonzaIAO.retriveBreed(yabonzaURL);
		
		//upload the retrieved dog breed image to the AWS S3 bucket
		boolean uploaded = awsS3IAO.upload(s3BucketName, dogBreedVo);
		
		if(uploaded){
			try{
				dogBreed = new DogBreed();
				dogBreed.setBreedName(dogBreedVo.getBreedName());
				
				String uploadedLoc = s3BucketFullURL + "/" +dogBreedVo.getUploadedFileName();
				dogBreed.setUploadedLocation(uploadedLoc);
				dogBreed.setUploadedOn(ZonedDateTime.now());
				
				//save the retrieved dog breed to the back end storage
				dogBreed = petDogsRepository.save(dogBreed);
			}catch(Exception ex){
				logger.error("An exception occurred when attempting to save a random dog breed. "
						+ "Retrieving from Yabonza API , Upload to S3 bucket were successful. But saving "
						+ "this back to the back end database failed!"); 
				dogBreed = null;
			}
		}
		
		return dogBreed;
	}


	@Override
	public DogBreed getDogBreed(Long id) {
		
		logger.info("Invoking getDogBreed now ... ");
		DogBreed existsingDogBreed = null;
		
		try{
			if(id != null && id > 0){
				existsingDogBreed = petDogsRepository.getOne(id);
				existsingDogBreed.getBreedName();
			}
		}catch(Exception ex){
			logger.error("An exception occured when attepting to "
					+ "retrieve a dog breed. Message is " + ex.getMessage());
			existsingDogBreed = null;
		}
		return existsingDogBreed;
	}


	@Override
	@Transactional
	public boolean removeDogBreed(Long id) {
		
		logger.info("Invoking removeDogBreed now ... ");
		boolean deleted = false;
		
		try{
			if(id != null && id > 0){
				petDogsRepository.deleteById(id);
				deleted = true;
			}
		}catch(Exception ex){
			logger.error("An exception occured when attepting to "
					+ "delete a dog breed. Message is " + ex.getMessage());
			ex.printStackTrace();
			deleted = false;
		}
		
		return deleted;
	}


	@Override
	public List<DogBreed> searchDogBreed(String breedName) {
		
		logger.info("Invoking searchDogBreed now ... ");
		List<DogBreed> resutls = new ArrayList<>();
		
		try{
			if(breedName != null && !breedName.trim().equalsIgnoreCase("")){
				resutls = petDogsRepository.findByBreedName(breedName);
			}
		}catch(Exception ex){
			logger.error("An exception occurred when attempting to search "
					+ "dog breeds. The message is " + ex.getMessage());
			ex.printStackTrace();
			resutls = new ArrayList<>();
		}
		
		return resutls;
	}


	@Override
	public List<String> findBreedNames(Integer page, Integer size) {
		
		logger.info("Invoking findBreedNames now ... ");
		List<String> breedNames = new ArrayList<>();
		
		try{
			if(page != null && size != null){
				
				Pageable pageable = PageRequest.of(page, size);
				Page<DogBreed> dogBreedsPage = petDogsRepository.findAll(pageable);
				
				if(dogBreedsPage != null && !dogBreedsPage.isEmpty()){
					List<DogBreed> pageContent = dogBreedsPage.getContent();
					if(pageContent != null && pageContent.size() > 0){
						//extract only what is needed
						Consumer<DogBreed> func = (db) -> {breedNames.add(db.getBreedName());}; 
						pageContent.forEach(func);
					}
				}
			}
		}catch(Exception ex){
			logger.error("An exception occurred when attempting to find "
					+ "existing breed names. The message is " + ex.getMessage()); 
			ex.printStackTrace();
		}
		
		return breedNames;
	}

	
}
