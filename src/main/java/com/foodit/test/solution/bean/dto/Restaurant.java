package com.foodit.test.solution.bean.dto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by salvatore on 02/08/2014.
 */
@Entity
public class Restaurant {

    @Id
    private Long id;
    @Index
    private String storeId;
    private int totalNumberOfOrders;
    private float totalAmountOfSales;

    public Restaurant(String storeId) {
        this.storeId = storeId;
        this.totalAmountOfSales = 0;
        this.totalNumberOfOrders = 0;

    }

    public Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getTotalNumberOfOrders() {
        return totalNumberOfOrders;
    }

    public void setTotalNumberOfOrders(int totalNumberOfOrders) {
        this.totalNumberOfOrders = totalNumberOfOrders;
    }

    public float getTotalAmountOfSales() {
        return totalAmountOfSales;
    }

    public void setTotalAmountOfSales(float totalAmountOfSales) {
        this.totalAmountOfSales = totalAmountOfSales;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", storeId='" + storeId + '\'' +
                ", totalNumberOfOrders=" + totalNumberOfOrders +
                ", totalAmountOfSales=" + totalAmountOfSales +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (!storeId.equals(that.storeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return storeId.hashCode();
    }
}
