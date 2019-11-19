package com.chintangohel.restaurantcatalogservice.restaurant.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.RatingBean;
import com.chintangohel.restaurantcatalogservice.restaurant.bean.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class UserRatingServiceImpl implements UserRatingService {

	private static final String USER_RATING_CACHE_KEY = "userRatingCache";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedisTemplate<String, UserRating> redisTemplate;

	private HashOperations<String, String, UserRating> userRatingCache;

	@PostConstruct
	public void init() {
		this.userRatingCache = redisTemplate.opsForHash();
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallbackGetUserRating", commandKey = "getUserRating", threadPoolKey = "GetUserRatingFromEndpoint")
	public UserRating getUserRating(String userId) {
		UserRating userRating = restTemplate.getForObject(
				"http://rating-data-service/ratingDataService/getRestaurantRating/" + userId, UserRating.class);

		if (userRating.equals(null)) {
			log.error("Cannot access rating-data-service endpoint");
			throw new RuntimeException("Exception: Cannot access rating-data-service endpoint");
		} else {
			log.debug("Request rating-data-service successful ");
			this.updateCache(userRating);
			return userRating;
		}

	}

	@HystrixCommand(fallbackMethod = "defaultFallback", commandKey = "getUserRating", threadPoolKey = "GetUserRatingFromCache")
	private UserRating fallbackGetUserRating(String userId) {
		System.out.println("fallbackGetUserRating method called");
		log.debug("read data from cache");
		return userRatingCache.get(USER_RATING_CACHE_KEY, userId);
		// return new UserRating(userId, Arrays.asList(new RatingBean("100", 4),
		// new RatingBean("200", 3)));
	}

	@SuppressWarnings("unused")
	private UserRating defaultFallback(String userId) {
		log.error("Cannot read userRating from cache with the id: " + userId);
		log.debug("Returning Default userRating");
		return new UserRating(userId, Arrays.asList(new RatingBean("100", 4), new RatingBean("200", 3)));
	}

	private void updateCache(UserRating userRating) {
		try {
			log.debug("Updating cache...");
			userRatingCache.put(USER_RATING_CACHE_KEY, userRating.getUserId(), userRating);
		} catch (Throwable e) {
			log.error("Cannot update cache", e);
		}
	}
}
