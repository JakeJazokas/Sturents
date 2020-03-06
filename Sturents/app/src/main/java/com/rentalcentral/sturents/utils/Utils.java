package com.rentalcentral.sturents.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rentalcentral.sturents.model.Profile;
import com.rentalcentral.sturents.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    //Parse all the data from the json files into a 'Profile' containing a specific listing
    public static List<Profile> loadProfiles(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromResources(context));
            List<Profile> profileList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Profile profile = gson.fromJson(array.getString(i), Profile.class);
                profileList.add(profile);
            }
            return profileList;
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

    //Get the display size
    public static Point getDisplaySize(WindowManager windowManager) {
        try {
            if (Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                return new Point(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    //Convert DP to pixels
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    //Write JSON saved data to file
    public static File createCacheFile(Context context, String fileName, String json, boolean appendValue) {
        File cacheFile = new File(context.getFilesDir(), fileName);
        try {
            FileWriter fw = new FileWriter(cacheFile, appendValue);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            cacheFile = null;
        }

        return cacheFile;
    }

    //Read JSON saved data from file
    public static String readFile(File file) {
        String fileContent = "";
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((currentLine = br.readLine()) != null) {
                fileContent += currentLine + '\n';
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            fileContent = null;
        }
        return fileContent;
    }
}