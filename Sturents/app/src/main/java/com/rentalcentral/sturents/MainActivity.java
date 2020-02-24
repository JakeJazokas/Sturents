package com.rentalcentral.sturents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class MainActivity extends AppCompatActivity {

//    TextView basicTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //find text
//        basicTextView = findViewById(R.id.textview1);
//        //Call parser
//        String json = null;
//        try {
//            InputStream is = getResources().openRawResource(R.raw.listings);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        //Log.d("LISTINGS", json);
//        Gson gson = new Gson();
//        Wrapper[] arr = gson.fromJson(json, Wrapper[].class);
//        //The textview
//        String displayText = "";
//        for(int i = 0; i < arr.length; i++){
//            displayText = displayText.concat(arr[i].description);
//            displayText+= "\n============\n";
//        }
//        basicTextView.setText(displayText);
//        basicTextView.setMovementMethod(new ScrollingMovementMethod());
//    }
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.sturents_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.sturents_swipe_out_msg_view));


        for(Profile profile : Utils.loadProfiles(this.getApplicationContext())){
            mSwipeView.addView(new RentalCardView(mContext, profile, mSwipeView));
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
    }
}

//class Wrapper{
//    String description;
//    String title;
//    String url;
//}
