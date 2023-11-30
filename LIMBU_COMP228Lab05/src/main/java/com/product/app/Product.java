package com.product.app;

public class Product {

    //variable declaration
    private int product_id;
    private String product_name;
    private String product_company;
    private String product_price;

    //constructor
    public Product(int product_id, String product_name, String product_company, String product_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_company = product_company;
        this.product_price = product_price;
    }

    //getter and setter

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_company() {
        return product_company;
    }

    public void setProduct_company(String product_company) {
        this.product_company = product_company;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
