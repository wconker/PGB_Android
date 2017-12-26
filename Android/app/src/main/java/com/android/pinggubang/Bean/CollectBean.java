package com.android.pinggubang.Bean;

/**
 * Created by conker on 2017/1/26.
 */

public class CollectBean {


    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    String cityname;

    public CollectBean(String cityname, String citycode) {
        this.cityname = cityname;
        this.citycode = citycode;
    }

    String citycode;

}
