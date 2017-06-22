package com.primeted.springRest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.primeted.springRest.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	Restaurant findByNombre(String name);
    @Query(value = "SELECT * FROM restaurantes ORDER BY RAND() LIMIT 1;",
           nativeQuery = true
    )
	Restaurant findByRandom();
}
