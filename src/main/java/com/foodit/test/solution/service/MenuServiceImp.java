package com.foodit.test.solution.service;

import com.foodit.test.solution.bean.dto.Meal;
import com.foodit.test.solution.bean.dto.Restaurant;
import com.foodit.test.solution.bean.frontend.MostFrequentCategory;
import com.threewks.thundr.logger.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;
import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class MenuServiceImp implements MenuServiceInterface{

    public static final String ROOT_ELEMENT = "menu";
    public static final String ID = "id";
    public static final String CATEGORY = "category";

    @Override
    public List<Meal> parse(String jsonString, String restaurantName)
            throws IOException {
        JsonFactory jsonFactory = new org.codehaus.jackson.map.MappingJsonFactory();
        JsonParser jsonParser = jsonFactory.createJsonParser(jsonString);
        JsonToken currentToken;

        JsonToken jsonToken = jsonParser.nextToken();
        if (jsonToken != JsonToken.START_OBJECT) {
            Logger.error("Invalid JSON! The first token is: " + jsonToken.asString());
        }

        List<Meal> mealList = new ArrayList<>();

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            currentToken = jsonParser.nextToken();
            if (fieldName.equals(ROOT_ELEMENT)) {
                //ignore and go down, I only want the elements of the array
                continue;
            }

            if (currentToken == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode node = jsonParser.readValueAsTree();
                    mealList.add(new Meal(node.get(ID).getLongValue(), node.get(
                            CATEGORY).getTextValue(), restaurantName));
                }
            }

        }
        return mealList;
    }

    @Override
    public MostFrequentCategory getMostFrequentlyOrderedCategory(String restaurant) {
        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).filter("storeId", restaurant).list();
        if(restaurants == null) return null;
        if(restaurants.size() > 1) Logger.warn("WARNING! Multiple (" +restaurants.size() + ") restaurant entry " +
                " for restaurant: " + restaurant);
        Restaurant restaurantObject = restaurants.get(0);
        return getMostFrequentCategoryForRestaurantObject(restaurantObject);

    }

    @Override
    public Set<MostFrequentCategory> getMostFrequentlyOrderedCategoryForEachRestaurant() {
        List<Restaurant> restaurants = ofy().load().type(Restaurant.class).list();
        Set<MostFrequentCategory> result = new HashSet<>();
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant =  restaurants.get(i);
            result.add(getMostFrequentCategoryForRestaurantObject(restaurant));
        }
        return result;
    }


    // ------------------ private methods ---------------------

    private MostFrequentCategory getMostFrequentCategoryForRestaurantObject(Restaurant restaurantObject) {
        MostFrequentCategory result = new MostFrequentCategory();
        result.setRestaurant(restaurantObject.getStoreId());
        Map<String, Long> categoryMap = restaurantObject.getCategoryMap();
        List<String> indexes = getIndexesMaxValue(categoryMap);
        Long numberOrders = categoryMap.get(indexes.get(0));
        result.setNumberOfOrders(numberOrders.longValue());
        List<String> categories = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            String category = indexes.get(i);
            categories.add(category);

        }
        result.setCategories(categories);
        return result;
    }

    private List<String> getIndexesMaxValue(Map<String, Long> inputMap) {
        ArrayList<String> maxKeys = new ArrayList<>();
        Long maxValue = new Long(-1);
        // TODO extract the method and put it in some utility class
        for (Map.Entry<String, Long> entry : inputMap.entrySet()) {
            if (entry.getValue().longValue() > maxValue.longValue()) {
                // New max remove all current keys
                maxKeys.clear();
                maxKeys.add(entry.getKey());
                maxValue =  entry.getValue();
            } else if (entry.getValue().longValue() == maxValue.longValue()) {
                maxKeys.add(entry.getKey());
            }
        }
        return maxKeys;
    }
}