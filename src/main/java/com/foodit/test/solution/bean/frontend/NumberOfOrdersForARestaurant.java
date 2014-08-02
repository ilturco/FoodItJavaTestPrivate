package com.foodit.test.solution.bean.frontend;

/**
 * Created by salvatore on 02/08/2014.
 */
public class NumberOfOrdersForARestaurant {
    private String restaurant;
    private int numberOfOrders;

    public NumberOfOrdersForARestaurant(String restaurant, int numberOfOrders) {
        this.restaurant = restaurant;
        this.numberOfOrders = numberOfOrders;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }
}
