package com.android.pinggubang.Bean;

import com.android.pinggubang.Utils.JSONUtils;

import org.json.JSONObject;

/**
 * Created by wu on 2017/3/29.
 */

public class CompanyMateBean {

    public CompanyMateBean(JSONObject object) {
        this.xm = JSONUtils.getString(object,"xm");
        this.bm =JSONUtils.getString(object,"bm");
        this.gsmc =JSONUtils.getString(object,"gsmc");
        this.squserid =JSONUtils.getInt(object,"squserid",0);
        this.sqbz =JSONUtils.getInt(object,"sqbz",0);
        this.shbz =JSONUtils.getInt(object,"shbz",0);
        this.adminflag =JSONUtils.getInt(object,"adminflag",0);
        this.qygly =JSONUtils.getInt(object,"qygly",0);
        this.tx_img =JSONUtils.getString(object,"tx_img");
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    private String tx_img;

    public int getSquserid() {
        return squserid;
    }

    public void setSquserid(int squserid) {
        this.squserid = squserid;
    }

    public String getTx_img() {
        return tx_img;
    }

    public void setTx_img(String tx_img) {
        this.tx_img = tx_img;
    }

    public int getAdminflag() {
        return adminflag;
    }

    public void setAdminflag(int adminflag) {
        this.adminflag = adminflag;
    }

    private int squserid;


    public int getSqbz() {
        return sqbz;
    }

    public void setSqbz(int sqbz) {
        this.sqbz = sqbz;
    }

    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    private String gsmc;
    private int sqbz;

    public int getShbz() {
        return shbz;
    }

    public void setShbz(int shbz) {
        this.shbz = shbz;
    }

    public int getQygly() {
        return qygly;
    }

    public void setQygly(int qygly) {
        this.qygly = qygly;
    }

    private int qygly;
    private int shbz;
    private int adminflag;
    private String xm;
    private String bm;

}
