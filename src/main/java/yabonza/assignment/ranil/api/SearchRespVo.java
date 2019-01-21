/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RANIL L.A
 */
public class SearchRespVo extends RespBaseVo {

	private List<PetDogVO> dogs = new ArrayList<>();

	public List<PetDogVO> getDogs() {
		return dogs;
	}
	public void setDogs(List<PetDogVO> dogs) {
		this.dogs = dogs;
	}
}
