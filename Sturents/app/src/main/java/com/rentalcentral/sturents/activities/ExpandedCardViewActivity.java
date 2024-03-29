package com.rentalcentral.sturents.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.adapters.SliderAdapter;
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

        //Populate all the data
        populateViews();

        //Go back to the main activity when the back button is pressed
        ImageButton actionBarButton = findViewById(R.id.topBarButtonBack);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateViews(){
        //Grab the description and images that were passed to this activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] images = extras.getStringArray("listingImages");
            String description = extras.getString("listingDescription");
            String title = extras.getString("listingTitle");

            int price = extras.getInt("listingPrice");
            int distance = extras.getInt("listingDistance");

            TextView expandedDescription = findViewById(R.id.expandedDescription);
            TextView expandedTitle = findViewById(R.id.expandedTitle);

            TextView expandedLocation = findViewById(R.id.locationIconText);
            TextView expandedPrice = findViewById(R.id.expandedPriceText);
            String expandedLocationString = distance + "KM";
            expandedLocation.setText(expandedLocationString);
            String priceString = "$" + price;
            expandedPrice.setText(priceString);

            expandedDescription.setText(description);
            expandedTitle.setText(title);

            SliderView sliderView = findViewById(R.id.expandedImageSlider);
            SliderAdapter adapter = new SliderAdapter();

            for(int i = 0; i < images.length; i++){
                adapter.addItem(images[i]);
            }

            sliderView.setSliderAdapter(adapter);
            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        }
    }
}
