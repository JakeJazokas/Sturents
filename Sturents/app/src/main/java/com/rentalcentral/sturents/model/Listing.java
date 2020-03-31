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

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("address")
    @Expose
    private String address;

    private int locationDistance;

    public int getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(int locationDistance) {
        this.locationDistance = locationDistance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

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
