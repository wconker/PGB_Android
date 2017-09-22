package com.android.pgb.Activity.Core;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.pgb.R;
import com.bumptech.glide.Glide;

public class Activity_ShowBigImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__show_big_image);

       ImageView bigImg = (ImageView) this.findViewById(R.id.bigImg);

        String url = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(url).asBitmap().into(bigImg);

    }
}
