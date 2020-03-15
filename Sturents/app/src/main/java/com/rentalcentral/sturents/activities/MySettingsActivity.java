package com.rentalcentral.sturents.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.rentalcentral.sturents.R;

public class MySettingsActivity extends AppCompatActivity {

    private Button location;

    @Override
    protected void onCreate(Bundle savedInstanceState) { {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sturents_settings_layout);

        //Change the top bar to use custom xml layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar_layout_back);
        getSupportActionBar().setElevation(0);

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //creating location button
        location = findViewById(R.id.location_button);
        location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySettingsActivity.this,Pop.class));
            }
        });
    }}

}
