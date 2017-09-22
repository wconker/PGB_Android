package com.android.pgb.Bean;

import com.android.pgb.Utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wu on 2017/3/25.
 */

public class Record {

    public Record(JSONObject object) {
        this.sjxmc = JSONUtils.getString(object, "sjxmc");
        this.sjxz = JSONUtils.getString(object, "sjxz");
        this.lx = JSONUtils.getString(object, "lx");
        this.bt = JSONUtils.getString(object, "bt");
        this.tswz = JSONUtils.getString(object, "tswz");
        this.btbz = JSONUtils.getInt(object, "btbz", 0);
        this.dw = JSONUtils.getString(object, "bt");
        this.xx = JSONUtils.getString(object, "xx");
        this.rqlx = JSONUtils.getString(object, "rqlx");
        this.lj = JSONUtils.getString(object, "lj");
        this.pxh = JSONUtils.getInt(object, "pxh", 0);
        this.files =JSONUtils.getJSONArray(object,"files");

    }

    /**
     * sjxmc : 门牌号地址
     * sjxz :
     * lx : 单行输入框
     * bt : 门牌号地址
     * tswz :
     * btbz : 1
     * dw :
     * xx :
     * rqlx :
     * lj :
     * pxh : 30
     * xxlist : []
     */

    private String sjxmc;
    private String sjxz;
    private String lx;
    private String bt;
    private String tswz;
    private int btbz;
    private String dw;
    private String xx;
    private String rqlx;
    private String lj;
    private int pxh;
    private List<?> xxlist;


    private JSONArray files;



    public JSONArray getFiles() {
        return files;
    }

    public void setFiles(JSONArray files) {
        this.files = files;
    }
    public String getSjxmc() {
        return sjxmc;
    }

    public void setSjxmc(String sjxmc) {
        this.sjxmc = sjxmc;
    }

    public String getSjxz() {
        return sjxz;
    }

    public void setSjxz(String sjxz) {
        this.sjxz = sjxz;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getTswz() {
        return tswz;
    }

    public void setTswz(String tswz) {
        this.tswz = tswz;
    }

    public int getBtbz() {
        return btbz;
    }

    public void setBtbz(int btbz) {
        this.btbz = btbz;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

    public String getRqlx() {
        return rqlx;
    }

    public void setRqlx(String rqlx) {
        this.rqlx = rqlx;
    }

    public String getLj() {
        return lj;
    }

    public void setLj(String lj) {
        this.lj = lj;
    }

    public int getPxh() {
        return pxh;
    }

    public void setPxh(int pxh) {
        this.pxh = pxh;
    }

    public List<?> getXxlist() {
        return xxlist;
    }

    public void setXxlist(List<?> xxlist) {
        this.xxlist = xxlist;
    }
}
