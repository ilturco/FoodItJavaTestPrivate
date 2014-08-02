package com.foodit.test.solution.service;

/**
 * Created by salvatore on 01/08/2014.
 */

import com.foodit.test.solution.bean.dto.Order;
import com.foodit.test.solution.bean.dto.Restaurant;
import com.foodit.test.solution.bean.frontend.AmountOfMoneyForARestaurant;
import com.foodit.test.solution.bean.frontend.NumberOfOrdersForARestaurant;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class OrderServiceImp implements  OrderServiceInterface {
    @Deprecated
    @Override
    public int getNumberOfOrders(String restaurant) {
        // Datastore does not provide with any (real) aggregation function. This method retrieves all the orders that
        // match the filter and count them. Not very efficient.
        // http://stackoverflow.com/questions/21915793/count-number-of-objects-in-datastore-appengine-java
        // in older version there was a cap of 1000. The cap has now been removed but this is a sign that we should
        // not really count on count and use de-normalised objects to be updated at insert/update time.
        return ofy().load().type(Order.class).filter("storeId", restaurant).count();

    }

    @Override
    public List<NumberOfOrdersForARestaurant> getNumberOfOrdersForEachRestaurant() {

        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).list();
        // Projection queries are not supported by objectify yet (http://stackoverflow.com/questions/23154817/distinct-using-objectify)
        // Therefore we need to manually adapt the dto to a front-end bean that has only the fields needed, which
        // actually is always a best practice.
        List<NumberOfOrdersForARestaurant> results = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            NumberOfOrdersForARestaurant restaurantProjected =
                    new NumberOfOrdersForARestaurant(restaurant.getStoreId(), restaurant.getTotalNumberOfOrders());
            results.add(restaurantProjected);
        }

        return results;
    }
    @Deprecated
    @Override
    public float getTotalAmountOfMoney(String restaurant) {
        List<Order> orders = ofy().load().type(Order.class).filter("storeId", restaurant).list();
        // Datastore does not provide with any aggregation function. This method retrieves all the orders that
        // match the filter and manually calculate the sum. Not very efficient indeed.
        float sum = 0;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            sum = sum + order.getTotalValue();
        }
        return sum;

    }

    @Override
    public List<AmountOfMoneyForARestaurant> getTotalAmountOfMoneyForEachRestaurant() {
        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).list();
        // Projection queries are not supported by objectify yet (http://stackoverflow.com/questions/23154817/distinct-using-objectify)
        // Therefore we need to manually adapt the dto to a front-end bean that has only the fields needed, which
        // actually is always a best practice.
        List<AmountOfMoneyForARestaurant> results = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            AmountOfMoneyForARestaurant restaurantProjected =
                    new AmountOfMoneyForARestaurant(restaurant.getStoreId(), restaurant.getTotalAmountOfSales());
            results.add(restaurantProjected);
        }

        return results;
    }
}
