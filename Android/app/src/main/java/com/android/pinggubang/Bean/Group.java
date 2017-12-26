package com.android.pinggubang.Bean;

import java.io.Serializable;

/**
 * Created by kanghui on 2017/3/13.
 */

public class Group  implements Serializable{

    private int userid;
    private String bm;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    private String xm;

}
