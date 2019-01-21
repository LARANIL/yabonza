/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author RANIL L.A
 */
public class EndpointInfoVo extends RespBaseVo{

	private List<Map<String,String>> endpoints = new ArrayList<>();

	public List<Map<String, String>> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<Map<String, String>> endpoints) {
		this.endpoints = endpoints;
	}
}
