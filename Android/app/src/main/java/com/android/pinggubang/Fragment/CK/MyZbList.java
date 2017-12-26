package com.android.pinggubang.Fragment.CK;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.pinggubang.Activity.Activity_CK.Activity_ZbInfo;
import com.android.pinggubang.Activity.Activity_CK.CreateKC;
import com.android.pinggubang.Activity.WebView;
import com.android.pinggubang.Adapter.innerAdapter.outsideAdapter;
import com.android.pinggubang.Bean.ZBBean;
import com.android.pinggubang.BroadCast.ExChange;
import com.android.pinggubang.Interface.ParentsButtonLisener;
import com.android.pinggubang.R;
import com.android.pinggubang.Utils.Dialog;
import com.android.pinggubang.Utils.JSONUtils;
import com.android.pinggubang.Utils.Log;
import com.android.pinggubang.View.floatbutton.CustomFAB;
import com.android.pinggubang.base.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyZbList extends BaseFragment implements ExChange.CallBackForData, ParentsButtonLisener<ZBBean> {

    private List<ZBBean> listBean;
    private ExChange ex;
    private int pullFlag = 100;
    private outsideAdapter adapter;
    private int CurrentPage = 1;
    private List<ZBBean> listems;
    private List<List<ZBBean>> mapList;
    private Handler showPage = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_zb_list, container, false);

        listBean = new ArrayList<>();
        mapList = new ArrayList<>();
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ex = new ExChange(this);
        ex.getMyZbList(CurrentPage);
        com.android.pinggubang.Utils.Log.e("这是我都招标列表的 onResume()方法");

    }

    @Override
    public void onPause() {
        super.onPause();
        com.android.pinggubang.Utils.Log.e("这是我都招标列表的 onPause()方法");
    }

    private void initView(View view) {
        listBean = new ArrayList<>();
        adapter = new outsideAdapter(getActivity(), listBean, mapList, this);
        pullListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefresh);
        pullListView.setAdapter(adapter);
        final CustomFAB floatButton = (CustomFAB) view.findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateKC.class);
                getActivity().startActivity(intent);
            }
        });
        pullListView.setOnItemClickListener(new AdaptItemClick());
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                CurrentPage = 1;
                ex.getMyZbList(CurrentPage);
                pullFlag = 0;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullFlag = 1;
                CurrentPage++;
                ex.getMyZbList(CurrentPage);
            }
        });

        firstLoad();

    }

    private class AdaptItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent(getActivity(), Activity_ZbInfo.class);
            int zbId = listBean.get(position - 1).getZbid();
            intent.putExtra("zbid", zbId);
            getActivity().startActivity(intent);
        }
    }

    // FIXME: 2017/3/14 数据处理中心
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String MessageInfo;
            Dialog dialog;
            switch (msg.what) {
                case LOGIN:
                    pullListView.onRefreshComplete();
                    break;
                case 98:
                    String value = msg.getData().getString("getMyZbList");
                    JSONObject data;

                    //上拉刷新时更新页码
                    if (pullFlag == 0) {
                        CurrentPage = 1;
                    }
                    //不是上拉刷新都清空数据，以免重复数据
                    if (pullFlag != 1) {
                        listBean.clear();
                        mapList.clear();
                    }
                    try {
                        data = new JSONObject(value);
                        JSONArray arr = JSONUtils.getJSONArray(data, "data");
                        ZBBean zbBean;
                        JSONObject temp;
                        for (int i = 0; i < arr.length(); i++) {
                            temp = (JSONObject) arr.get(i);
                            listems = new ArrayList<>();
                            JSONArray zbList = (JSONArray) temp.get("tbinfo");
                            for (int c = 0; c < zbList.length(); c++) {
                                JSONObject bjobj = (JSONObject) zbList.get(c);
                                ZBBean zz = new ZBBean(bjobj);
                                listems.add(zz);
                            }
                            mapList.add(listems);
                            zbBean = new ZBBean(temp);
                            listBean.add(zbBean);
                        }
                        pullListView.onRefreshComplete();
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 99:
                    MessageInfo = ex.getMessageInfo(msg.getData().getString("endZb"));
                    dialog = new Dialog(getActivity(), "提示", MessageInfo);
                    ex.getMyZbList(CurrentPage);

                    break;
                case 100:
                    MessageInfo = ex.getMessageInfo(msg.getData().getString("AgainSendEmail_kcfile"));
                    new Dialog(getActivity(), "提示", MessageInfo);
                    break;
                case 101:

                    JSONObject ob = JSONUtils.StringToJSON(_download_zb_file);
                    try {
                        JSONObject file = (JSONObject) ob.get("data");
                        if (file != null) {
                            String ob_value = file.getString("file_url");
                            if (ob_value != null) {
                                Intent zb_file = new Intent(getActivity(), WebView.class);
                                zb_file.putExtra("file", ob_value);
                                startActivity(zb_file);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 102:
                    MessageInfo = ex.getMessageInfo(_download_zb_file);
                    new Dialog(getActivity(), "提示", MessageInfo);


                    break;
            }
        }
    };

    private void inputTitleDialog(final int zbid) {

        final EditText inputServer = new EditText(getActivity());
        inputServer.setFocusable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("输入邮箱").setIcon(
                R.drawable.mail).setView(inputServer).setNegativeButton(
                "", null);
        builder.setPositiveButton("发送",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ex.AgainSendEmail_kcfile(zbid, inputServer.getText().toString());
                    }
                });
        builder.show();
    }

    @Override
    public void leftbtnClick(int vid, int posttion, ZBBean bean) {

        //占用一个接口函数，根据不同的id来分别点击事件
        //查勘附件
        if (R.id.ckfj == vid) {

            ex.download_zb_file(bean.getZbid());
        }
        //发送邮箱
        if (R.id.fsyx == vid) {
            inputTitleDialog(bean.getZbid());

        }
    }


    @Override
    public void rightbtnClick(int vid, final int posttion, ZBBean bean) {


        new Dialog(getActivity(), "提示：", "是否终止招标？", new Dialog.dialogButton() {
            @Override
            public void ok(DialogInterface dialog, int which) {
                ex.endZb(listBean.get(posttion).getZbid());

            }

            @Override
            public void cannot(DialogInterface dialog, int which) {

            }
        });


    }

    private final int LOGIN = 9999;
    private String _download_zb_file = "";

    @Override
    public void onMessage(String str, String cmd, int code) {

        Log.e(str);
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        switch (cmd) {
            case "business.getMyZbList":
                msg.what = 98;
                bundle.putString("getMyZbList", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
                break;
            case "business.endZb":                          //终止招标
                bundle.putString("endZb", str);
                msg.setData(bundle);
                msg.what = 99;
                handler.sendMessage(msg);
                break;
            case "business.AgainSendEmail_kcfile":           //终止招标
                bundle.putString("AgainSendEmail_kcfile", str);
                msg.setData(bundle);
                msg.what = 100;
                handler.sendMessage(msg);
                break;
            case "business.download_zb_file":                //终止招标
                if (code == 0) {
                    _download_zb_file = str;
                    msg.what = 101;
                    handler.sendMessage(msg);
                } else {
                    _download_zb_file = str;
                    msg.what = 102;
                    handler.sendMessage(msg);
                }
                break;
            case "user.Login":
                handler.sendEmptyMessage(LOGIN);
                break;
        }


    }
}
