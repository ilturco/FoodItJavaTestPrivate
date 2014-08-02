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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberOfOrdersForARestaurant that = (NumberOfOrdersForARestaurant) o;

        if (numberOfOrders != that.numberOfOrders) return false;
        if (!restaurant.equals(that.restaurant)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = restaurant.hashCode();
        result = 31 * result + numberOfOrders;
        return result;
    }
}
