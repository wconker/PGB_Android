package com.android.pgb.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Activity.Activity_Job.Activity_Home;
import com.android.pgb.Activity.Activity_Job.JobList;
import com.android.pgb.Activity.Activity_MainCenter;
import com.android.pgb.Activity.Activity_More;
import com.android.pgb.Activity.Activity_Tool.Activity_KCGJ;
import com.android.pgb.Activity.Activity_Tool.Calculate;
import com.android.pgb.Activity.Activity_Tool.Evaluate;
import com.android.pgb.Activity.Activity_Tool.ProTool;
import com.android.pgb.Activity.Activity_XJ.XJ.XJList;
import com.android.pgb.Activity.Core.SelectCity;
import com.android.pgb.Activity.FileLibrary.FileList;
import com.android.pgb.Adapter.GridAdapter;
import com.android.pgb.Adapter.ListAdapter;
import com.android.pgb.Bean.ADInfo;

import com.android.pgb.Bean.NewBean;
import com.android.pgb.Bean.NewInfo;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.News.NewMain;
import com.android.pgb.R;
import com.android.pgb.Utils.CycleViewPager;
import com.android.pgb.Utils.Dialog;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;
import com.android.pgb.Utils.SharedPreferences.SharedPreferencesUtils;
import com.android.pgb.Utils.ViewFactory;
import com.android.pgb.View.CBarView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class index extends Fragment implements ExChange.CallBackForData {
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private GridView gridView;
    private ExChange ex;
    private String[] imageUrls = {
            "https://www.yiqiyun.org/filepgb/ad/ad01.jpg",
            "https://www.yiqiyun.org/filepgb/ad/ad02.jpg",
            "https://www.yiqiyun.org/filepgb/ad/ad03.jpg"
    };
    private Integer[] imgDrable = {
            R.drawable.flash2011_01_17,
            R.drawable.flash2017_02_17,
            R.drawable.flash2017_03_17,
    };
    private String LocalTime = "";
    private TextView rw_qdsj;
    private ListView listView;
    private List<Map<String, Object>> data_list;
    // 图片封装为一个数组

    private String[] iconName = {"询价业务", "勘察业务", "查勘工具", "求职招聘", "专业论坛", "文库", "在线教育", "专业工具", "更多"};
    private TextView more;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            LocalTime = location.getTime();
            rw_qdsj.setText(location.getTime());

        }
    };
    private CBarView bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ex = new ExChange(this);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        bar = new CBarView(getActivity(),
                new CBarView.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        super.onLeftClick();
                        Intent postion = new Intent(getActivity(), SelectCity.class);
                        startActivityForResult(postion, 32);
                    }
                }, view);

        ex.getNewsList(5);
        if (bar.getLeftBtn().equals("我的位置")) {
            ex.getYwCity();
        }
        //配置适配器
        gridView = (GridView) view.findViewById(R.id.gview);
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(getActivity().WINDOW_SERVICE);                                     //获取屏幕对象
        int width = wm.getDefaultDisplay().getWidth();                                                //计算屏幕宽度
        gridView.setColumnWidth(width / 3);                                                          //gridview分成三列
        gridView.setAdapter(new GridAdapter(getActivity(), iconName, null));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ((Activity_MainCenter) getActivity()).mTabLayout.setCurrentTab(1);
                }
                if (i == 1) {
                    ((Activity_MainCenter) getActivity()).mTabLayout.setCurrentTab(3);
                }
                if (i == 2) {
                    Intent intent = new Intent(getActivity(), Activity_KCGJ.class);
                    getActivity().startActivity(intent);

                }
                if (i == 3) {
                    Intent intent2 = new Intent(getActivity(), JobList.class);
                    getActivity().startActivity(intent2);

                }
                if (i == 4) {
                    Snackbar.make(more,"功能开发中，敬请期待！",Snackbar.LENGTH_SHORT).show();
                }

                if (i == 5) {
                    Intent intent55 = new Intent(getActivity(), FileList.class);
                    getActivity().startActivity(intent55);

                }
                if (i == 6) {


                }
                if (i == 7) {
                    Intent intent7 = new Intent(getActivity(), ProTool.class);
                    getActivity().startActivity(intent7);

                }
                if (i == 8) {
                    Snackbar.make(more,"功能开发中,敬请期待！",Snackbar.LENGTH_SHORT).show();

                }
            }
        });
        listView = (ListView) view.findViewById(R.id.listview);
        more = (TextView) view.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), NewMain.class);
                startActivity(i);
            }
        });
        newBeanList = new ArrayList<>();
        lvAdapter = new ListAdapter(getActivity(), newBeanList);
        listView.setAdapter(lvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getActivity(), com.android.pgb.News.NewInfo.class);
                //it.putExtra("newId", newBeanList.get(i).getId());
                startActivity(it);
            }
        });
        configImageLoader();
        initialize(view);
        return view;
    }

    private ListAdapter lvAdapter;
    private List<NewBean> newBeanList;
    private String dqmc = "";
    private int dqdm = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;

        dqmc = data.getStringExtra("dqmc");
        dqdm = data.getIntExtra("dqdm", 0);
        if (!dqmc.equals("")) {
            bar.setLeftText(dqmc);
            ex.setYwCity(dqdm);

        }
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < iconName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @SuppressLint("NewApi")
    private void initialize(View view) {
        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl(), 0));
