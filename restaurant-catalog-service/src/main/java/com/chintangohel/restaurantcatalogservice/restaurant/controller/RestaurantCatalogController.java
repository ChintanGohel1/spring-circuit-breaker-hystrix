package com.chintangohel.restaurantcatalogservice.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.Restaurant;
import com.chintangohel.restaurantcatalogservice.restaurant.bean.RestaurantCatalog;
import com.chintangohel.restaurantcatalogservice.restaurant.bean.RestaurantCatalogResponseBean;
import com.chintangohel.restaurantcatalogservice.restaurant.bean.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class RestaurantCatalogController {

	private static final String PATH_GET_RESTAURANT_CATALOG = "/getRestaurantCatalog/{userId}";

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(path = PATH_GET_RESTAURANT_CATALOG)
	@HystrixCommand(fallbackMethod = "fallbackGetrestaurantCatalog")
	public RestaurantCatalogResponseBean getrestaurantCatalog(@PathVariable("userId") String userId) {
		// UserRating userRating = restTemplate.getForObject(
		// "http://rating-data-service/ratingDataService/getRestaurantRating/" +
		// userId, UserRating.class);
		//
		UserRating userRating = restTemplate.getForObject(
				"http://rating-data-service/ratingDataService/getRestaurantRating/" + userId, UserRating.class);

		RestaurantCatalogResponseBean restaurantCatalogResponseBean = new RestaurantCatalogResponseBean();
		restaurantCatalogResponseBean.setRestaurants(userRating.getRatings().stream().map(data -> {
			Restaurant restaurant = restTemplate.getForObject(
					"http://restaurant-info-service/restaurantInfoService/getRestaurantInfo/" + data.getRestaurantId(),
					Restaurant.class);
			return new RestaurantCatalog(restaurant.getName(), restaurant.getDescription(), restaurant.getLocation(),
					data.getRating());
		}).collect(Collectors.toList()));

		return restaurantCatalogResponseBean;

	}
	
	
	@SuppressWarnings("unused")
	private RestaurantCatalogResponseBean fallbackGetrestaurantCatalog(@PathVariable("userId")String userId){
		System.out.println("fallback method called");
		return null;
	}

}
