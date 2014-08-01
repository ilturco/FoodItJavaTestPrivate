package com.foodit.test.solution.controller;

import com.foodit.test.solution.service.OrderServiceInterface;
import com.threewks.thundr.view.json.JsonView;

/**
 * Created by salvatore on 01/08/2014.
 */
public class DataSearchController {

    private OrderServiceInterface orderService;

    public void setOrderService(OrderServiceInterface orderService) {
        this.orderService = orderService;
    }

    public JsonView getTotalNumberOfOrders(){

        int number =  orderService.getNumberOfOrder("bbqgrill");
        JsonView result = new JsonView(number);

        return result;

    }

    public JsonView getTotalAmountOfMoney(){

        JsonView result = new JsonView("not yet implemented");

        return result;

    }

    public JsonView getMostFrequentlyOrderedMeal(){

        JsonView result = new JsonView("not yet implemented");

        return result;

    }

    public JsonView getMostFrequentlyOrderedCategory(){

        JsonView result = new JsonView("not yet implemented");

        return result;

    }
}
