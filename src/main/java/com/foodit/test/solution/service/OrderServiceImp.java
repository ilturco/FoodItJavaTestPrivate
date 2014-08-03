package com.foodit.test.solution.service;

/**
 * Created by salvatore on 01/08/2014.
 */

import com.foodit.test.solution.bean.dto.LineItem;
import com.foodit.test.solution.bean.dto.Order;
import com.foodit.test.solution.bean.dto.Restaurant;
import com.foodit.test.solution.bean.frontend.AmountOfMoneyForARestaurant;
import com.foodit.test.solution.bean.frontend.MealFrontEnd;
import com.foodit.test.solution.bean.frontend.NumberOfOrdersForARestaurant;

import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class OrderServiceImp implements OrderServiceInterface {
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
    public Set<NumberOfOrdersForARestaurant> getNumberOfOrdersForEachRestaurant() {

        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).list();
        System.out.println("\n\n\n\n\nsize of the restaurant list = " + restaurants.size());
        // Projection queries are not supported by objectify yet (http://stackoverflow.com/questions/23154817/distinct-using-objectify)
        // Therefore we need to manually adapt the dto to a front-end bean that has only the fields needed, which
        // actually is always a best practice.
        Set<NumberOfOrdersForARestaurant> results = new HashSet<>();
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            NumberOfOrdersForARestaurant restaurantProjected =
                    new NumberOfOrdersForARestaurant(restaurant.getStoreId(), restaurant.getTotalNumberOfOrders());
            results.add(restaurantProjected);
        }
        System.out.println("\n\n\n\n\nsize of the result list = " + results.size());

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
    public Set<AmountOfMoneyForARestaurant> getTotalAmountOfMoneyForEachRestaurant() {
        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).list();
        // Projection queries are not supported by objectify yet (http://stackoverflow.com/questions/23154817/distinct-using-objectify)
        // Therefore we need to manually adapt the dto to a front-end bean that has only the fields needed, which
        // actually is always a best practice.
        Set<AmountOfMoneyForARestaurant> results = new HashSet<>();
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            AmountOfMoneyForARestaurant restaurantProjected =
                    new AmountOfMoneyForARestaurant(restaurant.getStoreId(), restaurant.getTotalAmountOfSales());
            results.add(restaurantProjected);
        }

        return results;
    }

    @Override
    public Set<MealFrontEnd> getMostFrequentlyOrderedMeals() {


        // first part: create the temporary structure that holds, for each item in the menu
        // the total number or orders
        HashMap<Long, Long> ordersHM = new HashMap<>();
        List<Order> orders = ofy().load().type(Order.class).list();
        // external loop: check each single order placed
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Set<LineItem> lineItems = order.getLineItems();
            // internal loop: for each order check each single line item
            for (Iterator<LineItem> iterator = lineItems.iterator(); iterator.hasNext(); ) {
                LineItem lineItem = iterator.next();
                Long lineItemId = lineItem.getId();
                // the following line deals with the "delivery charges"
                // I prefer to keep this info into the order structure -> line items
                // even this gives a lineItem with ID = 0
                if(lineItemId == null) continue;

                Long currentCount = ordersHM.get(lineItemId);
                if (currentCount != null) {
                    ordersHM.put(lineItemId, new Long(currentCount.longValue() + lineItem.getQuantity()));
                } else {
                    ordersHM.put(lineItemId, new Long(lineItem.getQuantity()));
                }
            }
        }

        // second part: now that we have the structure we just need to find the keys corresponding to the max value
        ArrayList<Long> maxKeys = new ArrayList<>();
        Long maxValue = new Long(-1);
        // TODO extract the method and put it in some utility class
        for (Map.Entry<Long, Long> entry : ordersHM.entrySet()) {
            if (entry.getValue() > maxValue) {
                // New max remove all current keys
                maxKeys.clear();
                maxKeys.add(entry.getKey());
                maxValue = entry.getValue();
            } else if (entry.getValue() == maxValue) {
                maxKeys.add(entry.getKey());
            }
        }



        // third part: create an object for each key
        Set<MealFrontEnd> result = new HashSet<>();
        for (int i = 0; i < maxKeys.size(); i++) {
            Long itemId = maxKeys.get(i);

            MealFrontEnd mealFrontEnd = new MealFrontEnd();
            mealFrontEnd.setId(itemId);
            mealFrontEnd.setNumberOfOrders(ordersHM.get(itemId));
            // TODO - read the object
            mealFrontEnd.setUnitPrice(0);
            result.add(mealFrontEnd);

        }

        return result;

    }

    @Override
    public Set<Restaurant> getRestaurantStats() {
        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).list();
        //this is because from time to time the /load action is invoked twice and this causes two
        //different sets of Restaurant objects to be created.
        Set<Restaurant> result = new HashSet<>(restaurants);
        return result;

    }
}
