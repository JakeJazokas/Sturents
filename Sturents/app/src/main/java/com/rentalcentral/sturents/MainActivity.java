package com.rentalcentral.sturents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    TextView basicTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find text
        basicTextView = findViewById(R.id.textview1);
        //Call parser
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.listings);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //Log.d("LISTINGS", json);
        Gson gson = new Gson();
        Wrapper[] arr = gson.fromJson(json, Wrapper[].class);
        //The textview
        String displayText = "";
        for(int i = 0; i < arr.length; i++){
            displayText = displayText.concat(arr[i].description);
            displayText+= "\n============\n";
        }
        basicTextView.setText(displayText);
        basicTextView.setMovementMethod(new ScrollingMovementMethod());
    }
}

class Wrapper{
    String description;
    String title;
    String url;
}
