package com.rentalcentral.sturents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyListingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sturents_user_listings);

        //Change the top bar to use custom xml layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar_layout_back);
        getSupportActionBar().setElevation(0);

        //Load cached file
        File cacheFile = new File(getFilesDir(), "config.json");
        String JSONProfiles = Utils.readFile(cacheFile);
        Log.d("SAVED", JSONProfiles);

        //Split at the value ~END~
        String[] values = JSONProfiles.split("~END~");
        //Split into title and image values
        ArrayList<String> titleValues = new ArrayList<String>();
        ArrayList<String> imageValues = new ArrayList<String>();
        for(int i = 0; i < values.length-1; i++){
            if(i%2==0){
                titleValues.add(values[i]);
            }
            else{
                imageValues.add(values[i]);
            }
        }
        populateView(titleValues, imageValues);

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Populate the view with saved listings
    private void populateView(ArrayList<String> titleVals, ArrayList<String> imageVals){
//        ArrayList<String> myValues = new ArrayList<String>();
//        myValues.addAll(Arrays.asList(values).subList(0,values.length-1));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titleVals, imageVals);
        RecyclerView myView = findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);
    }
}
