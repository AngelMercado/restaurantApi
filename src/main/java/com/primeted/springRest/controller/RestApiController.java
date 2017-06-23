package com.primeted.springRest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistrar;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeted.springRest.model.Restaurant;
import com.primeted.springRest.service.RestaurantService;
import com.primeted.springRest.storage.StorageService;

@RestController
@RequestMapping("/apiv1")
public class RestApiController {
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	 
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	StorageService storageService;
	
	HttpHeaders headers = new HttpHeaders();
	
	public RestApiController(){
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Content-Type", "application/json;charset=UTF-8");
		headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		headers.add("Access-Control-Allow-Headers", "X-Requested-With,content-type");
	}
	//-------------------Retrive all restaurants--------------//
	@RequestMapping(value="/restaurantes",method = RequestMethod.GET)
	public ResponseEntity<String> listRestaurants(){
	    
		Map<String, Object> json = new HashMap<String, Object>();
		List<Restaurant> restaurants = restaurantService.findAllRestaurants();
		String data="";
		
		if(restaurants.isEmpty()){
			 json.put("status","error");
			 json.put("message","there arent registers");
			return new ResponseEntity<String>(data,headers,HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}else{
			json.put("status", "success");
			json.put("data", restaurants);
		}
		
		try {
		        ObjectMapper mapper = new ObjectMapper();
		        data  = mapper.writeValueAsString(json);
		    } catch (Exception e) { //TODO
		    }
				
		
		return new ResponseEntity<String>(data,headers,HttpStatus.OK);

 	}
	@RequestMapping(value="/restaurantes/{id}",method=RequestMethod.GET)
	public ResponseEntity<String> getRestaurantById(@PathVariable("id")Long id){
		Map<String, Object> json = new HashMap<String, Object>();
		Restaurant restaurant = restaurantService.findById(id);
		String data="";
		if(restaurant.equals(null)){
			json.put("status","error");
			json.put("message","restaurant doesnt exists");
			json.put("data", "{}");
			data= toJson(json);
			return new ResponseEntity<String>(data,headers,HttpStatus.NO_CONTENT);
		}else{
			json.put("status", "success");
			json.put("data", restaurant);
			data = toJson(json);
		}
		return new ResponseEntity<String>(data,headers,HttpStatus.OK);
	}
	

	@RequestMapping(value="/restaurante",method=RequestMethod.POST)
	public ResponseEntity<String> insertRestaurant(@RequestBody Restaurant restaurant ,UriComponentsBuilder ucBuilder){
		Map<String,Object> json= new HashMap<String,Object>();
		String data="";
		logger.info("Creating Restaurant : {}", restaurant);
		if(restaurantService.isRestaurantExists(restaurant)){
			logger.info("Unable to create. A restaurant with name {} already exists",restaurant.getNombre());
			json.put("status", "error");
			json.put("message", "Unable to create. A restaurant with name "+restaurant.getNombre()+" already exists");
			data=toJson(json);
			return new ResponseEntity<String>(data,headers,HttpStatus.CONFLICT);
		}else{
			restaurantService.saveRestaurant(restaurant);
			json.put("status", "success");
			json.put("data", "{}");
			data=toJson(json);
		}
		
		return new ResponseEntity<String>(data,headers,HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/restaurante/{id}",method=RequestMethod.PUT)
	public ResponseEntity<String> updateRestaurant(@RequestBody Restaurant restaurant ,UriComponentsBuilder ucBuilder){
		Map<String,Object> json= new HashMap<String,Object>();
		String data="";
		json.put("status", "success");
		json.put("message","success update");
		data=toJson(json);
		logger.info("Updating restaurant: {}",restaurant);
		restaurantService.updateRestaurant(restaurant);
		return new ResponseEntity<String>(data,headers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/restaurante/{id}",method=RequestMethod.GET)
	public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long id){
		Map<String,Object> json= new HashMap<String,Object>();
		String data="";
		Restaurant restaurant = restaurantService.findById(id);
		if(restaurant==null){
			json.put("status", "error");
			json.put("message", "Restaurant doesnt exists in database");
			data = toJson(json);
			return new ResponseEntity<String>(data,headers,HttpStatus.NOT_FOUND);
		}else{
			json.put("status", "success");
			data = toJson(json);
			restaurantService.deleteRestaurantById(id);
			return new ResponseEntity<String>(data,headers,HttpStatus.OK);
		}
	}
	@RequestMapping(value="/random-restaurant",method=RequestMethod.GET)
	public ResponseEntity<String> getRandomRestaurant(){
		Map<String,Object> json= new HashMap<String,Object>();
		String data="";
		Restaurant restaurant = restaurantService.findByRandom();
		if(restaurant==null){
			json.put("status", "error");
			json.put("message", "database does not have registers");
			data=toJson(json);
			return new ResponseEntity<String>(data,headers,HttpStatus.NOT_FOUND);
		}else{
			json.put("status", "success");
			json.put("data", restaurant);
			data=toJson(json);
			return new ResponseEntity<String>(data,headers,HttpStatus.OK);
		}
		
	}
	@RequestMapping(value="/upload-file",method=RequestMethod.POST)
	public ResponseEntity<String>uploadImage(@RequestParam("uploads[]") MultipartFile file){
		
		logger.info("FILE====> {}",file.getOriginalFilename());
		Map<String,Object> json= new HashMap<String,Object>();
		String data="";
		String finalName="";
		try{
			finalName=storageService.store(file);
			json.put("status", "succes");
			json.put("message", "upload file succesfully");
			json.put("filename",finalName);
			data=toJson(json);
			logger.info("upload sucess");
			return new ResponseEntity<String>(data,headers,HttpStatus.OK);
		}catch(Exception e){
			json.put("status", "error");
			json.put("message", "fails to upload file");
			json.put("StackErrors {} ", e);
			logger.info("Throw exception {} ", e);
			return new ResponseEntity<String>(data,headers,HttpStatus.CONFLICT);
		}								
	}
	@RequestMapping(value="/files/{filename:.+}",method=RequestMethod.GET)
	public ResponseEntity<Resource> getFile(@PathVariable String filename){
		Resource file= storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	public String toJson(Map<String, Object> json){
		String data="";
		
		try { 
			
	        ObjectMapper mapper = new ObjectMapper();
	        data  = mapper.writeValueAsString(json);
	    } catch (Exception e) { //TODO
	    }
		return data;
	}
	
	
	
}
