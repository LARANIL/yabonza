/*
 * Copyright (c) 2019 RANIL L.A and/or its affiliates. All rights reserved. Use is subject to license terms.
 */
package yabonza.assignment.ranil.api;

import static yabonza.assignment.ranil.util.PetDogConstants.OPERATION_FAILURE;
import static yabonza.assignment.ranil.util.PetDogConstants.OPERATION_SUCCESS;
import static yabonza.assignment.ranil.util.PetDogConstants.REST_API_ENDPOINT_FIND;
import static yabonza.assignment.ranil.util.PetDogConstants.REST_API_ENDPOINT_PRODUCE;
import static yabonza.assignment.ranil.util.PetDogConstants.REST_API_ENDPOINT_REMOVE;
import static yabonza.assignment.ranil.util.PetDogConstants.REST_API_ENDPOINT_RETRIEVE;
import static yabonza.assignment.ranil.util.PetDogConstants.REST_API_ENDPOINT_SEARCH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import yabonza.assignment.ranil.model.DogBreed;
import yabonza.assignment.ranil.service.PetDogService;
import yabonza.assignment.ranil.util.PetDogUtility;

/**
 * @author RANIL L.A
 */
@RestController
@RequestMapping("/petdogs")
public class PetDogsController {
	
	private Logger logger = LoggerFactory.getLogger(PetDogsController.class);

	
	private PetDogService petDogService;
	private PetDogUtility petDogUtility;
	
	
	@GetMapping("/")
	public EndpointInfoVo index(){
		
		logger.info("Invoking the REST API endpoint / ");
		
		EndpointInfoVo respVo = new EndpointInfoVo();
		String operationStatus = OPERATION_FAILURE;
		List<Map<String,String>> endpoints  = new ArrayList<>();
		
		try{
			
			//list of supported end points 
			String[] enpointList = new String[]{REST_API_ENDPOINT_PRODUCE,
					REST_API_ENDPOINT_RETRIEVE, REST_API_ENDPOINT_REMOVE,
					REST_API_ENDPOINT_SEARCH, REST_API_ENDPOINT_FIND};
			
			//list of supported HTTP methods for those respective end points 
			String[] methodList = new String[]{HttpMethod.PUT.toString(), HttpMethod.GET.toString(), 
					HttpMethod.DELETE.toString(), HttpMethod.GET.toString(), HttpMethod.GET.toString()};
			
			int counter = 0;
			for(String endpoint : enpointList){
				
				String method = methodList[counter];
				Map<String,String> produceMap = new HashMap<>();
				produceMap.put(method, endpoint);
				endpoints.add(produceMap);
				
				counter++;
			}
		
			operationStatus = OPERATION_SUCCESS;
			
		}catch(Exception ex){
			logger.error("An exception occurred when attempting to list the supposted REST "
					+ "endpoints.  Message is "  + ex.getMessage()); 
			
		}
		
		respVo.setEndpoints(endpoints);		
		respVo.setMessage("This rest service supports given endpoint list");
		respVo.setStatus(operationStatus);
		
		return respVo;
	}
	
