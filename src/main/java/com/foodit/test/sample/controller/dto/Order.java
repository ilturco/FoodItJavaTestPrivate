package com.foodit.test.sample.controller.dto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.threewks.thundr.gae.objectify.repository.RepositoryEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by salvatore on 31/07/2014.
 */
@Entity
public class Order implements RepositoryEntity {
    @Id
    private Long orderId;
    @Index
    private String storeId;
    private float totalValue;
    //with this specific version of gson there are issues with inner classes. With 2.2.4 they work just fine.
    //TODO if there is enough time let's see whether it is possible to use an inner class...cleaner!
    private Set<LineItem> lineItems = new HashSet<>();

    public Order() {
    }

    public Order(Long orderId, String storeId, float totalValue, Set<LineItem> lineItems) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.totalValue = totalValue;
        this.lineItems = lineItems;
    }

    public Order(String storeId) {
        this.storeId = storeId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    @Override
    public Long getId() {
        return orderId;
    }


    public String getStoreId() {
        return storeId;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        return true;
    }

}
