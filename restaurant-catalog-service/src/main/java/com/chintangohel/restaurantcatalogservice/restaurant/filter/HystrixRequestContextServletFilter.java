package com.chintangohel.restaurantcatalogservice.restaurant.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import lombok.extern.log4j.Log4j;

@Log4j
public class HystrixRequestContextServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Inside HystrixRequestContextServletFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.debug("Initializing HystrixRequestContext");
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        try {
            filterChain.doFilter(request, response);
        }
        finally {
            log.debug("Shutting down HystrixRequestContext");
            hystrixRequestContext.shutdown();
        }
    }

    @Override
    public void destroy() {
        log.debug("HystrixRequestContextServletFilter is destroyed");
    }
}