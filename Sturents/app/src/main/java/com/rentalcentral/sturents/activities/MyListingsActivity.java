package com.rentalcentral.sturents.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.adapters.RecyclerListingViewAdapter;
import com.rentalcentral.sturents.model.SavedListingArray;
import com.rentalcentral.sturents.utils.FileUtils;

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
        SavedListingArray savedListings = FileUtils.readSerializableListingArray(getApplicationContext(), "saved_data.srl");
        populateViewWithListings(savedListings);

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
    private void populateViewWithListings(SavedListingArray savedListings){
        RecyclerListingViewAdapter adapter = new RecyclerListingViewAdapter(savedListings);
        RecyclerView rView = findViewById(R.id.recyclerview);
        rView.setHasFixedSize(true);
        rView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(llm);
    }
}
