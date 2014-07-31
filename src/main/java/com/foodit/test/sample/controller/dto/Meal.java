package com.foodit.test.sample.controller.dto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by salvatore on 31/07/2014.
 */
@Entity
public class Meal {
    @Id
    private Long id;
    private String name;
    private String description;
    private String category;
    private String restaurant;
    private Long startingFromPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Long getStartingFromPrice() {
        return startingFromPrice;
    }

    public void setStartingFromPrice(Long startingFromPrice) {
        this.startingFromPrice = startingFromPrice;
    }
}
