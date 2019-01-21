/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.model;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author RANIL L.A
 */
@Entity
@Table(name="dog_breed")
public class DogBreed {

	@Id
	@Column(name="breed_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dogBreedId;
	
	@Column(name="breed_name")
	private String breedName;
	
	@Basic
	@Column(name="uploaded_on")
	private ZonedDateTime uploadedOn;
	
	@Column(name="uploaded_location")
	private String uploadedLocation;
	
	public DogBreed(){
		//default constructor
	}
		
	public Long getDogBreedId() {
		return dogBreedId;
	}
	public void setDogBreedId(Long dogBreedId) {
		this.dogBreedId = dogBreedId;
	}
	public String getBreedName() {
		return breedName;
	}
	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}
	public ZonedDateTime getUploadedOn() {
		return uploadedOn;
	}
	public void setUploadedOn(ZonedDateTime uploadedOn) {
		this.uploadedOn = uploadedOn;
	}
	public String getUploadedLocation() {
		return uploadedLocation;
	}
	public void setUploadedLocation(String uploadedLocation) {
		this.uploadedLocation = uploadedLocation;
	}
}
