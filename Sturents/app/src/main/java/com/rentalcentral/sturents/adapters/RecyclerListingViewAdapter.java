package com.rentalcentral.sturents.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rentalcentral.sturents.R;
import com.rentalcentral.sturents.activities.ExpandedCardViewActivity;
import com.rentalcentral.sturents.model.Listing;
import com.rentalcentral.sturents.model.SavedListingArray;
import com.rentalcentral.sturents.utils.FileUtils;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

public class RecyclerListingViewAdapter extends RecyclerView.Adapter<RecyclerListingViewAdapter.ListingViewHolder> {

    private static SavedListingArray savedListingArray;

    public RecyclerListingViewAdapter (SavedListingArray myListings){
        savedListingArray = myListings;
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_card_menu_layout, parent, false);
        return new ListingViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final ListingViewHolder holder, final int position) {
        holder.myTextView.setText(getTitleAtPosition(position));
        //Listing circle image
        Glide.with(holder.myImageView.getContext())
                .load(getImageAtPosition(position))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.myImageView);
        //Create the contact swipe button via glide
        Glide.with(holder.myContactButton.getContext())
                .load(R.drawable.ic_message_3)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.myContactButton);
        //Create the delete swipe button
        Glide.with(holder.myContactButton2.getContext())
                .load(R.drawable.ic_cancel)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.myContactButton2);
        //Create a onclick listener for the delete button
        holder.myContactButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedListingArray.removeListing(savedListingArray.getListings().get(position));
                notifyDataSetChanged();
                holder.swipeHorizontalMenuLayout.smoothCloseMenu(250);
                FileUtils.writeSerializableListingArray(holder.itemView.getContext(), savedListingArray, "saved_data.srl");
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedListingArray.getListings().size();
    }

    private String getTitleAtPosition(int position){
        return savedListingArray.getListings().get(position).getTitle();
    }

    private String getImageAtPosition(int position){
        return savedListingArray.getListings().get(position).getFirstImage();
    }

    public static class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView myTextView;
        private ImageView myImageView;
        private ImageView myContactButton;
        private ImageView myContactButton2;
        private SwipeHorizontalMenuLayout swipeHorizontalMenuLayout;

        public ListingViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.userListingCardText);
            myImageView = itemView.findViewById(R.id.userListingCardImage);
            myContactButton = itemView.findViewById(R.id.btnContact);
            myContactButton2 = itemView.findViewById(R.id.btnContact2);
            swipeHorizontalMenuLayout = itemView.findViewById(R.id.swipeMenuLayout);
            //Click listener for the contact button
            myContactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Listing mListing = savedListingArray.getListings().get(position);
                    v.getContext().startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(mListing.getUrl())));
                    swipeHorizontalMenuLayout.smoothCloseMenu(250);
                }
            });
            //Set the onClick listener to use the onClick method below
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Get the current position and the listing at the position
            int position = getAdapterPosition();
            Listing mListing = savedListingArray.getListings().get(position);
            //Create an intent
            Intent intent = new Intent(v.getContext(), ExpandedCardViewActivity.class);
            //Flag for starting activity outside of main
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Send the image urls, description, and title values to the new activity
            intent.putExtra("listingImages", mListing.getImages());
            intent.putExtra("listingDescription", mListing.getFullDescription());
            intent.putExtra("listingTitle", mListing.getTitle());
            //Send the price and distance to the new activity
            intent.putExtra("listingPrice", mListing.getPrice());
            intent.putExtra("listingDistance", mListing.getLocationDistance());
            v.getContext().startActivity(intent);
        }
    }
}
