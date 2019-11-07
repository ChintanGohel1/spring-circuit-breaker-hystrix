package com.chintangohel.restaurantinfoservice.restaurant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintangohel.restaurantinfoservice.restaurant.bean.Restaurant;

@RestController
@RequestMapping("/restaurantInfoService")
public class RestaurantInfoController {

	private static final String PATH_GET_RESTAURANT_INFO = "getRestaurantInfo/{restaurantId}";

	// @Autowired
	// private RestTemplate restTemplate;

	@GetMapping(path = PATH_GET_RESTAURANT_INFO)
	public Restaurant getRestaurantInfo(@PathVariable("restaurantId") String restaurantId) {

		return new Restaurant("Honest", "Restaurant for North Indian Cusine", "Ahmedabad");

	}

}
