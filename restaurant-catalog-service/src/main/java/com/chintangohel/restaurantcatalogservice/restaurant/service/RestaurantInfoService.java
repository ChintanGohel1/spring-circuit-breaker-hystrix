package com.chintangohel.restaurantcatalogservice.restaurant.service;

import org.springframework.stereotype.Service;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.Restaurant;

@Service
public interface RestaurantInfoService {

	Restaurant getRestaurantInfo(String restaurantId);
	
}
