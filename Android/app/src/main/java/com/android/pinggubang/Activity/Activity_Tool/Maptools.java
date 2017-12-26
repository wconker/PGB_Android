package com.android.pinggubang.Activity.Activity_Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pinggubang.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Maptools extends Activity implements OnGetGeoCoderResultListener, BDLocationListener, BaiduMap.OnMapClickListener {
    @Bind(R.id.MapBaidu)
    MapView MapBaidu;
    @Bind(R.id.addressText)
    TextView addressText;
    @Bind(R.id.deside)
    Button deside;
    private GeoCoder geoCoder;
    private BaiduMap map;
    public LocationClient mLocationClient = null;
    private final int MAPCODE = 198;
    private String addressname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maptools);
        ButterKnife.bind(this);
        initMap();
        deside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("addressText", addressText.getText().toString());
                setResult(MAPCODE, data); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                finish();
            }
        });
    }


    void initMap() {
        map = MapBaidu.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        map.setMyLocationEnabled(true);
        map.setOnMapClickListener(this);
        mLocationClient = new LocationClient(this.getApplicationContext());
        //监听定位
        mLocationClient.registerLocationListener(this);
        mLocationClient.start();

        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        // 设置反地理经纬度坐标,请求位置时,需要一个经纬度

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

        addressText.setText(reverseGeoCodeResult.getAddress().toString() + addressname);

    }


    //获取当前用户定位信息
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        LatLng myPos = pianyi(bdLocation.getLongitude(), bdLocation.getLatitude());
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(1000)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100)
                .latitude(myPos.latitude)
                .longitude(myPos.longitude)
                .build();
        // 设置定位数据
        map.setMyLocationData(locData);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.maps);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, false, mCurrentMarker);
        map.setMyLocationConfigeration(config);
        map.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));
        // 当不需要定位图层时关闭定位图层
        map.setMyLocationEnabled(true);
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


    void getAddressName(LatLng latLng, String addressName) {
        ReverseGeoCodeOption option = new ReverseGeoCodeOption().location(new LatLng(latLng.latitude, latLng.longitude));
        geoCoder.reverseGeoCode(option);
        addressname = addressName;
    }

    //点击地图的回掉，用于获取名称
    @Override
    public void onMapClick(LatLng latLng) {
        getAddressName(latLng, "");

    }

    //点击地图的回掉，用于获取名称
    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        getAddressName(new LatLng(mapPoi.getPosition().latitude, mapPoi.getPosition().longitude), mapPoi.getName());
        return true;
    }
}
