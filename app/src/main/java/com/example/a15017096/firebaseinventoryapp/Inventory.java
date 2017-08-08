package com.example.a15017096.firebaseinventoryapp;

import android.text.Html;

import java.io.Serializable;

/**
 * Created by 15017096 on 1/8/2017.
 */

public class Inventory implements Serializable {
    private String id;
    private String name;
    private String brand;
    private int cost;

    public Inventory(){}

    public Inventory(String name, int cost, String brand) {
        this.name = name;
        this.cost = cost;
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return brand+"\n"+name+"\nPrice: $"+cost;
    }
}
