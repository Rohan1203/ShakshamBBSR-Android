package com.ecomhandcrafting.model;

public class Category {
private int catId;
private String catName;
private String catType;

    public Category() {
    }

    public Category(int catId, String catName, String catType) {
        this.catId = catId;
        this.catName = catName;
        this.catType = catType;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }
}
