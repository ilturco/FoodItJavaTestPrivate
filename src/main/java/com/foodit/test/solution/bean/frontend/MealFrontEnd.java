package com.foodit.test.solution.bean.frontend;

/**
 * Created by salvatore on 02/08/2014.
 */
public class MealFrontEnd {
    private long id;
    private String name;
    private String category;
    private long numberOfOrders;
    private String restaurant;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public MealFrontEnd() {

    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
}
