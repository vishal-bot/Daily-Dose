package com.vishal.Dailydose.ModelClass;

public class Cart {
    private String Name,Image,shop;
    private int Quantity;
    private long Price;

    public Cart(){

    }

    public Cart(String name, String image, long price, int quantity, String shop){
        Name = name;
        Image = image;
        Price = price;
        Quantity = quantity;
        shop = shop;
    }

    public String getName() {
        return Name;
    }
    public String getImage() {
        return Image;
    }
    public long getPrice() {
        return Price;
    }
    public int getQuantity() {
        return Quantity;
    }
    public String getshop() {
        return shop;
    }
}
