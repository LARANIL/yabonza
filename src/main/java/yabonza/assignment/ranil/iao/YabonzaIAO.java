/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.iao;

import yabonza.assignment.ranil.vo.DogBreedVo;

/**
 * @author RANIL L.A
 */
public interface YabonzaIAO {

	/**
	 *Attempt to retrieve a random dog breed specific information
	 **/
	DogBreedVo retriveBreed(String api);
}
