package com.rentalcentral.sturents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Listing implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("images")
    @Expose
    private String[] images;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("price")
    @Expose
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getFirstImage() {
        //Check if there are any images
        if(images.length > 0) {
            return images[0];
        }else{
            return "";
        }
    }

    public void setFirstImage(String image) {
        this.images[0] = image;
    }

    public String getDescription() {
        //Replace all newline chars
        String parsedDescription = description.replace('\n',' ');
        //Check length of description
        if(parsedDescription.length() > 280){
            return parsedDescription.substring(0,280).concat("...");
        }
        else{
            return parsedDescription;
        }
    }

    public String getFullDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
