package com.foodit.test.solution.service;

import com.foodit.test.solution.bean.frontend.AmountOfMoneyForARestaurant;
import com.foodit.test.solution.bean.frontend.NumberOfOrdersForARestaurant;

import java.util.Set;

/**
 * Created by salvatore on 01/08/2014.
 */
public interface OrderServiceInterface {

    public int getNumberOfOrders(String restaurant);

    public Set<NumberOfOrdersForARestaurant> getNumberOfOrdersForEachRestaurant();

    public float getTotalAmountOfMoney(String restaurant);

    public Set<AmountOfMoneyForARestaurant> getTotalAmountOfMoneyForEachRestaurant();


}
