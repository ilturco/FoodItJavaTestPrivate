package com.foodit.test.sample.controller.dto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by salvatore on 31/07/2014.
 */
@Entity
public class Order {
    @Id
    private Long orderId;
    private String storeId;
    private float totalValue;
    //with this specific version of gson there are issues with inner classes. With 2.2.4 they work just fine.
    //TODO if there is enough time let's see whether it is possible to use an inner class...cleaner!
    private Set<LineItem> lineItems = new HashSet<>();

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", storeId='" + storeId + '\'' +
                ", totalValue=" + totalValue +
                ", lineItems=" + lineItems +
                '}';
    }


}
