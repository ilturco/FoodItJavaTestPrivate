package com.foodit.test.solution.controller;

import com.foodit.test.solution.bean.dto.Restaurant;
import com.foodit.test.solution.bean.frontend.AmountOfMoneyForARestaurant;
import com.foodit.test.solution.bean.frontend.MealFrontEnd;
import com.foodit.test.solution.bean.frontend.MostFrequentCategory;
import com.foodit.test.solution.bean.frontend.NumberOfOrdersForARestaurant;
import com.foodit.test.solution.service.MenuServiceInterface;
import com.foodit.test.solution.service.OrderServiceInterface;
import com.threewks.thundr.view.json.JsonView;

import java.util.Set;

/**
 * Created by salvatore on 01/08/2014.
 */
public class DataSearchController {

    private OrderServiceInterface orderService;
    private MenuServiceInterface menuService;


    public void setOrderService(OrderServiceInterface orderService) {
        this.orderService = orderService;
    }
    public void setMenuService(MenuServiceInterface menuService) {
        this.menuService = menuService;
    }

    public JsonView getTotalNumberOfOrders(String restaurant){

        int number =  orderService.getNumberOfOrders(restaurant);
        JsonView result = new JsonView(number);

        return result;

    }

    public JsonView getTotalNumberOfOrdersForEachRestaurant(){

        Set<NumberOfOrdersForARestaurant> restaurants =  orderService.getNumberOfOrdersForEachRestaurant();
        JsonView result = new JsonView(restaurants);

        return result;

    }

    public JsonView getTotalAmountOfMoney(String restaurant){

        float number =  orderService.getTotalAmountOfMoney(restaurant);
        JsonView result = new JsonView(number);

        return result;

    }

    public JsonView getTotalAmountOfMoneyForEachRestaurant(){

        Set<AmountOfMoneyForARestaurant> restaurants =  orderService.getTotalAmountOfMoneyForEachRestaurant();
        JsonView result = new JsonView(restaurants);

        return result;

    }

    public JsonView getMostFrequentlyOrderedMeal(){

        Set<MealFrontEnd> mealFrontEnds =  orderService.getMostFrequentlyOrderedMeals();
        JsonView result = new JsonView(mealFrontEnds);

        return result;

    }

    public JsonView getMostFrequentlyOrderedCategory(String restaurant){

        MostFrequentCategory mostFrequentCategory =  menuService.getMostFrequentlyOrderedCategory(restaurant);
        JsonView result = new JsonView(mostFrequentCategory);

        return result;

    }

    public JsonView getMostFrequentlyOrderedCategoryForEachRestaurant(){

        Set<MostFrequentCategory> mostFrequentCategorySet =  menuService.getMostFrequentlyOrderedCategoryForEachRestaurant();
        JsonView result = new JsonView(mostFrequentCategorySet);

        return result;

    }

    public JsonView getRestaurantsStats(){

        Set<Restaurant> restaurantSet =  orderService.getRestaurantStats();
        JsonView result = new JsonView(restaurantSet);

        return result;

    }
}
