/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yabonza.assignment.ranil.model.DogBreed;

/**
 * @author RANIL L.A
 */
@Repository("petDogsRepository")
public interface PetDogsRepository extends JpaRepository<DogBreed, Long> {

	DogBreed save(DogBreed dogBreed);
	
	DogBreed getOne(Long id);
	
	void deleteById(Long id);
	
	List<DogBreed> findByBreedName(String breedName);
	
	Page<DogBreed> findAll(Pageable pageable);
}
