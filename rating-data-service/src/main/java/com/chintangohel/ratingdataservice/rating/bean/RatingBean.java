package com.chintangohel.ratingdataservice.rating.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingBean {

	private String restaurantId;
	
	private int rating;
		
}
