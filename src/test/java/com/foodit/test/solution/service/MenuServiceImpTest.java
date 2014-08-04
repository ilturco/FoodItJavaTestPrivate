package com.foodit.test.solution.service;

import com.foodit.test.solution.bean.dto.Meal;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by salvatore on 04/08/2014.
 */
public class MenuServiceImpTest {

    @Test
    public void parseMenuJson(){
        MenuServiceImp menuServiceImp = new MenuServiceImp();
        String menuJson = "{\n" +
                "    \"menu\": {\n" +
                "        \"Kebabs (Kebabs)\": [\n" +
                "            {\n" +
                "                \"id\": 0,\n" +
                "                \"name\": \"Lamb Kebab\",\n" +
                "                \"description\": \"Cubes of lamb cooked on charcoal served in pitta bread and salad\",\n" +
                "                \"category\": \"Kebabs\",\n" +
                "                \"sizeAndPrice\": {\n" +
                "                    \"Regular\": \"0.00\"\n" +
                "                },\n" +
                "                \"mealTypeOptions\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Size\",\n" +
                "                        \"multiSelect\": false,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Small\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"4.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Large\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"6.50\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Side\",\n" +
                "                        \"multiSelect\": true,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Chips\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Roast Potatoes\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Rice\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Taramasalata\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Houmous\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Tzatziki\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Greek Salad\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"3.00\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Drinks\",\n" +
                "                        \"multiSelect\": true,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Coke\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Diet Coke\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Pepsi\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"7UP\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"startingFromPrice\": \"4.50\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Pork Kebab\",\n" +
                "                \"description\": \"Cubes of pork cooked on charcoal served in pitta bread and salad\",\n" +
                "                \"category\": \"Kebabs\",\n" +
                "                \"sizeAndPrice\": {\n" +
                "                    \"Regular\": \"0.00\"\n" +
                "                },\n" +
                "                \"mealTypeOptions\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Size\",\n" +
                "                        \"multiSelect\": false,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Small\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"4.00\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Large\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"5.50\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Side\",\n" +
                "                        \"multiSelect\": true,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Chips\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Roast Potatoes\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Rice\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Taramasalata\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Houmous\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Tzatziki\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Greek Salad\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"3.00\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Drinks\",\n" +
                "                        \"multiSelect\": true,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Coke\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Diet Coke\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Pepsi\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"7UP\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"0.80\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"startingFromPrice\": \"4.00\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"Drinks (Drinks)\": [\n" +
                "            {\n" +
                "                \"id\": 40,\n" +
                "                \"name\": \"Coke Can\",\n" +
                "                \"description\": \"Coca Cola Can\",\n" +
                "                \"category\": \"Drinks\",\n" +
                "                \"sizeAndPrice\": {\n" +
                "                    \"Regular\": \"0.80\"\n" +
                "                },\n" +
                "                \"mealTypeOptions\": [],\n" +
                "                \"startingFromPrice\": \"0.80\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 41,\n" +
                "                \"name\": \"Cola 1.5 Litre Large Bottle\",\n" +
                "                \"description\": \"Coca Cola 1.5lt Bottle\",\n" +
                "                \"category\": \"Drinks\",\n" +
                "                \"sizeAndPrice\": {\n" +
                "                    \"Regular\": \"0.00\"\n" +
                "                },\n" +
                "                \"mealTypeOptions\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Pick Drink\",\n" +
                "                        \"multiSelect\": false,\n" +
                "                        \"dropDown\": false,\n" +
                "                        \"options\": [\n" +
                "                            {\n" +
                "                                \"label\": \"Coca Cola 1.5 Litre\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"label\": \"Diet Cola 1.5 Litre\",\n" +
                "                                \"pricesForSize\": {\n" +
                "                                    \"Regular\": \"1.50\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"startingFromPrice\": \"1.50\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"restaurantId\": \"bbqgrill\"\n" +
                "}";

        List<Meal> meals = null;
        try {
            meals = menuServiceImp.parseMenuAndEnrichWithName(menuJson, "bbqgrill");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(meals.size(), 4);
        Meal firstMeal = meals.get(0);
        assertEquals(firstMeal.getName(), "Lamb Kebab");
        assertEquals(firstMeal.getMealId(), new Long(0));
        assertEquals(firstMeal.getMealCategory(), "Kebabs");


    }
}
