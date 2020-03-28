package com.rentalcentral.sturents.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.rentalcentral.sturents.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MySettingsActivity extends AppCompatActivity {

    TextView locationRangeText, priceRangeText;
    SeekBar locationRangeSeek, priceRangeSeek;
    Spinner citySpinner, modeSpinner;

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
        citySpinner = findViewById(R.id.citySpinner);
        modeSpinner = findViewById(R.id.modeSpinner);

        //Load the shared prefs file in order to get saved values
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());

        //Call the methods to initialize listeners
        placeSelectionDropdown();
        locationRangeSeekBar(prefs);
        priceRangeSeekBar(prefs);
        darkMode();

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }}

    private void placeSelectionDropdown(){
        //Create the adapter and use the android default layouts for the spinner drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities_array, R.layout.location_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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

    public void darkMode() {
        //Create the adapter and use the android default layouts for the spinner drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mode_array, R.layout.location_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);


        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    setState("Toggle", true, getApplicationContext());
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    setState("Toggle", false, getApplicationContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public static void setState(String key, Boolean value, Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Funciton to retrieve the switch state
    public static Boolean getState(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }
}

