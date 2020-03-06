package com.rentalcentral.sturents.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rentalcentral.sturents.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private List<String> mSliderItems = new ArrayList<>();

    public SliderAdapter() { }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        String sliderItem = mSliderItems.get(position);
        Glide.with(viewHolder.itemView.getContext())
                .load(sliderItem)
                .apply(RequestOptions.fitCenterTransform())
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() { return mSliderItems.size(); }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.auto_image_slider);
            this.itemView = itemView;
        }
    }

}
