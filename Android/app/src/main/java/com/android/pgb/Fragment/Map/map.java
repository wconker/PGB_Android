package com.android.pgb.Fragment.Map;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pgb.Bean.WZ;
import com.android.pgb.BroadCast.ExChange;
import com.android.pgb.R;
import com.android.pgb.Utils.JSONUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class map extends Fragment implements ExChange.CallBackForData {

    private MapView baidu_map;
    private BaiduMap map;
    private TextView describe;
    private View BDMapView;
    private LatLng myPos;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new BDLocationListener() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceiveLocation(BDLocation location) {

            android.util.Log.e("map", "我现在的位置事onReceiveLocation");

            //提交我的位置
            ex.UpdateSite(location.getLongitude(), location.getLatitude());
            myPos = pianyi(location.getLongitude(), location.getLatitude());
            //1.创建圆圈
            CircleOptions circleOptions = new CircleOptions();
            //2.给自己设置数据
            circleOptions.center(pianyi(location.getLongitude(), location.getLatitude())) //圆心
                    .radius(1000)//半径 单位米
                    .fillColor(0x6000ff00)//填充色
                    .stroke(new Stroke(2, 0x6000ff00));//边框宽度和颜色

            //3.把覆盖物添加到地图中
            map.addOverlay(circleOptions);

            LatLng point = pianyi(location.getLongitude(), location.getLatitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.maps);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
//            map.addOverlay(option);

            for (int pos = 0; pos < wzList.size(); pos++) {
                // 构造定位数据 //定义Maker坐标点
                View markView = LayoutInflater.from(getActivity()).inflate(R.layout.mapmark_view, null);
                LatLng qt = pianyi(Double.parseDouble(wzList.get(pos).getJd()), Double.parseDouble(wzList.get(pos).getWd()));
                //构建Marker图标
                View vi = LayoutInflater.from(getActivity()).inflate(R.layout.mapmark_view, null);
                TextView textView = (TextView) vi.findViewById(R.id.markText);
                ImageView imageView = (ImageView) vi.findViewById(R.id.markImage);

                if (!wzList.get(pos).getXm().isEmpty()) {
                    imageView.setImageDrawable(getActivity().getDrawable(R.drawable.person2));
                    textView.setText(wzList.get(pos).getXm());

                } else
                    textView.setText("未知");
                BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromView(vi); //內容不能為空，
                if (bitmap2 == null)
                    wzList.get(pos).getXm();
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option2 = new MarkerOptions()
                        .position(qt)
                        .animateType(MarkerOptions.MarkerAnimateType.grow)
                        .icon(bitmap2);
                //在地图上添加Marker，并显示
                map.addOverlay(option2);
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(1000)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)
                    .latitude(myPos.latitude)
                    .longitude(myPos.longitude).build();
            // 设置定位数据
            map.setMyLocationData(locData);
            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.maps);
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
            map.setMyLocationConfigeration(config);
            map.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));
            // 当不需要定位图层时关闭定位图层
            map.setMyLocationEnabled(false);
            showTitle();

        }
    };

    void showTitle() {
        Near = 0; //附近的人
        //根据返回的经纬度来定位附近的评估师
        for (int i = 0; i < wzList.size(); i++) {
            if (GetDistance(Double.parseDouble(wzList.get(i).getJd()),
                    Double.parseDouble(wzList.get(i).getWd()),
                    myPos.longitude,
                    myPos.latitude) < 1) {
                Near++;
            }
        }
        String html = "一公里范围内共有<font color='#ffe954'  size='33px'><big><big>" + Near + "</big></big></font>位评估师";
        describe.setText(Html.fromHtml(html));
        mLocationClient.stop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ex = new ExChange(this);
        this.BDMapView = view;
        baidu_map = (MapView) BDMapView.findViewById(R.id.bmapView);
        describe = (TextView) BDMapView.findViewById(R.id.describe);
        return view;
    }


    private void initBaiDu(View BDMapView) {
        map = baidu_map.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        map.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());

        mLocationClient.registerLocationListener(myListener);

        mLocationClient.start();


        ex.getWzList();
        ex.WebSocket();
    }


    private double MybdLat;
    private double MybdbdLon;

    @Override
    public void onResume() {
        super.onResume();
        android.util.Log.e("Map", "onResume");
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        } else {
            initBaiDu(BDMapView);
        }


    }

    private LatLng pianyi(double lon, double lat) {
        double x = lon;
        double y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double temp = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double bdLon = z * Math.cos(temp) + 0.0065;
        double bdLat = z * Math.sin(temp) + 0.006;
        LatLng newcenpt = new LatLng(bdLat, bdLon);
        return newcenpt;
    }

    private static double rad(double d) {

        return d * Math.PI / 180.0;

    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private ExChange ex;
    private List<WZ> wzList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WZLIST) {
                String value = _str;
                JSONObject obj;
                try {
                    wzList = new ArrayList<>();
                    obj = new JSONObject(value);
                    JSONObject jsonObject = obj.getJSONObject("data");
                    JSONArray arr = JSONUtils.getJSONArray(jsonObject, "name");
                    if (arr != null) {
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject data = (JSONObject) arr.get(i);
                            WZ wz = new WZ();
                            wz.setJd((data.getString("jd")));
                            wz.setWd((data.getString("wd")));
                            wz.setXm((data.getString("xm")));
                            wz.setSjhm((data.getString("sjhm")));
                            wzList.add(wz);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private int Near = 0;
    private final int WZLIST = 233;
    private String _str = "";

    @Override
    public void onMessage(String str, String cmd, int code) {
        android.util.Log.e("C*******", "Map" + str);
        if (cmd.equals("admin.getWzList")) {
            _str = str;
            handler.sendEmptyMessage(WZLIST);
        }
    }

    private static final int BAIDU_READ_PHONE_STATE = 100;

    //检查权限，6.0以上需要动态检查权限
    public void showContacts() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, //位置
                            Manifest.permission.ACCESS_FINE_LOCATION,  //为止
                            Manifest.permission.READ_PHONE_STATE}, //电话
                    BAIDU_READ_PHONE_STATE);
        } else {
            initBaiDu(BDMapView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initBaiDu(BDMapView);
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getActivity(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
