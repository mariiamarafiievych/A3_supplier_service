package com.example.supplier.entities.dto;


import com.example.supplier.entities.Item;

import java.util.List;

public class ItemsDTO {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ThingsDTO{" +
                "items=" + items +
                '}';
    }
}
