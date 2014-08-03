package com.foodit.test.solution.service;

import com.foodit.test.sample.controller.RestaurantData;
import com.foodit.test.solution.bean.dto.LineItem;
import com.foodit.test.solution.bean.dto.Meal;
import com.foodit.test.solution.bean.dto.Order;
import com.foodit.test.solution.bean.dto.Restaurant;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.threewks.thundr.logger.Logger;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by salvatore on 03/08/2014.
 */
public class LoadDataServiceImp implements LoadDataServiceInterface {

    private MenuServiceInterface menuService;

    public void setMenuService(MenuServiceInterface menuService) {
        this.menuService = menuService;
    }

    @Override
    public void loadAllData() {
        List<String> restaurants = Lists.newArrayList("bbqgrill", "butlersthaicafe", "jashanexquisiteindianfood", "newchinaexpress");
        List<RestaurantData> restaurantData = Lists.newArrayList();


        //external cycle on restaurants
        for (String restaurant : restaurants) {
            //read the json files
            String ordersJson = readFile(String.format("orders-%s.json", restaurant));
            String menuJson = readFile(String.format("menu-%s.json", restaurant));

            // -------- menu data
            // parse
            List<Meal> mealList1 = loadDataRestaurantMenu(restaurant, menuJson);
            ofy().save().entities(mealList1).now();

            // --------- this is the old sample data structure (I won't use it)
            restaurantData.add(new RestaurantData(restaurant, menuJson, ordersJson));

            // --------- order data
            // parse
            Order[] orders =  loadDataTest(restaurant, ordersJson);
            // this is a de-normalised object that is updated at query time (insert/update) and that will
            // be accessed whenever an aggregation operation is required.
            Restaurant restaurantObject = new Restaurant(restaurant);
            //medium cycle on each order
            for (int i = 0; i < orders.length; i++) {

                Order order = orders[i];
                ofy().save().entities(order);

                // now update the Restaurant object
                float oldTotalAmountOfSales = restaurantObject.getTotalAmountOfSales();
                int oldTotalNumberOfOrders = restaurantObject.getTotalNumberOfOrders();

                restaurantObject.setTotalAmountOfSales(oldTotalAmountOfSales + order.getTotalValue());
                restaurantObject.setTotalNumberOfOrders(++oldTotalNumberOfOrders);
                //internal cycle on lineItems
                Set<LineItem> lineItemSet = order.getLineItems();
                for (Iterator<LineItem> iterator = lineItemSet.iterator(); iterator.hasNext(); ) {
                    LineItem lineItem = iterator.next();
                    //get category
                    //Logger.debug("\nline item id = " + lineItem.getId() + " restaurant = " + restaurant + " order = " + order.getId());
                    List<Meal> mealList = ofy().load().type(Meal.class).filter("mealId", lineItem.getId()).filter("restaurantName", restaurant).list();
                    Meal meal;
                    // there are two issues with the original data:
                    // 1. sometimes there is an order that has no correspondence with a menu. For example the restaurant
                    //      bbqgrill has, for the order 5014180675452928, the lineItem with id 39 and there is no such an
                    //      item in the corresponding menu. In this case of course we can not consider the item in
                    //      the count of the categories
                    // 2. the delivery charge has no id. No a big problem but we need to deal with potential null values
                    if(mealList.size() == 0){
                        Logger.warn("THERE IS NO ENTRY IN THE MENU FOR THE ORDER: " + order.getId() +
                                " RESTAURANT " + restaurant + " ITEM " + lineItem.getId());
                    } else {
                        // update of the Restaurant object. Here we keep track of the category of the items in the order
                        meal = mealList.get(0);
                        Map<String, Long> map = restaurantObject.getCategoryMap();
                        int quantity = lineItem.getQuantity();
                        String mealCategory = meal.getMealCategory();
                        Long count = map.get(mealCategory);
                        if(count == null) {
                            count = new Long(quantity);
                        } else {
                            count = count + quantity;
                        }
                        map.put(mealCategory, count);
                        restaurantObject.setCategoryMap(map);
                    }
                }

            }
            ofy().save().entity(restaurantObject);
        }
        ofy().save().entities(restaurantData);
    }

    // ----------------- private methods ----------------------

    private Order[] loadDataTest(String restaurantName, String ordersJson) {
        //TODO probably not the best place where declare and initialize the Gson serializer.
        Gson gson = new Gson();
        Order[] orders = gson.fromJson(ordersJson, Order[].class);
        return orders;
    }

    private List<Meal> loadDataRestaurantMenu(String restaurantName, String menuJson){
        //TODO the file has already been read. Refactor!
        try {
            return menuService.parse(menuJson, restaurantName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readFile(String resourceName) {
        URL url = Resources.getResource(resourceName);
        try {
            return IOUtils.toString(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
