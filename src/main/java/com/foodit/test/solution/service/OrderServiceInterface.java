package com.foodit.test.solution.service;

import com.foodit.test.solution.dto.Restaurant;

import java.util.List;

/**
 * Created by salvatore on 01/08/2014.
 */
public interface OrderServiceInterface {
    public int getNumberOfOrders(String restaurant);

    public List<Restaurant> getNumberOfOrdersForEachRestaurant();


}
