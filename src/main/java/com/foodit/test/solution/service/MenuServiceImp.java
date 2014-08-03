package com.foodit.test.solution.service;

import com.foodit.test.solution.bean.dto.Meal;
import com.foodit.test.solution.bean.frontend.MostFrequentCategory;
import com.threewks.thundr.logger.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        return null;
    }

    @Override
    public Set<MostFrequentCategory> getMostFrequentlyOrderedCategoryForEachRestaurant() {
        return null;
    }
}