package com.example.supplier.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Item {
    @Id
    private UUID id;
    private String name;
    private double price;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier addedBy;

    public Item() {

    }

    public Item(String name, double price, Supplier addedBy) {
        this.name = name;
        this.price = price;
        this.addedBy = addedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Supplier getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Supplier addedBy) {
        this.addedBy = addedBy;
    }

}