//        for (int i = 0; i < infos.size(); i++) {
//            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl(),R.drawable.flash_pic00));
//        }
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl(), R.drawable.flash2017_03_17));
        views.add(ViewFactory.getImageView(getActivity(), infos.get(2).getUrl(), R.drawable.flash2011_01_17));
        views.add(ViewFactory.getImageView(getActivity(), infos.get(1).getUrl(), R.drawable.flash2017_02_17));
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl(), R.drawable.flash2017_03_17));
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(5000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
//                Toast.makeText(getActivity(),
//                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
//                        .show();
            }
        }

    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GETYWCITY) {
                try {
                    JSONObject data = new JSONObject(_str);
                    JSONObject arr = (JSONObject) JSONUtils.getJSONArray(data, "data").get(0);
                    SharedPreferences.Editor sp = SharedPreferencesUtils.getEditor(getActivity());

                    sp.putInt("dqdm", arr.getInt("dqdm"));
                    sp.putString("dqmc", arr.getString("dqmc"));
                    sp.commit();
                    bar.setLeftText(arr.getString("dqmc"));
                } catch (JSONException e) {

                    new Dialog(getActivity(), "提示：", "请在我的位置中选择您所在的城市！", new Dialog.dialogButton() {
                        @Override
                        public void ok(DialogInterface dialog, int which) {
//                                Intent postion = new Intent(getActivity(), SelectCity.class);
//                                startActivityForResult(postion, 32);
                        }

                        @Override
                        public void cannot(DialogInterface dialog, int which) {


                        }
                    });

                    e.printStackTrace();
                }


            }
            if (msg.what == NEW) {
                try {
                    JSONObject data = new JSONObject(_new);
                    JSONArray arr = JSONUtils.getJSONArray(data, "data");
                    if (arr != null) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject newObject = (JSONObject) arr.get(i);
                          //  NewBean newBean = new NewBean(newObject);
                            //newBeanList.add(newBean);
                        }
                        lvAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };
    private String _str = "", _new = "";

    private final int NEW = 400;
    private final int GETYWCITY = 100;

    @Override
    public void onMessage(String str, String cmd, int code) {

        if (cmd.equals("business.getYwCity")) {
            _str = str;
            handler.sendEmptyMessage(GETYWCITY);
        }
        if (cmd.equals("conn.getNewsList")) {
            _new = str;
            handler.sendEmptyMessage(NEW);
        }

    }
}
