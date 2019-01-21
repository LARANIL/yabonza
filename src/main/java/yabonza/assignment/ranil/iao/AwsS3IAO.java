/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.iao;

import yabonza.assignment.ranil.vo.DogBreedVo;

/**
 * This is an integration access object which attempt to
 * access a given AWS S3 bucket for various bucket
 * operations.
 * 
 * @author RANIL L.A
 */
public interface AwsS3IAO {
	
	boolean upload(String bucketName , DogBreedVo breedVo);
}
