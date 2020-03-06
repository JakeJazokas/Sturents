package com.rentalcentral.sturents.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.rentalcentral.sturents.model.Listing;
import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.model.SavedListingArray;
import com.rentalcentral.sturents.ui.RentalCardView;
import com.rentalcentral.sturents.utils.DisplayUtils;
import com.rentalcentral.sturents.utils.FileUtils;
import com.rentalcentral.sturents.utils.JsonUtils;

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

        //TODO Check if the local storage file is present
        //TODO if it is present then remove saved listings that are already present
        FileUtils.createCacheFile(mContext, "saved_data.srl", "", false);
        //Write blank array
        FileUtils.writeSerializableListingArray(mContext, new SavedListingArray(), "saved_data.srl");

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

        List<Listing> listingList = JsonUtils.loadProfiles(this.getApplicationContext());

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
