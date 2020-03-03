package com.rentalcentral.sturents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public ArrayList<String> myTitleValues;
    public ArrayList<String> myImageValues;

    public RecyclerViewAdapter (ArrayList<String> myTValues, ArrayList<String> myIValues){
        this.myTitleValues = myTValues;
        this.myImageValues = myIValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.sturents_user_listing_card, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.myTextView.setText(myTitleValues.get(position));
        Glide.with(holder.myImageView.getContext())
                .load(myImageValues.get(position))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.myImageView);
    }

    @Override
    public int getItemCount() {
        return myTitleValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView myTextView;
        private ImageView myImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.userListingCardText);
            myImageView = itemView.findViewById(R.id.userListingCardImage);
        }
    }
}
