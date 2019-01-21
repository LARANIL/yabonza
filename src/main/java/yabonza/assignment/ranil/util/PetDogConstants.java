/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.util;

/**
 * @author RANIL L.A
 */
public class PetDogConstants {

	public static final String REST_API_ENDPOINT_PRODUCE = "/dogs";
	public static final String REST_API_ENDPOINT_RETRIEVE = "/dogs/{id}";
	public static final String REST_API_ENDPOINT_REMOVE = "/dogs/{id}";
	public static final String REST_API_ENDPOINT_SEARCH = "/dogs?breedName=<breedName>";
	public static final String REST_API_ENDPOINT_FIND = "/dogs?size=<pageSize>&page=<pageNo>";
	
	public static final String OPERATION_SUCCESS = "success";
	public static final String OPERATION_FAILURE = "failed";
	
	public static final String DEFAULT_TIMEZONE_ID = "Australia/Sydney";
	
}
