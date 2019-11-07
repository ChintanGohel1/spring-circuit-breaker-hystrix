package com.chintangohel.restaurantcatalogservice.restaurant.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCatalog {
	private String name;

	private String description;

	private String location;

	private int rating;
}
