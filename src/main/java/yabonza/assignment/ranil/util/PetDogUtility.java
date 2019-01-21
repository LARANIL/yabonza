/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.util;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static yabonza.assignment.ranil.util.PetDogConstants.DEFAULT_TIMEZONE_ID;

/**
 * This is a utility class that provides some of the useful utility 
 * method for this application. This utility class may be used across
 * all layers 
 * 
 * @author RANIL L.A
 */

public class PetDogUtility {

	private static PetDogUtility _instance = new PetDogUtility();
	
	private static Logger logger = LoggerFactory.getLogger(PetDogUtility.class);
	
	@Autowired
	private Environment env;
	
	public PetDogUtility getInstance(){
		logger.info("Returning the statically created instance ... ");
		return _instance;
	}
	
	
	/**
	 *Provides the base URL of the AWS S3 bucket based on the properties
	 *configured for the key "petdogs.aws.s3.bucket.location"
	 **/
	public String withAWSS3BucketBaseURL(){
		
		logger.info("Attempting to find the configured AWS S3 bucket base URL "); 
		
		String baseURL = null;
		
		try{
			
			baseURL = env.getProperty("petdogs.aws.s3.bucket.location");
			logger.info("The configured bucket location is : [" + baseURL + "]"); 			
		
		}catch(Exception ex){
			logger.error("Exception occurred when attempting to access configured "
					+ "AWS S3 bucket base URL . The message is " + ex.getMessage());
		}
		
		return baseURL;
	}
	
	/**
	 *Provides the complete URL of the AWS S3 bucket based on the properties
	 *configured for the keys "petdogs.aws.s3.bucket.location" and 
	 *"petdogs.aws.s3.bucket.name"
	 **/
	public String withFullAWSS3BucketURL(){
		
		logger.info("Attempting to find the configured AWS S3 bucket URL "); 
		
		String bucketURL = null;
		
		try{
			
			String bucketLoc = env.getProperty("petdogs.aws.s3.bucket.location");
			logger.info("The configured bucket location is : [" + bucketLoc + "]"); 
			
			String bucketName = env.getProperty("petdogs.aws.s3.bucket.name");
			logger.info("The configured bucket name is : [" + bucketName + "]"); 
			
			bucketURL = bucketLoc + bucketName;
			logger.info("The configured bucket URL is : [" + bucketURL + "]"); 
			
		}catch(Exception ex){
			logger.error("Exception occurred when attempting to access configured "
					+ "AWS S3 bucket. The message is " + ex.getMessage());
		}
		
		return bucketURL;
	}

	
	/**
	 *Provides the configured AWS S3 bucket name only
	 **/
	public String withWSS3BucketName(){
		
		logger.info("Attempting to find the configured AWS S3 bucket name "); 
		
		String withWSS3BucketName = null;
		
		try{
			
			withWSS3BucketName = env.getProperty("petdogs.aws.s3.bucket.name");
			logger.info("The configured bucket name is : [" + withWSS3BucketName + "]"); 
			
		}catch(Exception ex){
			logger.error("Exception occurred when attempting to access configured "
					+ "AWS S3 bucket. The message is " + ex.getMessage());
		}
		
		return withWSS3BucketName;
	}
	
	/**
	 *Provides the configured AWS S3 bucket name only
	 **/
	public String withYabonzaApiURL(){
		
		logger.info("Attempting to find the configured REST API URL by Yabonza "); 
		
		String yabonzaURL = null;
		
		try{
			
			yabonzaURL = env.getProperty("petdogs.yabonza.rest.api.url");
			logger.info("The configured Yabonza REST api URL is : [" + yabonzaURL + "]"); 
			
		}catch(Exception ex){
			logger.error("Exception occurred when attempting to access configured "
					+ "Yabonza REST Api URL. The message is " + ex.getMessage());
		}
		
		return yabonzaURL;
	}
	
	/**
	 *Provides the time zone id configured for this application globally.
	 *Unless otherwise specified this time zone identity would be used 
	 *where necessary.
	 **/
	public ZoneId withConfiguredTimezoneId(){
		
		logger.info("Attempting to find the configured time zone identity"); 
		
		ZoneId zoneId = null;
		
		try{
			
			String timezoneId = env.getProperty("petdogs.base.timezone.id");
			logger.info("The configured time zone id is : [" + timezoneId + "]"); 
			if(timezoneId == null || timezoneId.trim().equalsIgnoreCase("")){
				//use the system configured defalt zone id
				timezoneId = DEFAULT_TIMEZONE_ID;
			}
			zoneId = ZoneId.of(timezoneId);
			
		}catch(Exception ex){
			logger.error("Exception occurred when attempting to access configured "
					+ "time zone identity . The message is " + ex.getMessage());
		}
		
		return zoneId;
	}
	
	public Long isValidIdentity(String id){
		
		Long idGiven = null;
		
		if(id != null && !id.trim().equalsIgnoreCase("")){
			try{
				idGiven = Long.parseLong(id.trim());
			}catch(Exception ex){
				logger.error("The value : [" + id + "] was unable to be "
						+ "parsed into a Long!"); 
				idGiven = null;
			}
		}
		
		return idGiven;
	}
	
	public Integer isValidInteger(String value){
		
		Integer valGiven = null;
		
		if(value != null && !value.trim().equalsIgnoreCase("")){
			try{
				valGiven = Integer.parseInt(value.trim());
			}catch(Exception ex){
				logger.error("The value : [" + value + "] was unable to be "
						+ "parsed into an integer!"); 
				valGiven = null;
			}
		}
		
		return valGiven;
	}
}
