package com.rentalcentral.sturents.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;


import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;



public class DataUtils extends AsyncTask<String, Void, String> {

    private Context context;

    public DataUtils(Context context){
        this.context = context;
    }

    private Exception exception;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected String doInBackground(String... urls){
        String data = "";

        Gson gson = new Gson();
        try {

            URL url = new URL("http://ec2-18-212-6-101.compute-1.amazonaws.com:3000/listings");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                data += line;
            }

            urlConnection.disconnect();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //FileUtils.writeSerializableJSON(context, data, "nodelistings.json");
        try (FileWriter file = new FileWriter("nodelistings.json")) {

            file.write(data);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
    protected void onPostExecute(String data) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

}
