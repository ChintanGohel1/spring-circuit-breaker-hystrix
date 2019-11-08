package com.chintangohel.restaurantcatalogservice.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.Restaurant;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class RestaurantInfoServiceImpl implements RestaurantInfoService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@HystrixCommand(fallbackMethod = "fallbackGetRestaurantInfo")
	public Restaurant getRestaurantInfo(String restaurantId) {
		return restTemplate.getForObject(
				"http://restaurant-info-service/restaurantInfoService/getRestaurantInfo/" + restaurantId,
				Restaurant.class);

	}

	@SuppressWarnings("unused")
	private Restaurant fallbackGetRestaurantInfo(String restaurantId) {
		System.out.println("fallbackGetRestaurantInfo method called");
		return new Restaurant("Default Name","Default Description","Default Location");
	}

}
