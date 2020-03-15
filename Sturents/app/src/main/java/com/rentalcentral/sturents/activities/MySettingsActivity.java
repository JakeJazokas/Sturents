package com.rentalcentral.sturents.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.rentalcentral.sturents.R;

public class MySettingsActivity extends AppCompatActivity {

    private TextView locationView;
    private ProgressBar locationProgressBar;
    private SeekBar locationSeekBar;
    private TextView priceView;
    private ProgressBar priceProgressBar;
    private SeekBar priceSeekBar;
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

        //creating location slider
        locationView = (TextView) findViewById(R.id.locationView);
        locationProgressBar = (ProgressBar) findViewById(R.id.locationProgressBar);
        locationSeekBar = (SeekBar) findViewById(R.id.locationSeekBar);

        locationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                locationProgressBar.setProgress(progress);
                locationView.setText("" + progress + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //creating price slider

        priceView = (TextView) findViewById(R.id.priceView);
        priceProgressBar = (ProgressBar) findViewById(R.id.priceProgressBar);
        priceSeekBar = (SeekBar) findViewById(R.id.priceSeekBar);

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceProgressBar.setProgress(progress);
                priceView.setText("$" + progress );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }}

}
