package com.chintangohel.restaurantcatalogservice.restaurant.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chintangohel.restaurantcatalogservice.restaurant.bean.RatingBean;
import com.chintangohel.restaurantcatalogservice.restaurant.bean.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class UserRatingServiceImpl implements UserRatingService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetUserRating")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingDataService/getRestaurantRating/" + userId,
                UserRating.class);
    }

    @SuppressWarnings("unused")
    private UserRating fallbackGetUserRating(String userId) {
        System.out.println("fallbackGetUserRating method called");
        return new UserRating(userId, Arrays.asList(new RatingBean("100", 4), new RatingBean("200", 3)));
    }
}
