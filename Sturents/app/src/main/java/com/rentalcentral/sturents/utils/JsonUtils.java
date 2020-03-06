package com.rentalcentral.sturents.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.model.Listing;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //Parse all the data from the json files into a 'Profile' containing a specific listing
    public static List<Listing> loadProfiles(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromResources(context));
            List<Listing> listingList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Listing listing = gson.fromJson(array.getString(i), Listing.class);
                listingList.add(listing);
            }
            return listingList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Loads the JSON file from the resources folder
    //The json file is named listings.json
    private static String loadJSONFromResources(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.nodelistings);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
