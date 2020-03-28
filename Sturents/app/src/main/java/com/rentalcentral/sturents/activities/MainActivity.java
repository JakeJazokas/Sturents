package com.rentalcentral.sturents.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.rentalcentral.sturents.model.Listing;
import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.model.SavedListingArray;
import com.rentalcentral.sturents.ui.RentalCardView;
import com.rentalcentral.sturents.utils.DisplayUtils;
import com.rentalcentral.sturents.utils.FileUtils;
import com.rentalcentral.sturents.utils.JsonUtils;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Change the top bar to use custom xml layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar_layout);
        getSupportActionBar().setElevation(0);

        mSwipeView = findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        //Check if the cache file exists, if it does not then create a new blank ones.
        File cacheFile = new File(getApplicationContext().getFilesDir(), "saved_data.srl");
        if(!cacheFile.exists()){
            //Create cache file
            FileUtils.createCacheFile(mContext, "saved_data.srl", "", false);
            //Write blank array to cache file
            FileUtils.writeSerializableListingArray(mContext, new SavedListingArray(), "saved_data.srl");
        }

        //Load the shared prefs file in order to get saved values
        SharedPreferences prefs = getDefaultSharedPreferences(getApplicationContext());

        // Check dark mode state from preferences
        String key = "dark_mode_prefs";
        String default_string = "";
        String return_value = prefs.getString(key, default_string);
        if(return_value.equals("") || return_value.equals("Light Mode")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


        int bottomMargin = DisplayUtils.dpToPx(120);
        Point windowSize = DisplayUtils.getDisplaySize(getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.sturents_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.sturents_swipe_out_msg_view));

        List<Listing> listingList = null;
        try {
            listingList = JsonUtils.loadProfiles(this.getApplicationContext(), "http://ec2-3-88-194-44.compute-1.amazonaws.com:3000/ottawa");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Listing listing : listingList){
            mSwipeView.addView(new RentalCardView(mContext, listing, mSwipeView));
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        //Add action listener for the image button on the action bar
        ImageButton actionBarButton = findViewById(R.id.topBarButton);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyListingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton actionBarSettingsButton = findViewById(R.id.topBarSettingsButton);
        actionBarSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MySettingsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
