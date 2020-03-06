package com.rentalcentral.sturents.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.adapters.RecyclerViewAdapter;
import com.rentalcentral.sturents.model.Listing;
import com.rentalcentral.sturents.model.SavedListingArray;
import com.rentalcentral.sturents.utils.FileUtils;

import java.util.ArrayList;

public class MyListingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sturents_user_listings);

        //Change the top bar to use custom xml layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar_layout_back);
        getSupportActionBar().setElevation(0);

        //Get saved listings and populate view
        ArrayList<ArrayList<String>> values = getSavedListingValues();
        populateView(values.get(0), values.get(1));

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private ArrayList<ArrayList<String>> getSavedListingValues(){
        //Load cached file
        SavedListingArray savedListings = FileUtils.readSerializableListingArray(getApplicationContext(), "saved_data.srl");
        ArrayList<String> titleValues = new ArrayList<String>();
        ArrayList<String> imageValues = new ArrayList<String>();
        for(Listing l : savedListings.getListings()){
            titleValues.add(l.getTitle());
            imageValues.add(l.getFirstImage());
        }
        ArrayList<ArrayList<String>> outArray = new ArrayList<ArrayList<String>>();
        outArray.add(titleValues);
        outArray.add(imageValues);
        return outArray;
    }

    //Populate the view with saved listings
    private void populateView(ArrayList<String> titleVals, ArrayList<String> imageVals){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titleVals, imageVals);
        RecyclerView myView = findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);
    }
}
