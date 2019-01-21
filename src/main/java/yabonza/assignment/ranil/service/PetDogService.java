/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.service;

import java.util.List;

import org.springframework.stereotype.Service;

import yabonza.assignment.ranil.model.DogBreed;

/**
 * @author RANIL L.A
 */
@Service
public interface PetDogService {
	
	DogBreed createDogBreed();
	
	DogBreed getDogBreed(Long id);
	
	boolean removeDogBreed(Long id);
	
	List<DogBreed> searchDogBreed(String breedName);
	
	List<String> findBreedNames(Integer page , Integer size);
}
