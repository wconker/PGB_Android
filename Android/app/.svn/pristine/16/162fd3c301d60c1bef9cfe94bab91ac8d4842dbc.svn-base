package com.android.pgb.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.pgb.Adapter.SpinnerAdapter;
import com.android.pgb.R;
import com.android.pgb.Utils.Log;

/**
 * Created by conker on 2017/1/29.
 */

public class ControlUtil {

    private Context mContent;
    private LinearLayout.LayoutParams layoutParams;

    public ControlUtil(Context context) {


        this.mContent = context;


    }

    public TextView getTextview(String text) {

        TextView tv = new TextView(this.mContent);
        tv.setText(text);

        return tv;

    }

    public EditText getEditText(final String content) {

        final EditText editText = new EditText(this.mContent);


        editText.setHint(content);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                }
                else
                {
                    Log.e("Onfp"+editText.getText());
                }
            }
        });

        return editText;

    }

    public Spinner getSpinner(String[]  minzuStrings, Activity activity) {



        Spinner  minzuSpinner =new Spinner(mContent);
        SpinnerAdapter adapter = new SpinnerAdapter(activity,
                R.layout.a_spinner_checked_text, minzuStrings, minzuSpinner);
        adapter.setDropDownViewResource(R.layout.a_spinner_item_layout);
        minzuSpinner.setAdapter(adapter);
        return minzuSpinner;

    }


    public ImageView getImageview(Uri imageUrl){


        ImageView imageView = new ImageView(mContent);


        imageView.setImageURI(imageUrl);
        return  imageView;

    }





}
