package com.android.pgb.Bean;

import com.android.pgb.Utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kanghui on 2017/3/15.
 */

public class TBBean {


    public TBBean() {
    }

    public TBBean(JSONObject obj) {
        this.tbid = JSONUtils.getInt(obj, "tbid", 0);
        this.zbid = JSONUtils.getInt(obj, "zbid", 0);
        this.zt = JSONUtils.getInt(obj, "zt", 0);
        this.tbuserid = JSONUtils.getInt(obj, "tbuserid", 0);
        this.tbsj = JSONUtils.getString(obj, "tbsj");
        this.zbbj = JSONUtils.getDouble(obj, "zbbj", 0.0);
        this.tbbj = JSONUtils.getDouble(obj, "tbbj", 0.0);
        this.bdw = JSONUtils.getString(obj, "bdw");
        this.tbsm = JSONUtils.getString(obj, "tbsm");
        this.bc = JSONUtils.getDouble(obj, "bc", 0.0);

        this.ztms = JSONUtils.getString(obj, "ztms");
        this.tbj = JSONUtils.getString(obj, "tbj");
        this.xxsm = JSONUtils.getString(obj, "xxsm");
        this.fjbs = JSONUtils.getString(obj, "fjbs");
        this.zbbz = JSONUtils.getInt(obj, "zbbz", 0);
        this.fj_img = JSONUtils.getJSONArray(obj, "fj_img");

    }



    public int getTbid() {
        return tbid;
    }

    public void setTbid(int tbid) {
        this.tbid = tbid;
    }

    public int getZbid() {
        return zbid;
    }

    public void setZbid(int zbid) {
        this.zbid = zbid;
    }

    public int getTbuserid() {
        return tbuserid;
    }

    public void setTbuserid(int tbuserid) {
        this.tbuserid = tbuserid;
    }

    public String getTbsj() {
        return tbsj;
    }

    public void setTbsj(String tbsj) {
        this.tbsj = tbsj;
    }

    public String getTbsm() {
        return tbsm;
    }

    public void setTbsm(String tbsm) {
        this.tbsm = tbsm;
    }

    public String getTbj() {
        return tbj;
    }

    public void setTbj(String tbj) {
        this.tbj = tbj;
    }

    public String getFjbs() {
        return fjbs;
    }

    public void setFjbs(String fjbs) {
        this.fjbs = fjbs;
    }

    public int getZbbz() {
        return zbbz;
    }

    public void setZbbz(int zbbz) {
        this.zbbz = zbbz;
    }

    public Double getTbbj() {
        return tbbj;
    }

    public void setTbbj(Double tbbj) {
        this.tbbj = tbbj;
    }

    public String getBdw() {
        return bdw;
    }

    public void setBdw(String bdw) {
        this.bdw = bdw;
    }

    public Double getBc() {
        return bc;
    }

    public void setBc(Double bc) {
        this.bc = bc;
    }

    public Double getZbbj() {
        return zbbj;
    }

    public void setZbbj(Double zbbj) {
        this.zbbj = zbbj;
    }

    public String getZtms() {
        return ztms;
    }

    public void setZtms(String ztms) {
        this.ztms = ztms;
    }

    public String getXxsm() {
        return xxsm;
    }

    public void setXxsm(String xxsm) {
        this.xxsm = xxsm;
    }
    public JSONObject getUser() {
        return user;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }
    public JSONArray getFj_img() {
        return fj_img;
    }

    public void setFj_img(JSONArray fj_img) {
        this.fj_img = fj_img;
    }


    private int tbid;
    private int zbid;

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    private int zt;

    private String bdw;
    private Double bc;
    private Double tbbj;
    private JSONObject user;
    private String xxsm;
    private String ztms;
    private Double zbbj;
    private int tbuserid;
    private String tbsj;
    private String tbsm;
    private String tbj;
    private String fjbs;
    private int zbbz;
    private JSONArray fj_img;


}
