/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.iao;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import yabonza.assignment.ranil.util.PetDogUtility;
import yabonza.assignment.ranil.vo.DogBreedVo;

/**
 * @author RANIL L.A
 */
public class AwsS3IAOImpl implements AwsS3IAO {

	private Logger logger = LoggerFactory.getLogger(AwsS3IAOImpl.class);
	
	@Autowired
	private PetDogUtility petDogUtility; 
	
	/* (non-Javadoc)
	 * @see yabonza.assignment.ranil.iao.AwsS3IAO#upload(java.lang.String, yabonza.assignment.ranil.vo.DogBreedVo)
	 */
	@Override
	public boolean upload(String bucketName, DogBreedVo breedVo) {
		
		logger.info("Attempting to upload object data into a given "
				+ "AWS S3 bucket " + bucketName + "]");
		boolean uploaded = false;
		
		if(breedVo != null ){
			try{
				
				AmazonS3 s3Client = AmazonS3ClientBuilder
						.standard()
						.withRegion(Regions.US_EAST_1)
						.withCredentials(new EnvironmentVariableCredentialsProvider()).build();
				if(bucketName == null || bucketName.trim().equalsIgnoreCase("")){
					bucketName = petDogUtility.withWSS3BucketName();
				}
				
				boolean hasBucketName = (bucketName != null && 
						!bucketName.trim().equalsIgnoreCase(""));
				
				if(hasBucketName){
					
					String uploadedFileName = UUID.randomUUID().toString() + ".jpg";
					
					try(InputStream input = new URL(breedVo.getImageURL()).openStream()){
						
						ObjectMetadata objMeta = new ObjectMetadata();
						objMeta.setContentType("image/jpg");
						
						PutObjectRequest putReq = new PutObjectRequest(bucketName, 
								uploadedFileName, input, objMeta);
						PutObjectResult putResult = s3Client.putObject(putReq);
						String eTag = putResult.getETag();
						logger.info("The uploaded Etag is : " + eTag);
						
						breedVo.setUploadedFileName(uploadedFileName); 
						uploaded = true;
						
					}catch(Exception ex){
						throw ex;
					}
				}
			}catch(Exception ex){
				logger.error("Exception occured when attepting to upload the "
						+ "given dog breed specific image to AWS S3 bucket. "
						+ "The message is " + ex.getMessage());
				ex.printStackTrace();
				uploaded = false;
			}
		}
		
		return uploaded;
	}

}
