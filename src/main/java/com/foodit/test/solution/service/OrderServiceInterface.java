package com.foodit.test.solution.service;

import com.foodit.test.solution.bean.frontend.AmountOfMoneyForARestaurant;
import com.foodit.test.solution.bean.frontend.NumberOfOrdersForARestaurant;

import java.util.List;

/**
 * Created by salvatore on 01/08/2014.
 */
public interface OrderServiceInterface {

    public int getNumberOfOrders(String restaurant);

    public List<NumberOfOrdersForARestaurant> getNumberOfOrdersForEachRestaurant();

    public float getTotalAmountOfMoney(String restaurant);

    public List<AmountOfMoneyForARestaurant> getTotalAmountOfMoneyForEachRestaurant();


}
