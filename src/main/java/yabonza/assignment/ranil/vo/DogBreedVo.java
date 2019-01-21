/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.vo;

/**
 * This is a helper class which carries the information
 * about each random dog breed retrieve from the
 * given Yabonza REST API. 
 * 
 * @author RANIL L.A
 */
public class DogBreedVo {

	private static final String STATUS_SUCCES = "success";
	
	private String status;
	private String message;
	private String breedName;
	private String imageURL;
	
	//upon uploading to S3 this would carry the 
	//file name of uploaded entry
	private String uploadedFileName;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBreedName() {
		return breedName;
	}
	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	
	public boolean healthy(){
		boolean healthy = false;
		
		try{
			if(this.status != null && this.status.trim().equalsIgnoreCase(STATUS_SUCCES) 
					&& this.message != null && !this.message.trim().equalsIgnoreCase("") ){
				
				//the result is received successful with non-empty image URL
				//checking if a breed name available
				String[] urlPathTokens =  this.message.trim().split("/");
				
				if(urlPathTokens != null && urlPathTokens.length > 0){
					if(urlPathTokens.length == 6){
						String breed = urlPathTokens[urlPathTokens.length - 2];
						if(breed != null && !breed.trim().equalsIgnoreCase("")){
							this.breedName = breed;
							this.imageURL = message;
							healthy = true;
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return healthy;
	}
}
