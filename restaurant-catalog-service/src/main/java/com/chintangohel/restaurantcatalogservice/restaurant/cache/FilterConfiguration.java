package com.chintangohel.restaurantcatalogservice.restaurant.cache;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chintangohel.restaurantcatalogservice.restaurant.filter.HystrixRequestContextServletFilter;

@Configuration
public class FilterConfiguration {

	@Bean
	public FilterRegistrationBean<HystrixRequestContextServletFilter> hystrixRequestContextServletFilter() {
		FilterRegistrationBean<HystrixRequestContextServletFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new HystrixRequestContextServletFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