	/**
	 *This rest end point access a given REST API endpoint to retrive a random
	 *dog breed image and save it to the back end storage for qurying purpose
	 **/
	@PutMapping("/dogs")
	public RespBaseVo produce(){
		logger.debug("Invoking the REST API endpoint /dogs ");
		
		DogBreed newDogBreed = petDogService.createDogBreed();
		
		RespBaseVo pdv = new RespBaseVo();
		if(newDogBreed != null){
			try{
				pdv = new PetDogVO();
				PetDogVO.class.cast(pdv).setMessage("A new random dog breed is created.");
				PetDogVO.class.cast(pdv).setId(newDogBreed.getDogBreedId().longValue());
				PetDogVO.class.cast(pdv).setBreed(newDogBreed.getBreedName());
				PetDogVO.class.cast(pdv).setCreatedOn(newDogBreed.getUploadedOn().toString());
				PetDogVO.class.cast(pdv).setAccessUrl(newDogBreed.getUploadedLocation());
				PetDogVO.class.cast(pdv).setStatus(OPERATION_SUCCESS);
				
			}catch(Exception ex){
				logger.error("Exception occurred when translating to created random "
						+ "dog breed to be sent as a REST response." + ex.getMessage()); 
				pdv.setMessage("Unable to create a random dog breed!");
				pdv.setStatus(OPERATION_FAILURE);
			}
		}else{
			pdv.setMessage("Unable to create a random dog breed!");
			pdv.setStatus(OPERATION_FAILURE);
		}
		
		return pdv;
	}
	
	
	/**
	 *Returns a record matching the given identity. If there is no record found for
	 *the given identity , an empty response would be returned
	 **/
	@GetMapping("/dogs/{id}")
	public RespBaseVo retrieve(@PathVariable String id){
		logger.info("Invoking the REST API endpoint /dogs/{id} , the identity received is : ["  + id + "]");	
		
		RespBaseVo pdv = new RespBaseVo();
		
		if(id != null && !id.trim().equalsIgnoreCase("")){
			
			Long dogBreedIdentity = petDogUtility.isValidIdentity(id);
			if(dogBreedIdentity != null){
				
				//attempt to query a dog breed from the back end 
				//matching the given identity 
				DogBreed existingBreed = petDogService.getDogBreed(dogBreedIdentity);
				if(existingBreed != null){
					
					pdv = new PetDogVO();
					PetDogVO.class.cast(pdv).setMessage("Dog breed found.");
					PetDogVO.class.cast(pdv).setId(existingBreed.getDogBreedId().longValue());
					PetDogVO.class.cast(pdv).setBreed(existingBreed.getBreedName());
					PetDogVO.class.cast(pdv).setCreatedOn(existingBreed.getUploadedOn().toString());
					PetDogVO.class.cast(pdv).setAccessUrl(existingBreed.getUploadedLocation());
					PetDogVO.class.cast(pdv).setStatus(OPERATION_SUCCESS);
					
				}else{
					pdv.setMessage("Unable to retrieve a dog breed. The identity "
							+ "given has no corresponding record");
					pdv.setStatus(OPERATION_FAILURE);
				}
			}else{
				pdv.setMessage("Unable to retrieve a dog breed. The identity "
						+ "given is not invalid!");
				pdv.setStatus(OPERATION_FAILURE);
			}
		}
		
		return pdv;
	}
	
	
	/**
	 *Returns a record matching the given identity. If there is no record found for
	 *the given identity , an empty response would be returned
	 **/
	@DeleteMapping("/dogs/{id}")
	public RespBaseVo remove(@PathVariable String id){
		
		logger.info("Invoking the REST API endpoint /dogs/{id} , the identity received is : ["  + id + "]");	
		RespBaseVo pdv = new RespBaseVo();
		
		if(id != null && !id.trim().equalsIgnoreCase("")){
			
			Long dogBreedIdentity = petDogUtility.isValidIdentity(id);
			if(dogBreedIdentity != null){
				
				//attempt to remove a dog breed from the back end 
				//matching the given identity 
				boolean removed = petDogService.removeDogBreed(dogBreedIdentity);
				if(removed){
					pdv.setMessage("Delete request is successfully executed");
					pdv.setStatus(OPERATION_SUCCESS);
				}else{
					pdv.setMessage("Unable to execute the delete request");
					pdv.setStatus(OPERATION_FAILURE);
				}
			}else{
				pdv.setMessage("Unable to execute the delete request. "
						+ "Identity value given is not valid!");
				pdv.setStatus(OPERATION_FAILURE);
			}
		}
		
		return pdv;
	}
	
	
	/**
	 *Returns an existing dog breed record based on the given name
	 **/
	@GetMapping(value="/dogs",params={"breedName"})
	public SearchRespVo search(@RequestParam String breedName){
		
		logger.info("Invoking the REST API endpoint /dogs , breed name  : [" 
				+ breedName + "]");
		
		SearchRespVo searchRespVo = new SearchRespVo();
		List<PetDogVO> petDogs = new ArrayList<>();		

		searchRespVo.setDogs(petDogs);
		searchRespVo.setMessage("No dogs found for the given breed name");
		searchRespVo.setStatus(OPERATION_FAILURE);
		
		try{
			if(breedName != null && !breedName.trim().equalsIgnoreCase("")){
				
				//lets find any dog breed records by this breed name
				List<DogBreed> breedList = petDogService.searchDogBreed(
						breedName.trim());
				if(breedList != null && breedList.size() > 0){
					
					Consumer<DogBreed> func  = (DogBreed db) -> {
						
						PetDogVO pdv = new PetDogVO();
						pdv.setMessage("Dog breed found.");
						pdv.setId(db.getDogBreedId().longValue());
						pdv.setBreed(db.getBreedName());
						pdv.setCreatedOn(db.getUploadedOn().toString());
						pdv.setAccessUrl(db.getUploadedLocation());
						pdv.setStatus(OPERATION_SUCCESS);
						petDogs.add(pdv);
						
					};
					breedList.forEach(func);	
					
					searchRespVo.setMessage("Found dogs for the given breed name");
					searchRespVo.setStatus(OPERATION_SUCCESS);
				}	
			}
		}catch(Exception ex){
			logger.error("Exception occurred when attempting to search dog "
					+ "breeds by breed name. The message is " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return searchRespVo;
	}
	
	
	/**
	 *Returns a list of dog breeds paginated and limited based on the
	 *given query parameters
	 **/
	@GetMapping(value="/dogs",params={"size","page"})
	public FindRespVo find(@RequestParam String size , @RequestParam String page){
		
		logger.info("Invoking the REST API endpoint /dogs , size  : [" + size + "] , page number : [" + page + "]");
		
		FindRespVo findRespVo = new FindRespVo();
		List<String> breeds = new ArrayList<>();
		
		boolean validSize = (size != null && !size.trim().equalsIgnoreCase("")) ;
		boolean validPage = (page != null && !page.trim().equalsIgnoreCase(""));
		
		if(validSize && validPage){
			try{
				
				Integer sizeInt = petDogUtility.isValidInteger(size);
				Integer pageInt = petDogUtility.isValidInteger(page);
			
				if(sizeInt != null && pageInt != null){
					
					breeds = petDogService.findBreedNames(pageInt, sizeInt);
					
					if(breeds != null && breeds.size() > 0){
						findRespVo.setBreeds(breeds);
						findRespVo.setMessage("Dog breed names found"); 
						findRespVo.setStatus(OPERATION_SUCCESS);
					}else{
						findRespVo.setMessage("No dog breed names found"); 
						findRespVo.setStatus(OPERATION_FAILURE);
					}
				}
			}catch(Exception ex){
				logger.error("Exception occurred when attempting to find "
						+ "dog breeds. The message is " + ex.getMessage());
				ex.printStackTrace();
			}
			
		}else{
			findRespVo.setMessage("Invalid page size or page number given"); 
			findRespVo.setStatus(OPERATION_FAILURE);
		}
		
		return findRespVo;
	}

	
	//autowiring
	
	public PetDogService getPetDogService() {
		return petDogService;
	}

	@Autowired
	public void setPetDogService(PetDogService petDogService) {
		this.petDogService = petDogService;
	}

	public PetDogUtility getPetDogUtility() {
		return petDogUtility;
	}

	@Autowired
	public void setPetDogUtility(PetDogUtility petDogUtility) {
		this.petDogUtility = petDogUtility;
	}
	
}

