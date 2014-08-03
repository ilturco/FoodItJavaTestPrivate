package com.foodit.test.solution.bean.dto;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
// We place the Entity in the global cache (https://code.google.com/p/objectify-appengine/wiki/Caching)
// This choice comes from two considerations: there are few writings and the data is small
@Cache
public class Meal {
   
    @Id Long id;	
    @Index Long mealId;
    @Index String restaurantName;
    String mealCategory;
   
    private Meal() { }

    public Meal(Long mealId, String mealCategory, String restaurantName) {
        this.mealId = mealId;
        this.mealCategory = mealCategory;
        this.restaurantName = restaurantName;
    }
    
    public String getMealCategory() {
        return mealCategory;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public String getMealId() {
        return restaurantName;
    }
    
    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }
    
    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }
    
    public void getMealId(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "id=" + id +
                ", mealId=" + mealId +
                ", restaurantName='" + restaurantName + '\'' +
                ", mealCategory='" + mealCategory + '\'' +
                '}';
    }
}