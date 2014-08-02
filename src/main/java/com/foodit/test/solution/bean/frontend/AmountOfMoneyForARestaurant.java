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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmountOfMoneyForARestaurant that = (AmountOfMoneyForARestaurant) o;

        if (Float.compare(that.totalSales, totalSales) != 0) return false;
        if (!restaurant.equals(that.restaurant)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = restaurant.hashCode();
        result = 31 * result + (totalSales != +0.0f ? Float.floatToIntBits(totalSales) : 0);
        return result;
    }
}
