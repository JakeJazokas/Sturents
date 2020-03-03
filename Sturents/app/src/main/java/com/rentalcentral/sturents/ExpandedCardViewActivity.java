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
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

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
            String[] images = extras.getStringArray("listingImages");
            String description = extras.getString("listingDescription");
            String title = extras.getString("listingTitle");

            TextView expandedDescription = findViewById(R.id.expandedDescription);
            TextView expandedTitle = findViewById(R.id.expandedTitle);
            //ImageView expandedImage = findViewById(R.id.expandedImage);

            expandedDescription.setText(description);
            expandedTitle.setText(title);
            //Glide.with(getApplicationContext()).load(imageUrl).into(expandedImage);

            SliderView sliderView = findViewById(R.id.expandedImageSlider);
            SliderAdapter adapter = new SliderAdapter(this);

            for(int i = 0; i < images.length; i++){
                adapter.addItem(images[i]);
            }
            
            sliderView.setSliderAdapter(adapter);
            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
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
