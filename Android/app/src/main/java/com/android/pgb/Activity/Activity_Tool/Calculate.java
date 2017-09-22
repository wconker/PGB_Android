package com.android.pgb.Activity.Activity_Tool;


import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.View.CircleProgressView;
import com.android.pgb.View.CustomBar;
import com.android.pgb.base.BaseActivity;

import org.json.JSONObject;


import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.ObserverSubscriber;


@SuppressWarnings("RestrictedApi")
public class Calculate extends BaseActivity {


    @Bind(R.id.nestSV)
    NestedScrollView nestSV;
    @Bind(R.id.bar)
    CustomBar bar;
    @Bind(R.id.totalPrice)
    EditText totalPrice;
    @Bind(R.id.housecertificate)
    LinearLayout housecertificate;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.singlePrice)
    EditText singlePrice;
    @Bind(R.id.Percent)
    EditText Percent;
    @Bind(R.id.OriginalPrice)
    EditText OriginalPrice;
    @Bind(R.id.rg_personal)
    RadioButton rgPersonal;
    @Bind(R.id.rg_company)
    RadioButton rgCompany;
    @Bind(R.id.rg_less2)
    RadioButton rgLess2;
    @Bind(R.id.rg_more2)
    RadioButton rgMore2;
    @Bind(R.id.rb_unUsual)
    RadioButton rbUnUsual;
    @Bind(R.id.rb_Usual)
    RadioButton rbUsual;
    @Bind(R.id.rb_specific)
    RadioButton rbSpecific;
    @Bind(R.id.getResult)
    Button getResult;
    @Bind(R.id.deal_total)
    TextView dealTotal;
    @Bind(R.id.deal_signal)
    TextView dealSignal;
    @Bind(R.id.legalfare)
    TextView legalfare;
    @Bind(R.id.auctionfee)
    TextView auctionfee;
    @Bind(R.id.evalfare)
    TextView evalfare;
    @Bind(R.id.otherfare)
    TextView otherfare;
    @Bind(R.id.incometax)
    TextView incometax;
    @Bind(R.id.stampfare)
    TextView stampfare;
    @Bind(R.id.valueadded)
    TextView valueadded;
    @Bind(R.id.sum)
    TextView sum;
    @Bind(R.id.saleType)
    RadioGroup saleType;
    @Bind(R.id.houseYear)
    RadioGroup houseYear;
    @Bind(R.id.rg_houseType)
    RadioGroup rgHouseType;
    @Bind(R.id.proLoading)
    CircleProgressView proLoading;
    private String v_saleType = "", v_houseType = "", v_houseYear = "";
    private HashMap<String, String> map;
    private Observer subscriber;

    @Override
    public void bindButterKnife() {
        super.bindButterKnife();
        ButterKnife.bind(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        final String va2 = getIntent().getStringExtra("evaluatetotal");
        final String va3 = getIntent().getStringExtra("evaluatesignal");
        singlePrice.setText(va3);
        totalPrice.setText(va2);


        setOb();
        saleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.rg_personal:
                        v_saleType = "1";
                        break;

                    case R.id.rg_company:
                        v_saleType = "2";
                        break;
                }
            }
        });

        houseYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.rg_more2:
                        v_houseYear = "2";
                        break;

                    case R.id.rg_less2:
                        v_houseYear = "1";
                        break;
                }
            }
        });

        rgHouseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.rb_unUsual:
                        v_saleType = "1";
                        break;

                    case R.id.rb_Usual:
                        v_saleType = "2";
                        break;

                    case R.id.rb_specific:
                        v_saleType = "3";
                        break;
                }
            }
        });

        getResult.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                map = new HashMap<String, String>();
                map.put("pgdj", singlePrice.getText().toString().trim());
                map.put("pgzj", totalPrice.getText().toString().trim());
                map.put("yj", singlePrice.getText().toString().trim());
                map.put("zzlx", v_houseType);
                map.put("cslx", v_saleType);
                map.put("zznx", v_houseYear);
                map.put("bxl", Percent.getText().toString().trim());
                ex.computeTheDisposalOfTaxAndFee(map);
                showLoading();
            }
        });


    }

    private void setOb() {
        subscriber = new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                DataCenter(o.toString());
            }
        };
    }


    void DataCenter(String Data) {
        hidLoading();
        JSONObject json = JSONUtils.StringToJSON(Data);
        int code = JSONUtils.getInt(json, "code", 111);
        JSONObject jsonData = JSONUtils.StringToJSON(JSONUtils.getString(json, "data"));
        switch (code) {
            case 0:
                dealTotal.setText(JSONUtils.getString(jsonData, "czzj") + "元");
                dealSignal.setText(JSONUtils.getString(jsonData, "czdj") + "元");
                valueadded.setText(JSONUtils.getString(jsonData, "tdzzs") + "元");
                incometax.setText(JSONUtils.getString(jsonData, "grsds") + "元");
                stampfare.setText(JSONUtils.getString(jsonData, "tdzzs") + "元");
                otherfare.setText(JSONUtils.getString(jsonData, "yys") + "元");
                evalfare.setText(JSONUtils.getString(jsonData, "pgf") + "元");
                auctionfee.setText(JSONUtils.getString(jsonData, "pmf") + "元");
                legalfare.setText(JSONUtils.getString(jsonData, "tdzzs") + "元");
                sum.setText(JSONUtils.getString(jsonData, "hj") + "元");
                nestSV.fullScroll(ScrollView.FOCUS_DOWN);
                break;
            default:
                Log.e("Conker", code + "====default");
                Toast.makeText(Calculate.this, JSONUtils.getString(jsonData, "message"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected int GetLayout() {
        return R.layout.activity_activitytool_calculate;
    }

    @Override
    public void showLoading() {
        super.showLoading();
        proLoading.setVisibility(View.VISIBLE);
        proLoading.spin();
    }

    @Override
    public void hidLoading() {
        super.hidLoading();
        proLoading.setVisibility(View.GONE);

    }

    @Override
    public void onMessage(String str, String cmd, int code) {


        if (subscriber == null) {
            Log.e("C---", "subscriber 为 null");
        } else {
            Log.e("C---", "subscriber 不为 null" + subscriber);
        }
        Observable.just(str)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
