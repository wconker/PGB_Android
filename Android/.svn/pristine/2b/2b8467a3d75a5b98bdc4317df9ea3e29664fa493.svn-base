package com.android.pgb.Bean;

import com.android.pgb.Utils.JSONUtils;

import org.json.JSONObject;

/**
 * Created by wu on 2017/4/7.
 */

public class QZ_Bean {
    /**
     * bz :
     * fbsj : 2017-04-07 09:32
     * gw :
     * gzdd : 杭州市
     * gzxz :
     * rcid : 160
     * xm : 555
     * ydcs : 0
     */

    private String bz;
    private String fbsj;
    private String gw;
    private String gzdd;
    private String gzxz;
    private int rcid;
    private String xm;
    private int ydcs;

    public QZ_Bean(JSONObject object) {
        this.bz = JSONUtils.getString(object, "bz");
        this.fbsj = JSONUtils.getString(object, "fbsj");
        this.gw = JSONUtils.getString(object, "gw");
        this.gzdd = JSONUtils.getString(object, "gzdd");
        this.gzxz = JSONUtils.getString(object, "gzxz");
        this.rcid = JSONUtils.getInt(object, "rcid", 0);
        this.xm = JSONUtils.getString(object, "xm");
        this.ydcs = JSONUtils.getInt(object, "ydcs", 0);
    }


    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFbsj() {
        return fbsj;
    }

    public void setFbsj(String fbsj) {
        this.fbsj = fbsj;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }

    public String getGzdd() {
        return gzdd;
    }

    public void setGzdd(String gzdd) {
        this.gzdd = gzdd;
    }

    public String getGzxz() {
        return gzxz;
    }

    public void setGzxz(String gzxz) {
        this.gzxz = gzxz;
    }

    public int getRcid() {
        return rcid;
    }

    public void setRcid(int rcid) {
        this.rcid = rcid;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public int getYdcs() {
        return ydcs;
    }

    public void setYdcs(int ydcs) {
        this.ydcs = ydcs;
    }
}
