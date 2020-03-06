package com.rentalcentral.sturents.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedListingArray implements Serializable {

    ArrayList<Listing> listings;

    public SavedListingArray(){
        listings = new ArrayList<Listing>();
    }

    public void addListing(Listing l){
        listings.add(l);
    }

    public void removeListing(Listing l){
        listings.remove(l);
    }

    public ArrayList<Listing> getListings(){
        return listings;
    }
}
