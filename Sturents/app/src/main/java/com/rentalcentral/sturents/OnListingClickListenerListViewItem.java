package com.rentalcentral.sturents;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class OnListingClickListenerListViewItem implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Context context = view.getContext();

        TextView textViewItem = (view.findViewById(R.id.userListingItem));

        String listItemText = textViewItem.getText().toString();

        //For now just log that the item was clicked
        Log.d("CLICKEDLISTING", listItemText);
    }

}