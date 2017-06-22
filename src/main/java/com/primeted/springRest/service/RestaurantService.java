package com.primeted.springRest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primeted.springRest.model.Restaurant;
import com.primeted.springRest.repositories.RestaurantRepository;



public interface RestaurantService {
	
	  Restaurant findById(Long id);
	 
	  	Restaurant findByNombre(String name);
	    void saveRestaurant(Restaurant restaurant);
	 
	    void updateRestaurant(Restaurant restaurant);
	 
	    void deleteRestaurantById(Long id);
	 
	    void deleteAllRestaurants();
	 
	    List<Restaurant> findAllRestaurants();	    	   

		boolean isRestaurantExists(Restaurant restaurant);

		Restaurant findByRandom();
	 	
	
	
	
	
}
