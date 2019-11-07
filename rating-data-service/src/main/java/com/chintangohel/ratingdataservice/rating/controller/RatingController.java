package com.chintangohel.ratingdataservice.rating.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintangohel.ratingdataservice.rating.bean.UserRating;

@RestController
@RequestMapping("/ratingDataService")
public class RatingController {

	private static final String PATH_GET_RESTAURANT_RATING_BY_USER_ID = "/getRestaurantRating/{userId}";

	@GetMapping(path = PATH_GET_RESTAURANT_RATING_BY_USER_ID)
	public UserRating getRestaurantRatingbyUserId(@PathVariable("userId") String userId) {
		UserRating userRating = new UserRating();
		userRating.initData(userId);
		return userRating;

	}

}
