package com.example.monte.ondb;

/**
 * Created by monte on 6/19/2019.
 */

public class List_Item {
    public String room;
    public String floor;
    public String price;
    public String img;
    public List_Item(String room,String floor,String price,String image){
        this.room=room;
        this.floor=floor;
        this.price=price;
        this.img=image;

    }

    public String getImage() {
        return img;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public String getPrice() {
        return price;
    }
}

