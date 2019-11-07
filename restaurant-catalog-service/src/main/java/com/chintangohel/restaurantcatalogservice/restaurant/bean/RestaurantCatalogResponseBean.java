package com.chintangohel.restaurantcatalogservice.restaurant.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestaurantCatalogResponseBean {

	private List<RestaurantCatalog> restaurants;
}
