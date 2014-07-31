package com.foodit.test.sample.controller.dto;

import com.googlecode.objectify.annotation.Embed;

/**
 * Created by salvatore on 31/07/2014.
 */

//this annotation will no more be needed with objectify 5.x
@Embed
public class LineItem {

    private Long id;
    private int quantity;

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public LineItem() {
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
