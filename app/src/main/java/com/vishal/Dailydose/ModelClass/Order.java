package com.vishal.Dailydose.ModelClass;

public class Order {
    private String Name,Image,shop,Address,Status,Payment;
    private int Quantity;
    private long Price;

    public Order(){

    }

    public Order(String name, String image, long price, int quantity, String shop, String address, String status, String payment){
        Name = name;
        Image = image;
        Price = price;
        Quantity = quantity;
        shop = shop;
        Address = address;
        Status = status;
        Payment = payment;
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
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    public String getshop() {
        return shop;
    }
    public void setshop(String shop) {
        shop = shop;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }
    public String getPayment() {
        return Payment;
    }
    public void setPayment(String payment) {
        Payment = payment;
    }
}
