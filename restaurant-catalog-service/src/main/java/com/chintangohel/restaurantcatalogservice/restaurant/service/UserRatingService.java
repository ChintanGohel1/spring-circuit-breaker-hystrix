package com.chintangohel.restaurantcatalogservice.restaurant.service;

import org.springframework.stereotype.Service;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.UserRating;

@Service
public interface UserRatingService {

	UserRating getUserRating(String userId);
	
}
