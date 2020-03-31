package com.rentalcentral.sturents.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.activities.ExpandedCardViewActivity;
import com.rentalcentral.sturents.model.Listing;
import com.rentalcentral.sturents.model.SavedListingArray;
import com.rentalcentral.sturents.utils.FileUtils;

@Layout(R.layout.sturents_card_view)
public class RentalCardView {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.descriptionTxt)
    private TextView descriptionTxt;

    @View(R.id.priceLocText)
    private TextView priceLocTxt;

//    @View(R.id.priceText)
//    private TextView priceTxt;

    private Listing mListing;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public RentalCardView(Context context, Listing listing, SwipePlaceHolderView swipeView) {
        mContext = context;
        mListing = listing;
        mSwipeView = swipeView;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Resolve
    private void onResolved(){
        //Load the first image
        Glide.with(mContext).load(mListing.getFirstImage()).into(profileImageView);
        titleTxt.setText(mListing.getTitle());
        descriptionTxt.setText(mListing.getDescription());
        String priceLocText = mListing.getLocationDistance() + "KM Away, " + mListing.getPrice() + "$/Month";
        priceLocTxt.setText(priceLocText);
    }

    @Click(R.id.profileImageView)
    private void onClickedCardImage(){
        Intent intent = new Intent(mContext, ExpandedCardViewActivity.class);
        //Flag for starting activity outside of main
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Send the image urls, description, and title values to the new activity
        intent.putExtra("listingImages", mListing.getImages());
        intent.putExtra("listingDescription", mListing.getFullDescription());
        intent.putExtra("listingTitle", mListing.getTitle());
        //Send the price and distance to the new activity
        intent.putExtra("listingPrice", mListing.getPrice());
        intent.putExtra("listingDistance", mListing.getLocationDistance());
        mContext.startActivity(intent);
    }

    @SwipeOut
    private void onSwipedOut(){
        //This adds the listing back to the queue of listings of the user rejects it
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){}

    @SwipeIn
    private void onSwipeIn(){
        SavedListingArray savedListings = FileUtils.readSerializableListingArray(mContext, "saved_data.srl");
        savedListings.addListing(mListing);
        FileUtils.writeSerializableListingArray(mContext, savedListings, "saved_data.srl");
    }

    @SwipeInState
    private void onSwipeInState(){}

    @SwipeOutState
    private void onSwipeOutState(){}
}
