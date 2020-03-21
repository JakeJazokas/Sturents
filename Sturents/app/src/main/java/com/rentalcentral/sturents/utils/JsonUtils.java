package com.rentalcentral.sturents.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.model.Listing;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JsonUtils {


    //Parse all the data from the json files into a 'Profile' containing a specific listing
    public static List<Listing> loadProfiles(Context context) throws ExecutionException, InterruptedException {

        //Log.d("something","its shit " + data);
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
    private static String loadJSONFromResources(Context context) throws ExecutionException, InterruptedException {
        String json = null;
        String data;
        data = new DataUtils(context).execute("http://ec2-18-212-6-101.compute-1.amazonaws.com:3000/listings").get();
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
