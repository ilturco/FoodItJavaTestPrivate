package com.foodit.test.solution.bean.frontend;

import java.util.List;

/**
 * Created by salvatore on 03/08/2014.
 */
public class MostFrequentCategory {
    private String restaurant;
    //there can be more than one category with the same amount of orders
    private List<String> categories;
    private long numberOfOrders;

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MostFrequentCategory that = (MostFrequentCategory) o;

        if (numberOfOrders != that.numberOfOrders) return false;
        if (!restaurant.equals(that.restaurant)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = restaurant.hashCode();
        result = 31 * result + (int) (numberOfOrders ^ (numberOfOrders >>> 32));
        return result;
    }
}
