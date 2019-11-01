package com.cmpe277.studentmarketplace;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Post {
    private String name,description,category;
    private String ownerEmail;
    private int itemId;
    private double price;
    private ArrayList<Bitmap> associated_pics;

    public Post(int id, String n, String d, String e, String c,double p, ArrayList<Bitmap> i){
        this.name=n;
        this.description=d;
        this.associated_pics=i;
        this.ownerEmail=e;
        this.itemId = id;
        this.category =c;
        this.price = p;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public ArrayList<Bitmap> getAllImages(){
        return associated_pics;
    }

    public String getOwnerEmail(){ return ownerEmail; }

    public Bitmap getMainImage(){
        if(associated_pics != null && associated_pics.size() > 0)
            return associated_pics.get(0);
        else return null;
    }

    public  int getItemId(){
        return itemId;
    }

    public String getCategory(){
        return category;
    }

    public double getPrice(){
        return price;
    }

}
