package com.foodit.test.solution.dto;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
