package com.example.myapplication;

public class CartItem {
    private String product;
    private float price;
    private String orderType;

    // Constructor
    public CartItem(String product, float price, String orderType) {
        this.product = product;
        this.price = price;
        this.orderType = orderType;
    }

    // Getters and setters
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "Product: " + product + ", Price: " + price + " /-";
    }
}
