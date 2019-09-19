package com.example.monte.ondb;

/**
 * Created by monte on 6/18/2019.
 */

public class Product {
    private String email;
    private boolean select;


    public Product(String email,boolean select){
        this.email=email;
        this.select=select;


    }

    public String getemail() {
        return email;
    }

    public boolean getselect() {
        return select;
    }


}
