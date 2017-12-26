package com.android.pinggubang.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.pinggubang.Utils.JSONUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghui on 2017/1/9.
 */

public class EnquiryBean implements Parcelable {


    private String e_address;
    private String e_date;
    private String e_readtime;
    private String e_remainingtime;
    private String xjid;
    private String xjuserid;
    private String xjbdw;
    private String szcs;
    private String szscmc;
    private String xjyxsj;
    private int jsbjtj;
    private String bc;
    private String bz;
    private String zt;
    private String xjyxsjtime;
    private int zcs;
    private int szc;
    private String sjyt;
    private String cqz;
    private String mj;
    private String tdyt;
    private String xjlb;
    private String wylx;
    private Float rjl;
    private String sqylx;
    private String sqyz;

    private List<Map<String, Object>> listItem;

    public String getXjbdw() {
        return xjbdw;
    }

    public void setXjbdw(String xjbdw) {
        this.xjbdw = xjbdw;
    }

    public String getSzcs() {
        return szcs;
    }

    public void setSzcs(String szcs) {
        this.szcs = szcs;
    }

    public String getSzscmc() {
        return szscmc;
    }

    public void setSzscmc(String szscmc) {
        this.szscmc = szscmc;
    }

    public int getJsbjtj() {
        return jsbjtj;
    }

    public void setJsbjtj(int jsbjtj) {
        this.jsbjtj = jsbjtj;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public int getSzc() {
        return szc;
    }

    public void setSzc(int szc) {
        this.szc = szc;
    }

    public String getCqz() {
        return cqz;
    }

    public void setCqz(String cqz) {
        this.cqz = cqz;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getSjyt() {
        return sjyt;
    }

    public void setSjyt(String sjyt) {
        this.sjyt = sjyt;
    }

    public int getZcs() {
        return zcs;
    }

    public void setZcs(int zcs) {
        this.zcs = zcs;
    }

    public String getXjyxsjtime() {
        return xjyxsjtime;
    }

    public void setXjyxsjtime(String xjyxsjtime) {
        this.xjyxsjtime = xjyxsjtime;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getXjyxsj() {
        return xjyxsj;
    }

    public void setXjyxsj(String xjyxsj) {
        this.xjyxsj = xjyxsj;
    }


    public EnquiryBean() {
    }


    public EnquiryBean(JSONObject json) {


        setBz(JSONUtils.getString(json, "bz"));


        setZt(JSONUtils.getString(json, "zt"));
        setXjyxsj(JSONUtils.getString(json, "xjyxsj"));
        setXjlb(JSONUtils.getString(json, "xjlb"));
        if (!JSONUtils.getString(json, "jsbjtj").equals(""))
            setJsbjtj(Integer.parseInt(JSONUtils.getString(json, "jsbjtj")));
        setMj(JSONUtils.getString(json, "mj"));
        setXjbdw(JSONUtils.getString(json, "xjbdw"));
        setSqylx(JSONUtils.getString(json, "sqylx"));
        setTdyt(JSONUtils.getString(json, "tdyt"));
        if (!JSONUtils.getString(json, "szc").equals("") && !JSONUtils.getString(json, "szc").equals("null"))
            setSzc(Integer.parseInt(JSONUtils.getString(json, "szc")));
        if (!JSONUtils.getString(json, "zcs").equals("") && !JSONUtils.getString(json, "zcs").equals("null"))
            setZcs(Integer.parseInt(JSONUtils.getString(json, "zcs")));
        if (!JSONUtils.getString(json, "szcs").equals(""))
            setSzcs(JSONUtils.getString(json, "szcs"));


    }


    protected EnquiryBean(Parcel in) {

        e_address = in.readString();
        e_date = in.readString();
        e_readtime = in.readString();
        e_remainingtime = in.readString();
        xjid = in.readString();
        xjuserid = in.readString();
        xjbdw = in.readString();
        szcs = in.readString();
        szscmc = in.readString();
        xjyxsj = in.readString();
        jsbjtj = in.readInt();
        bc = in.readString();
        bz = in.readString();
        zt = in.readString();
        xjyxsjtime = in.readString();
        zcs = in.readInt();
        szc = in.readInt();
        sjyt = in.readString();
        cqz = in.readString();
        mj = in.readString();
        tdyt = in.readString();
        xjlb = in.readString();
        wylx = in.readString();
        rjl = in.readFloat();
        sqylx = in.readString();
        sqyz = in.readString();

    }

    public static final Creator<EnquiryBean> CREATOR = new Creator<EnquiryBean>() {
        @Override
        public EnquiryBean createFromParcel(Parcel in) {
            return new EnquiryBean(in);
        }

        @Override
        public EnquiryBean[] newArray(int size) {
            return new EnquiryBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeString(e_address);
        dest.writeString(e_date);
        dest.writeString(e_readtime);
        dest.writeString(e_remainingtime);
        dest.writeString(xjid);
        dest.writeString(xjuserid);
        dest.writeString(xjbdw);
        dest.writeString(szcs);
        dest.writeString(szscmc);
        dest.writeString(xjyxsj);
        dest.writeInt(jsbjtj);
        dest.writeString(bc);
        dest.writeString(bz);
        dest.writeString(zt);
        dest.writeString(xjyxsjtime);
        dest.writeInt(zcs);
        dest.writeInt(szc);
        dest.writeString(sjyt);
        dest.writeString(cqz);
        dest.writeString(mj);
        dest.writeString(tdyt);
        dest.writeString(xjlb);
    }


    public String getE_address() {
        return e_address;
    }

    public void setE_address(String E_address) {
        e_address = E_address;
    }

    public String getE_remainingtime() {
        return e_remainingtime;
    }

    public void setE_remainingtime(String e_remainingtime) {
        this.e_remainingtime = e_remainingtime;
    }

    public String getE_readtime() {
        return e_readtime;
    }

    public void setE_readtime(String e_readtime) {
        this.e_readtime = e_readtime;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    public String getXjid() {
        return xjid;
    }

    public void setXjid(String xjid) {
        this.xjid = xjid;
    }

    public String getXjuserid() {
        return xjuserid;
    }

    public void setXjuserid(String xjuserid) {
        this.xjuserid = xjuserid;
    }

    public String getXjlb() {
        return xjlb;
    }

    public void setXjlb(String xjlb) {
        this.xjlb = xjlb;
    }

    public List<Map<String, Object>> getListItem() {
        return listItem;
    }

    public void setListItem(List<Map<String, Object>> listItem) {
        this.listItem = listItem;
    }

    public String getWylx() {
        return wylx;
    }

    public void setWylx(String wylx) {
        this.wylx = wylx;
    }

    public Float getRjl() {
        return rjl;
    }

    public void setRjl(Float rjl) {
        this.rjl = rjl;
    }

    public String getSqylx() {
        return sqylx;
    }

    public void setSqylx(String sqylx) {
        this.sqylx = sqylx;
    }

    public String getSqyz() {
        return sqyz;
    }

    public void setSqyz(String sqyz) {
        this.sqyz = sqyz;
    }
}
