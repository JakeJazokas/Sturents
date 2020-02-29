package com.rentalcentral.sturents;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

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

        TextView temp = findViewById(R.id.userListingsText);
        temp.setText(JSONProfiles);

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
