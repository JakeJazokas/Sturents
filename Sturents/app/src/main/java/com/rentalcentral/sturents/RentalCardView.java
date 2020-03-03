package com.rentalcentral.sturents;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

@Layout(R.layout.sturents_card_view)
public class RentalCardView {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public RentalCardView(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        //Load the first image
        Glide.with(mContext).load(mProfile.getFirstImage()).into(profileImageView);
        nameAgeTxt.setText(mProfile.getTitle());
        locationNameTxt.setText(mProfile.getDescription());
    }

    @Click(R.id.profileImageView)
    private void onClickedCardImage(){
        Log.d("EVENT", "clickedCardImage");
        Intent intent = new Intent(mContext, ExpandedCardViewActivity.class);
        //Flag for starting activity outside of main
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Send the image urls, description, and title values to the new activity
        intent.putExtra("listingImages", mProfile.getImages());
        intent.putExtra("listingDescription", mProfile.getFullDescription());
        intent.putExtra("listingTitle", mProfile.getTitle());
        mContext.startActivity(intent);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        //This adds the listing back to the queue of listings of the user rejects it
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Utils.createCacheFile(mContext, "config.json", mProfile.getTitle() + "~END~", true);
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}
