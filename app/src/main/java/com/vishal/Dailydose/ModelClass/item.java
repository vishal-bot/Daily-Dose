package com.vishal.Dailydose.ModelClass;

public class item {
    private String Name,Image,Quantity;
    private long Price;

    public item(){

    }

    public item(String name, String image, long price, String quantity){
        Name = name;
        Image = image;
        Price = price;
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }
    public long getPrice() {
        return Price;
    }
    public void setPrice(long price) {
        Price = price;
    }
    public String getQuantity() {
        return Quantity;
    }
    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

}
