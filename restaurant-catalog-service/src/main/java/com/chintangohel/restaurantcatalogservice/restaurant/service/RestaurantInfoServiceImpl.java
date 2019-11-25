package com.chintangohel.restaurantcatalogservice.restaurant.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.Restaurant;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class RestaurantInfoServiceImpl implements RestaurantInfoService {

	private static final String RESTAURANT_INFO_CACHE_KEY = "RestaurantInfo";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedisTemplate<String, Restaurant> redisTemplate;

	private HashOperations<String, String, Restaurant> restaurantInfoCache;

	@PostConstruct
	public void init() {
		this.restaurantInfoCache = redisTemplate.opsForHash();
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallbackGetRestaurantInfo")
	public Restaurant getRestaurantInfo(String restaurantId) {
		Restaurant restaurantInfo = restTemplate.getForObject(
				"http://restaurant-info-service/restaurantInfoService/getRestaurantInfo/" + restaurantId,
				Restaurant.class);

		if (restaurantInfo.equals(null)) {
			log.error("Cannot access restaurant-info-service endpoint");
			throw new RuntimeException("Exception: Cannot access restaurant-info-serviceendpoint");
		} else {
			log.debug("Request restaurant-info-service successful ");
			this.updateCache(restaurantInfo);
			return restaurantInfo;
		}

	}

	@HystrixCommand(fallbackMethod = "defaultFallback")
	private Restaurant fallbackGetRestaurantInfo(String restaurantId) {
		System.out.println("fallbackGetRestaurantInfo method called");
		log.debug("read data from cache");
		return restaurantInfoCache.get(RESTAURANT_INFO_CACHE_KEY, restaurantId);
		// return new Restaurant("Default Name", "Default Description", "Default
		// Location");
	}

	@SuppressWarnings("unused")
	private Restaurant defaultFallback(String restaurantId) {
		log.error("Cannot read restaurantInfo from cache with the id: " + restaurantId);
		log.debug("Returning Default restaurantInfo");
		return new Restaurant("Default Id", "Default Name", "Default Description", "DefaultLocation");
	}

	private void updateCache(Restaurant restaurantInfo) {
		try {
			log.debug("Updating cache...");
			restaurantInfoCache.put(RESTAURANT_INFO_CACHE_KEY, restaurantInfo.getId(), restaurantInfo);
		} catch (Throwable e) {
			log.error("Cannot update cache", e);
		}
	}

}
