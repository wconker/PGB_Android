package com.android.pinggubang.Bean;

import com.android.pinggubang.Utils.JSONUtils;

import org.json.JSONObject;

/**
 * Created by kanghui on 2017/3/21.
 */

public class City {
    public City(JSONObject obj) {

        this.dqdm = JSONUtils.getInt(obj,"dqdm",0);;
        this.dqmc = JSONUtils.getString(obj,"dqmc");
    }

    private int cid;
    private int dqdm;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getDqdm() {
        return dqdm;
    }

    public void setDqdm(int dqdm) {
        this.dqdm = dqdm;
    }

    public String getDqmc() {
        return dqmc;
    }

    public void setDqmc(String dqmc) {
        this.dqmc = dqmc;
    }

    private String dqmc;

}
