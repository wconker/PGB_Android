package com.android.pinggubang.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.pinggubang.Utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kanghui on 2017/3/8.
 */

public class XJBean implements Parcelable  {

    public XJBean() {

    }

    public XJBean(JSONObject obj) {

        this.setCqz_img(JSONUtils.getJSONArray(obj, "cqz_img"));
        this.setBc(JSONUtils.getDouble(obj, "bc", 0.0));
        this.setMj(JSONUtils.getString(obj, "mj"));
        this.setYdcs(JSONUtils.getInt(obj, "ydcs", 0));
        this.setWylx(JSONUtils.getString(obj, "wylx"));
        this.setNc(JSONUtils.getString(obj, "nc"));
        this.setBjid(JSONUtils.getInt(obj, "bjid", 0));
        this.setZtms(JSONUtils.getString(obj, "ztms"));
        this.setXjbdw(JSONUtils.getString(obj, "xjbdw"));
        this.setSzscmc(JSONUtils.getString(obj, "szscmc"));
        this.setXjnew(JSONUtils.getString(obj, "new"));
        this.setYxbjs(JSONUtils.getInt(obj, "yxbjs", 0));
        this.setNbxj(JSONUtils.getInt(obj, "nbxj", 0));
        this.setXjid(JSONUtils.getInt(obj, "xjid", 0));
        this.setXjuserid(JSONUtils.getInt(obj, "xjuserid", 0));
        this.setXjyxsjtime(JSONUtils.getString(obj, "xjyxsjtime"));
        this.setXjyxsj(JSONUtils.getInt(obj, "xjyxsj", 0));
        this.setXjsj(JSONUtils.getString(obj, "xjsj"));
        this.setXjlb(JSONUtils.getInt(obj, "xjlb", 0));
        this.setXjlb_name(JSONUtils.getString(obj,"xjlb_name"));
        this.setXjfw(JSONUtils.getString(obj, "xjfw"));
        this.setCqz(JSONUtils.getString(obj, "cqz"));
        this.setSzcs(JSONUtils.getString(obj, "szcs"));
        this.setZt(JSONUtils.getInt(obj, "zt", 100));
        this.setBz(JSONUtils.getString(obj, "bz"));
        this.setJsbjtj(JSONUtils.getInt(obj, "jsbjtj", 0));
        try {
            if (!obj.get("nb_list").toString().equals("[]")) {
                setNb_list((JSONArray) (((JSONArray) obj.get("nb_list"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * bc : 0.0425
     * bz :
     * cqz : {4A7D5F2B-B366-B62D-DD28-73484D0DF0EF}
     * fwxm : 1元住宅询价
     * jj : 0.00
     * jsbjtj : 1
     * mj : 100.00
     * nbxj : 0
     * new : new
     * sjyt : null
     * szc : null
     * szscmc : 杭州市
     * tdyt : null
     * xjbdw : 杭州市滨江区亚克中心b座904
     * xjfw : 对外
     * xjid : 306
     * xjlb : 1
     * xjsj : 2017-03-04 16:01
     * xjuserid : 7
     * xjyxsj : 1440
     * xjyxsjtime : 2017-03-05 16:01
     * ydcs : 4
     * zcs : null
     * zt : 2
     * ztms : 已完成
     */

    private Double zj;//总价
    private int bjid;//报价id
    private String zhlx;//账户类型
    private String cnbz; //采纳标志
    private String wylx;//物业类型
    private int yxbjs;
    private String nc; //昵称
    private double bc;//报酬
    private String bz;//标志
    private String cqz;//产权证
    private String fwxm;//状态描述
    private String jj;//加价
    private int jsbjtj;//
    private String xjlb_name;//
    private String mj;//面积
    private JSONArray cqz_img; //附件
    private int nbxj;//内部询价
    private JSONArray nb_list;//内部询价
    private JSONArray xjdx; //询价对象
    private String xjnew;//是否阅读过
    private String sjyt;//实际用途
    private int szc;//所在层
    private String szcs;//所在城市
    private String szscmc;//所在城市名称
    private String tdyt;//土地用途
    private String xjbdw;//询价标的物
    private String xjfw;//询价方式
    private int xjid;//询价id
    private int xjlb;//询价类别
    private String xjsj;//询价时间
    private int xjuserid;//询价人id
    private int xjyxsj;//询价预期时间
    private String xjyxsjtime;//询价预期时间_时间格式
    private int ydcs;//阅读次数
    private int zcs;//总层数
    private int zt;//状态
    private String ztms; //状态描述


    public JSONArray getNb_list() {
        return nb_list;
    }

    public void setNb_list(JSONArray nb_list) {
        this.nb_list = nb_list;
    }

    public JSONArray getCqz_img() {
        return cqz_img;
    }

    public void setCqz_img(JSONArray cqz_img) {
        this.cqz_img = cqz_img;
    }

    public Double getZj() {
        return zj;
    }

    public void setZj(Double zj) {
        this.zj = zj;
    }

    public int getBjid() {
        return bjid;
    }

    public void setBjid(int bjid) {
        this.bjid = bjid;
    }

    public String getCnbz() {
        return cnbz;
    }

    public void setCnbz(String cnbz) {
        this.cnbz = cnbz;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getXjlb_name() {
        return xjlb_name;
    }

    public void setXjlb_name(String xjlb_name) {
        this.xjlb_name = xjlb_name;
    }

    public String getSzcs() {
        return szcs;
    }

    public void setSzcs(String szcs) {
        this.szcs = szcs;
    }

    public double getBc() {
        return bc;
    }

    public void setBc(double bc) {
        this.bc = bc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCqz() {
        return cqz;
    }

    public void setCqz(String cqz) {
        this.cqz = cqz;
    }

    public String getFwxm() {
        return fwxm;
    }

    public void setFwxm(String fwxm) {
        this.fwxm = fwxm;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }

    public int getJsbjtj() {
        return jsbjtj;
    }

    public void setJsbjtj(int jsbjtj) {
        this.jsbjtj = jsbjtj;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public int getNbxj() {
        return nbxj;
    }

    public void setNbxj(int nbxj) {
        this.nbxj = nbxj;
    }

    public String getXjnew() {
        return xjnew;
    }

    public void setXjnew(String xjnew) {
        this.xjnew = xjnew;
    }

    public String getSjyt() {
        return sjyt;
    }

    public void setSjyt(String sjyt) {
        this.sjyt = sjyt;
    }

    public int getSzc() {
        return szc;
    }

    public void setSzc(int szc) {
        this.szc = szc;
    }

    public String getSzscmc() {
        return szscmc;
    }

    public void setSzscmc(String szscmc) {
        this.szscmc = szscmc;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getXjbdw() {
        return xjbdw;
    }

    public void setXjbdw(String xjbdw) {
        this.xjbdw = xjbdw;
    }

    public String getXjfw() {
        return xjfw;
    }

    public void setXjfw(String xjfw) {
        this.xjfw = xjfw;
    }

    public int getXjid() {
        return xjid;
    }

    public void setXjid(int xjid) {
        this.xjid = xjid;
    }

    public int getXjlb() {
        return xjlb;
    }

    public void setXjlb(int xjlb) {
        this.xjlb = xjlb;
    }

    public String getXjsj() {
        return xjsj;
    }

    public void setXjsj(String xjsj) {
        this.xjsj = xjsj;
    }

    public int getXjuserid() {
        return xjuserid;
    }

    public void setXjuserid(int xjuserid) {
        this.xjuserid = xjuserid;
    }

    public int getXjyxsj() {
        return xjyxsj;
    }

    public void setXjyxsj(int xjyxsj) {
        this.xjyxsj = xjyxsj;
    }

    public String getXjyxsjtime() {
        return xjyxsjtime;
    }

    public void setXjyxsjtime(String xjyxsjtime) {
        this.xjyxsjtime = xjyxsjtime;
    }

    public int getYdcs() {
        return ydcs;
    }

    public void setYdcs(int ydcs) {
        this.ydcs = ydcs;
    }

    public int getZcs() {
        return zcs;
    }

    public void setZcs(int zcs) {
        this.zcs = zcs;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getZtms() {
        return ztms;
    }

    public void setZtms(String ztms) {
        this.ztms = ztms;
    }


    public String getWylx() {
        return wylx;
    }

    public void setWylx(String wylx) {
        this.wylx = wylx;
    }

    public int getYxbjs() {
        return yxbjs;
    }

    public void setYxbjs(int yxbjs) {
        this.yxbjs = yxbjs;
    }

    public JSONArray getXjdx() {
        return xjdx;
    }

    public void setXjdx(JSONArray xjdx) {
        this.xjdx = xjdx;
    }

    public String getZhlx() {
        return zhlx;
    }

    public void setZhlx(String zhlx) {
        this.zhlx = zhlx;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.zj);
        dest.writeInt(this.bjid);
        dest.writeString(this.zhlx);
        dest.writeString(this.cnbz);
        dest.writeString(this.wylx);
        dest.writeInt(this.yxbjs);
        dest.writeString(this.nc);
        dest.writeDouble(this.bc);
        dest.writeString(this.bz);
        dest.writeString(this.cqz);
        dest.writeString(this.fwxm);
        dest.writeString(this.jj);
        dest.writeInt(this.jsbjtj);
        dest.writeString(this.xjlb_name);
        dest.writeString(this.mj);
        dest.writeInt(this.nbxj);
        dest.writeString(this.xjnew);
        dest.writeString(this.sjyt);
        dest.writeInt(this.szc);
        dest.writeString(this.szcs);
        dest.writeString(this.szscmc);
        dest.writeString(this.tdyt);
        dest.writeString(this.xjbdw);
        dest.writeString(this.xjfw);
        dest.writeInt(this.xjid);
        dest.writeInt(this.xjlb);
        dest.writeString(this.xjsj);
        dest.writeInt(this.xjuserid);
        dest.writeInt(this.xjyxsj);
        dest.writeString(this.xjyxsjtime);
        dest.writeInt(this.ydcs);
        dest.writeInt(this.zcs);
        dest.writeInt(this.zt);
        dest.writeString(this.ztms);
    }

    protected XJBean(Parcel in) {
        this.zj = (Double) in.readValue(Double.class.getClassLoader());
        this.bjid = in.readInt();
        this.zhlx = in.readString();
        this.cnbz = in.readString();
        this.wylx = in.readString();
        this.yxbjs = in.readInt();
        this.nc = in.readString();
        this.bc = in.readDouble();
        this.bz = in.readString();
        this.cqz = in.readString();
        this.fwxm = in.readString();
        this.jj = in.readString();
        this.jsbjtj = in.readInt();
        this.xjlb_name = in.readString();
        this.mj = in.readString();
        this.nbxj = in.readInt();

        this.xjnew = in.readString();
        this.sjyt = in.readString();
        this.szc = in.readInt();
        this.szcs = in.readString();
        this.szscmc = in.readString();
        this.tdyt = in.readString();
        this.xjbdw = in.readString();
        this.xjfw = in.readString();
        this.xjid = in.readInt();
        this.xjlb = in.readInt();
        this.xjsj = in.readString();
        this.xjuserid = in.readInt();
        this.xjyxsj = in.readInt();
        this.xjyxsjtime = in.readString();
        this.ydcs = in.readInt();
        this.zcs = in.readInt();
        this.zt = in.readInt();
        this.ztms = in.readString();
    }

    public static final Creator<XJBean> CREATOR = new Creator<XJBean>() {
        public XJBean createFromParcel(Parcel source) {
            return new XJBean(source);
        }

        public XJBean[] newArray(int size) {
            return new XJBean[size];
        }
    };
}
