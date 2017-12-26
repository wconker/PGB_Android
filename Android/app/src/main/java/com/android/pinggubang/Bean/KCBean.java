package com.android.pinggubang.Bean;

import com.android.pinggubang.Utils.JSONUtils;

import org.json.JSONObject;

/**
 * Created by kanghui on 2017/1/18.
 */

public class KCBean  {


    public KCBean(JSONObject ob) {


        setBz(JSONUtils.getString(ob,"bz"));

        setCkdz(JSONUtils.getString(ob,"ckdz"));

        setCklb(JSONUtils.getString(ob,"cklb"));

        setCklb_name(JSONUtils.getString(ob,"cklb_name"));

        setCkrq(JSONUtils.getString(ob,"ckrq"));

        setId(JSONUtils.getString(ob,"id"));

        setSend_flag(JSONUtils.getInt(ob,"send_flag",0));


    }

    /**
     * bz :
     * ckdz : 。。。
     * cklb : 1
     * cklb_name : 房地产勘查
     * ckrq : 2017-04-19 17:40:57
     * id : 100000022
     * send_flag : 0
     */

    private String bz;
    private String ckdz;
    private String cklb;
    private String cklb_name;
    private String ckrq;
    private String id;
    private int send_flag;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCkdz() {
        return ckdz;
    }

    public void setCkdz(String ckdz) {
        this.ckdz = ckdz;
    }

    public String getCklb() {
        return cklb;
    }

    public void setCklb(String cklb) {
        this.cklb = cklb;
    }

    public String getCklb_name() {
        return cklb_name;
    }

    public void setCklb_name(String cklb_name) {
        this.cklb_name = cklb_name;
    }

    public String getCkrq() {
        return ckrq;
    }

    public void setCkrq(String ckrq) {
        this.ckrq = ckrq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSend_flag() {
        return send_flag;
    }

    public void setSend_flag(int send_flag) {
        this.send_flag = send_flag;
    }
}
