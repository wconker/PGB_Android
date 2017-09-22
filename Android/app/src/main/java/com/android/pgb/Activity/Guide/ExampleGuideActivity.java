package com.android.pgb.Activity.Guide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.pgb.Activity.Activity_Login;
import com.android.pgb.R;
import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SingleElement;
import com.chechezhi.ui.guide.SinglePage;

import java.util.ArrayList;
import java.util.List;

public class ExampleGuideActivity extends AbsGuideActivity {
    @Override
    public List<SinglePage> buildGuideContent() {
        // prepare the information for our guide
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.drawable.dot1);
//        SingleElement e01 = new SingleElement(200, 200, 400, 400, 0.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e02 = new SingleElement(700, 800, 700, 100, 0.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        page01.mElementsList.add(e01);
//        page01.mElementsList.add(e02);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.drawable.dot2);
//        SingleElement e03 = new SingleElement(400, 400, -100, -100, 1.0f, 0.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e04 = new SingleElement(700, 100, 700, -200, 1.0f, 0.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        page02.mElementsList.add(e03);
//        page02.mElementsList.add(e04);
        guideContent.add(page02);

       // SinglePage page03 = new SinglePage();
      //  page03.mBackground = getResources().getDrawable(R.drawable.dot2);
//        SingleElement e05 = new SingleElement(-100, 2000, 100, 100, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e06 = new SingleElement(100, 2000, 300, 120, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e07 = new SingleElement(200, 2000, 600, 140, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e08 = new SingleElement(300, 2000, 900, 160, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        page03.mElementsList.add(e05);
//        page03.mElementsList.add(e06);
//        page03.mElementsList.add(e07);
//        page03.mElementsList.add(e08);
      //  guideContent.add(page03);

      //  SinglePage page04 = new SinglePage();
       // page04.mBackground = getResources().getDrawable(R.drawable.dot);
//        SingleElement e09 = new SingleElement(100, 100, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e10 = new SingleElement(300, 120, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e11 = new SingleElement(600, 140, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(), 0));
//        SingleElement e12 = new SingleElement(900, 160, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
//                getResources(),0));
//        page04.mElementsList.add(e09);
//        page04.mElementsList.add(e10);
//        page04.mElementsList.add(e11);
//        page04.mElementsList.add(e12);
      // guideContent.add(page04);


        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = new EntryFragment();
        guideContent.add(page05);
        return guideContent;
    }

    @Override
    public Bitmap dotDefault() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default);
    }

    @Override
    public Bitmap dotSelected() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected);
    }

    @Override
    public boolean drawDot() {
        return true;
    }

    public void entryApp() {
        startActivity(new Intent(ExampleGuideActivity.this, Activity_Login.class));
        finish();
    }

    /**
     * You need provide an id to the pager. You could define an id
     * values/ids.xml and use it.
     */
    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }
}