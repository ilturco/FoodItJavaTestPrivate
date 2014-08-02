package com.foodit.test.solution.service;

/**
 * Created by salvatore on 01/08/2014.
 */

import com.foodit.test.solution.dto.Order;
import com.foodit.test.solution.dto.Restaurant;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class OrderServiceImp implements  OrderServiceInterface {
    @Override
    public int getNumberOfOrders(String restaurant) {
        // TODO there is also count() but apparently it is not that efficient. Anyway it can't be worst than this.
        List<Order> results = ofy().load().type(Order.class).filter("storeId", restaurant).list();
        return results.size();
    }

    @Override
    public List<Restaurant> getNumberOfOrdersForEachRestaurant() {
        // TODO there is also count() but apparently it is not that efficient. Anyway it can't be worst than this.
        //List<Order> results = ofy().load().type(Order.class).filter("storeId", restaurant).list();

        //return results.size();
        List<Restaurant> results = ofy().load().type(Restaurant.class).list();
        return results;
    }
}
