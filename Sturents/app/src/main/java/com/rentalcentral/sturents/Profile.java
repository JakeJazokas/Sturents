package com.rentalcentral.sturents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("images")
    @Expose
    private String[] images;

    @SerializedName("description")
    @Expose
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setDescription(String description) {
        this.description = description;
    }

    //TODO Crate tostring method
    @Override
    public String toString() {
        return "{ \"title\":" + title + '}';
    }
}
