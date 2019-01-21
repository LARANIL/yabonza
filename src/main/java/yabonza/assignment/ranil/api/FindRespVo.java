/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RANIL L.A
 */
public class FindRespVo extends RespBaseVo {

	private List<String> breeds = new ArrayList<>();

	public List<String> getBreeds() {
		return breeds;
	}

	public void setBreeds(List<String> breeds) {
		this.breeds = breeds;
	}
}
