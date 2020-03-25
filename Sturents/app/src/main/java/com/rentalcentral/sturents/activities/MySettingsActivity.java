package com.rentalcentral.sturents.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.rentalcentral.sturents.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MySettingsActivity extends AppCompatActivity {

    TextView locationRangeText, priceRangeText;
    SeekBar locationRangeSeek, priceRangeSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) { {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sturents_settings_layout);

        //Change the top bar to use custom xml layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar_layout_back);
        getSupportActionBar().setElevation(0);

        //Initialize the settings seek bars and text views
        locationRangeSeek = findViewById(R.id.seekBarLocationRange);
        locationRangeText = findViewById(R.id.textViewLocationRange);
        priceRangeSeek = findViewById(R.id.seekBarPriceRange);
        priceRangeText = findViewById(R.id.textViewPriceRange);

        //Load the shared prefs file in order to get saved values
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());

        //Call the methods to initialize listeners
        locationRangeSeekBar(prefs);
        priceRangeSeekBar(prefs);

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }}

    private void locationRangeSeekBar(final SharedPreferences preferences){
        //Set the max value
        locationRangeSeek.setMax(150);

        //Read preferences from local storage
        String key = "location_range_pref";
        int default_int = -1;
        int return_value = preferences.getInt(key, default_int);
        if(return_value == -1){
            locationRangeSeek.setProgress(0);
        }else{
            locationRangeSeek.setProgress(return_value);
        }

        //Set the initial values
        String initialString = "0 - " + locationRangeSeek.getProgress() + "KM";
        locationRangeText.setText(initialString);

        //SeekBar onChange listener
        locationRangeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedVal = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedVal = progress;
                //Update storage value
                preferences.edit().putInt("location_range_pref", progressChangedVal).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String progressText = "0 - " + progressChangedVal + "KM";
                locationRangeText.setText(progressText);
            }
        });
    }

    private void priceRangeSeekBar(final SharedPreferences preferences){
        //Set the max value
        priceRangeSeek.setMax(3000);

        //Read preferences from local storage
        String key = "price_range_pref";
        int default_int = -1;
        int return_value = preferences.getInt(key, default_int);
        if(return_value == -1){
            priceRangeSeek.setProgress(0);
        }else{
            priceRangeSeek.setProgress(return_value);
        }

        //Set the initial values
        String initialString = "$0 - $" + priceRangeSeek.getProgress();
        priceRangeText.setText(initialString);

        //SeekBar onChange listener
        priceRangeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedVal = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedVal = progress;
                //Update storage value
                preferences.edit().putInt("price_range_pref", progressChangedVal).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String progressText = "$0 - $" + progressChangedVal;
                priceRangeText.setText(progressText);
            }
        });
    }
}
