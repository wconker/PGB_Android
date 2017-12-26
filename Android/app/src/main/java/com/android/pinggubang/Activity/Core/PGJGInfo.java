package com.android.pinggubang.Activity.Core;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pinggubang.Bean.CompanyInfoBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class PGJGInfo extends Activity implements ExChange.CallBackForData {


    @Bind(R.id.comp_title)
    TextView compTitle;
    @Bind(R.id.imageList)
    LinearLayout imageList;
    @Bind(R.id.substr)
    TextView substr;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.logo)
    ImageView logo;
    private Observer<String> observer;
    private ExChange exChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgjginfo);
        ButterKnife.bind(this);
        exChange = new ExChange(this);
        setObserver();
    }

    void setObserver() {
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String s) {
                Gson gson = new Gson();
                Type type = new TypeToken<CompanyInfoBean>() {
                }.getType();
                CompanyInfoBean companyInfoBean = gson.fromJson(s, type);
                compTitle.setText(companyInfoBean.getData().getCompany_name());
                phone.setText(companyInfoBean.getData().getTelephone());
                address.setText(companyInfoBean.getData().getAddress());
                substr.setText(companyInfoBean.getData().getBusiness());
                description.setText(companyInfoBean.getData().getDescription());
                intoImage(companyInfoBean);
            }
        };
    }

    void intoImage(CompanyInfoBean d) {
        Glide.with(this).load(d.getData().getLogo()).into(logo);
        List<String> imgList = d.getData().getImage();
        imageList.removeAllViews();
        Log.e("Cpmler",imgList.size()+"");
        for (String s : imgList) {
            ImageView imageView= new ImageView(this);
            LinearLayout.LayoutParams l=  new LinearLayout.LayoutParams(200, 200);
            l.setMargins(10,10,10,10);
            imageView.setLayoutParams(l);  //设置图片宽高
            imageView.setImageResource(R.drawable.user); //图片资源
            Glide.with(this).load(s).into(imageView);
            imageList.addView(imageView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        exChange.getComDetail(102);
    }
    @Override
    public void onMessage(String str, String cmd, int code) {
        Observable.just(str).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}