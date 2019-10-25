package com.cmpe277.studentmarketplace;

import android.media.Image;

public class Post {
    private String name,description;
    private Image[] associated_pics;
    private String ownerEmail;

    public Post(String n, String d, String e, Image[] i){
        this.name=n;
        this.description=d;
        this.associated_pics=i;
        this.ownerEmail=e;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Image[] getAllImages(){
        return associated_pics;
    }

    public String getOwnerEmail(){ return ownerEmail; }

    public Image getMainImage(){
        if(associated_pics == null || associated_pics.length > 0)
            return associated_pics[0];
        else return null;
    }

}
