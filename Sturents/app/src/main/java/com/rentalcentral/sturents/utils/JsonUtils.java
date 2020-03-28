package com.rentalcentral.sturents.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rentalcentral.sturents.model.Listing;

import org.json.JSONArray;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JsonUtils {

    //Parse all the data from the json files into a 'Profile' containing a specific listing
    public static List<Listing> loadProfiles(Context context, String url) throws ExecutionException, InterruptedException {
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromResources(context, url));
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
    private static String loadJSONFromResources(Context context, String url) throws ExecutionException, InterruptedException {
        String json = null;
        String data;
        data = new DataUtils(context, url).execute(url).get();
        InputStream inputStream = new ByteArrayInputStream(data.getBytes(Charset.forName("UTF-8")));
        try {
            InputStream is = inputStream;
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
