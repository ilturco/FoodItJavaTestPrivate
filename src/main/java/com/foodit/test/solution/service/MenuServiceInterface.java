package com.foodit.test.solution.service;

import com.foodit.test.solution.bean.dto.Meal;
import com.foodit.test.solution.bean.frontend.MostFrequentCategory;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by salvatore on 03/08/2014.
 */
public interface MenuServiceInterface {
    public List<Meal> parse(String jsonString, String restaurantName)
            throws JsonParseException, IOException;

    public MostFrequentCategory getMostFrequentlyOrderedCategory(String restaurant);

    public Set<MostFrequentCategory> getMostFrequentlyOrderedCategoryForEachRestaurant();

}
