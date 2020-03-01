package com.rentalcentral.sturents;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ExpandedCardViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sturents_expanded_card_view);

        //Change the top bar to use custom xml layout
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.top_bar_layout_back);
        getSupportActionBar().setElevation(0);

        //Grab the description and images that were passed to this activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageUrl = extras.getString("imgUrl");
            String description = extras.getString("listingDescription");
            String title = extras.getString("listingTitle");

            TextView expandedDescription = findViewById(R.id.expandedDescription);
            TextView expandedTitle = findViewById(R.id.expandedTitle);
            ImageView expandedImage = findViewById(R.id.expandedImage);

            expandedDescription.setText(description);
            expandedTitle.setText(title);
            Glide.with(getApplicationContext()).load(imageUrl).into(expandedImage);
        }

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
