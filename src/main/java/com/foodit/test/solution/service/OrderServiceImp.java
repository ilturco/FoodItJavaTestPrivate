package com.foodit.test.solution.service;

/**
 * Created by salvatore on 01/08/2014.
 */

import com.foodit.test.solution.dto.Order;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class OrderServiceImp implements  OrderServiceInterface {
    @Override
    public int getNumberOfOrder(String restaurant) {
        List<Order> results = ofy().load().type(Order.class).filter("storeId", restaurant).list();
        return results.size();
    }
}
