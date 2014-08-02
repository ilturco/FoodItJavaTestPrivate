package com.foodit.test.solution.bean.frontend;

/**
 * Created by salvatore on 02/08/2014.
 */
public class AmountOfMoneyForARestaurant {
    private String restaurant;
    private float totalSales;

    public AmountOfMoneyForARestaurant(String restaurant, float totalSales) {
        this.restaurant = restaurant;
        this.totalSales = totalSales;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public float getTotalSales() {
        return totalSales;
    }
}
