package com.android.pinggubang.Activity.FileLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.pinggubang.Activity.WebView;
import com.android.pinggubang.Bean.DocumentBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.Interface.CommonClick;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.CommonAdapter;
import com.android.pinggubang.Utils.CommonViewHolder;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.View.CBarView;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class FileList extends Activity implements ExChange.CallBackForData, Observer<String>, CommonClick {

    @Bind(R.id.recyc)
    ListView recyc;
    private ExChange ex;
    private Observer me = this;
    private List<DocumentBean.DataBean> list = new ArrayList<>();
    private fileAdapter adapter;
    private String DownLoadUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        ButterKnife.bind(this);
        new CBarView(this, new CBarView.OnClickListener() {
            @Override
            public void onLeftClick() {
                super.onLeftClick();
                finish();
            }

            @Override
            public void onRightClick() {
                super.onRightClick();

            }
        }, null);
        ex = new ExChange(this);
        adapter = new fileAdapter(this, list, R.layout.activity_file_item, this);

        recyc.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ex.getDocumentLists();
    }

    @Override
    public void onMessage(String str, String cmd, int code) {

        Observable.just(str).subscribeOn(AndroidSchedulers.mainThread()).subscribe(me);
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(String s) {


        JSONObject cmd = JSONUtils.StringToJSON(s);
        if (JSONUtils.getString(cmd, "cmd").equals("business.getDocuments")) {
            Gson gson = new Gson();
            Type type = new TypeToken<DocumentBean>() {
            }.getType();
            DocumentBean documentBean = gson.fromJson(s, type);
            list.clear();
            list.addAll(documentBean.getData());
            adapter.notifyDataSetChanged();
        }
        if (JSONUtils.getString(cmd, "cmd").equals("business.loadDocuments")) {

            JSONObject obj = JSONUtils.getJSONPersonBean(cmd, "data");
            String url =JSONUtils.getString(obj, "url");
            Intent i =new Intent(FileList.this, WebView.class);
            i.putExtra("file",url);
            startActivity(i);
        }
    }

    @Override
    public void MyClick(int pos, View view) {
        ex.loadDocuments(pos);
    }

    class fileAdapter extends CommonAdapter<DocumentBean.DataBean> {
        private CommonClick Cclick;

        public fileAdapter(Context context, List<DocumentBean.DataBean> list, int layoutId, CommonClick commonClick) {
            super(context, list, layoutId);
            this.Cclick = commonClick;
        }
        @Override
        public void setViewContent(final CommonViewHolder viewHolder, final DocumentBean.DataBean dataBean) {
            viewHolder.setText(R.id.doc_title, dataBean.getFilename())
                    .setText(R.id.doc_size, dataBean.getSize())
                    .setText(R.id.down_count, dataBean.getDownloads());
            ImageView v = viewHolder.getView(R.id.down_icon);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cclick.MyClick(dataBean.getId(), view);
                }
            });
        }
    }
}
