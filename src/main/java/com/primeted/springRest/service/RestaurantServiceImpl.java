package com.primeted.springRest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primeted.springRest.model.Restaurant;
import com.primeted.springRest.repositories.RestaurantRepository;

@Service("RestaurantService")
public class RestaurantServiceImpl implements RestaurantService{

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public Restaurant findById(Long id) {
		return restaurantRepository.findOne(id);
	}


	@Override
	public void saveRestaurant(Restaurant restaurant) {
		restaurantRepository.save(restaurant);
		
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) {
		restaurantRepository.save(restaurant);
		
	}

	@Override
	public void deleteRestaurantById(Long id) {
		restaurantRepository.delete(id);
		
	}

	@Override
	public void deleteAllRestaurants() {
		restaurantRepository.deleteAll();
		
	}

	@Override
	public List<Restaurant> findAllRestaurants() {		
		return restaurantRepository.findAll();
	}


	@Override
	public Restaurant findByNombre(String name) {
		// TODO Auto-generated method stub
		return restaurantRepository.findByNombre(name);
	}


	@Override
	public boolean isRestaurantExists(Restaurant restaurant) {
		
		return restaurantRepository.findByNombre(restaurant.getNombre())!=null;
	}

	@Override
	public Restaurant findByRandom(){
		return restaurantRepository.findByRandom();
	}
	
}
