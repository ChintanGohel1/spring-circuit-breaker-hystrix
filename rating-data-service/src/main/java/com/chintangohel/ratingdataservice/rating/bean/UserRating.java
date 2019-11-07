package com.chintangohel.ratingdataservice.rating.bean;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRating {

	private String userId;

	private List<RatingBean> ratings;
	
	 public void initData(String userId) {
	        this.setUserId(userId);
	        this.setRatings(Arrays.asList(
	                new RatingBean("100", 2),
	                new RatingBean("200", 2)
	        ));
	    }


}
