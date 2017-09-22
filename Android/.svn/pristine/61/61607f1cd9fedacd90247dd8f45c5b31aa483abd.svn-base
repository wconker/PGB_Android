package com.android.pgb.Bean;

import com.android.pgb.Utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kanghui on 2017/1/18.
 */

public class ZBBean {


    private JSONArray zbdx;//招标对象
    private int zbid; //招标id
    private int zbuserid; //招标人id
    private int zblb; //招标类别
    private String zblbmc; //招标类别名称
    private String zbsj;//招标时间
    private int kcbmb;//招标类别


    private String kcbmbmc;//招标类别
    private String bdw;//标的物
    private String szcs;//所在城市
    private String szcsmc;//所在城市名称
    private String lxr;//联系人
    private String lxdh;//联系人号码
    private String dkyh;
    private String pgjg;//评估机构
    private String cqz;//产权证


    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    private String isnew;
    public String getQdsj() {
        return qdsj;
    }

    public void setQdsj(String qdsj) {
        this.qdsj = qdsj;
    }

    private String qdsj;//签到时间
    private String fjbs; //附件标识
    private String xxsm;//招标类别
    private String zbyxsj;//招标预期时间
    private String zbyxsjtime;//招标预期时间_时间格式
    private double bc;//报酬
    private int zt;//状态
    private String nc;
    private String zblb_name;
    private String bgjzsj;
    private int tbid;
    private Double tbj;
    private String tbsj;//投标时间
    private String nbzb;
    private String tbsm; //说明
    private int tbuserid;


    private String zhlx;//账户类型


    private JSONArray fj;
    private int zbbz; //中标标志
    private String ztms;   //状态描述
    private String zbfw;//招标范围
    private String ydcs;//招标类别

    public ZBBean() {

    }

    public ZBBean(JSONObject obj) {
        this.zbid = JSONUtils.getInt(obj, "zbid", 0);
        this.zbuserid = JSONUtils.getInt(obj, "zbuserid", 0);
        this.zblb = JSONUtils.getInt(obj, "zblb", 0);
        this.zbsj = JSONUtils.getString(obj, "zbsj");
        this.kcbmb = JSONUtils.getInt(obj, "kcbmb", 0);
        this.kcbmbmc = JSONUtils.getString(obj, "kcbmbmc");
        this.bdw = JSONUtils.getString(obj, "bdw");
        this.qdsj = JSONUtils.getString(obj, "qdsj");
        this.szcs = JSONUtils.getString(obj, "szcs");
        this.szcsmc = JSONUtils.getString(obj, "szcsmc");
        this.lxr = JSONUtils.getString(obj, "lxr");
        this.lxdh = JSONUtils.getString(obj, "lxdh");
        this.dkyh = JSONUtils.getString(obj, "dkyh");
        this.pgjg = JSONUtils.getString(obj, "pgjg");
        this.isnew = JSONUtils.getString(obj, "new");
        this.zblbmc = JSONUtils.getString(obj, "zblbmc");
        this.fj = JSONUtils.getJSONArray(obj, "fj_img");
        this.zblb_name = JSONUtils.getString(obj, "zblb_name");
        this.cqz = JSONUtils.getString(obj, "cqz");
        this.nbzb = JSONUtils.getString(obj, "nbzb");
        this.fjbs = JSONUtils.getString(obj, "fjbs");
        this.xxsm = JSONUtils.getString(obj, "xxsm");
        this.zbyxsj = JSONUtils.getString(obj, "zbyxsj");
        this.zbyxsjtime = JSONUtils.getString(obj, "zbyxsjtime");
        this.bc = JSONUtils.getDouble(obj, "bc", 0.0);
        this.ydcs = JSONUtils.getString(obj, "ydcs");
        this.ztms = JSONUtils.getString(obj, "ztms");
        this.zbfw = JSONUtils.getString(obj, "zbfw");
        this.nc = JSONUtils.getString(obj, "nc");
        this.tbid = JSONUtils.getInt(obj, "tbid", 0);
        this.zbbz = JSONUtils.getInt(obj, "zbbz", 0);
        this.tbj = JSONUtils.getDouble(obj, "tbj", 0.0);
        this.tbsj = JSONUtils.getString(obj, "tbsj");
        this.tbsm = JSONUtils.getString(obj, "tbsm");
        this.bgjzsj = JSONUtils.getString(obj, "bgjzsj");
        this.zt = JSONUtils.getInt(obj, "zt", 0);

    }

    public String getKcbmbmc() {
        return kcbmbmc;
    }

    public void setKcbmbmc(String kcbmbmc) {
        this.kcbmbmc = kcbmbmc;
    }

    public String getZhlx() {
        return zhlx;
    }

    public void setZhlx(String zhlx) {
        this.zhlx = zhlx;
    }


    public JSONArray getZbdx() {
        return zbdx;
    }

    public void setZbdx(JSONArray zbdx) {
        this.zbdx = zbdx;
    }

    public JSONArray getFj() {
        return fj;
    }

    public void setFj(JSONArray fj) {
        this.fj = fj;
    }

    public String getZblb_name() {
        return zblb_name;
    }

    public void setZblb_name(String zblb_name) {
        this.zblb_name = zblb_name;
    }

    public String getBgjzsj() {
        return bgjzsj;
    }

    public void setBgjzsj(String bgjzsj) {
        this.bgjzsj = bgjzsj;
    }

    public String getNbzb() {
        return nbzb;
    }

    public void setNbzb(String nbzb) {
        this.nbzb = nbzb;
    }


    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public int getTbid() {
        return tbid;
    }

    public void setTbid(int tbid) {
        this.tbid = tbid;
    }

    public Double getTbj() {
        return tbj;
    }

    public void setTbj(Double tbj) {
        this.tbj = tbj;
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

    public int getTbuserid() {
        return tbuserid;
    }

    public void setTbuserid(int tbuserid) {
        this.tbuserid = tbuserid;
    }

    public int getZbbz() {
        return zbbz;
    }

    public void setZbbz(int zbbz) {
        this.zbbz = zbbz;
    }

    public String getZblbmc() {
        return zblbmc;
    }

    public void setZblbmc(String zblbmc) {
        this.zblbmc = zblbmc;
    }

    public String getZtms() {
        return ztms;
    }

    public void setZtms(String ztms) {
        this.ztms = ztms;
    }

    public String getZbfw() {
        return zbfw;
    }

    public void setZbfw(String zbfw) {
        this.zbfw = zbfw;
    }

    public int getZbid() {
        return zbid;
    }

    public void setZbid(int zbid) {
        this.zbid = zbid;
    }

    public int getZbuserid() {
        return zbuserid;
    }

    public void setZbuserid(int zbuserid) {
        this.zbuserid = zbuserid;
    }

    public int getZblb() {
        return zblb;
    }

    public void setZblb(int zblb) {
        this.zblb = zblb;
    }

    public String getZbsj() {
        return zbsj;
    }

    public void setZbsj(String zbsj) {
        this.zbsj = zbsj;
    }

    public Integer getKcbmb() {
        return kcbmb;
    }

    public void setKcbmb(int kcbmb) {
        this.kcbmb = kcbmb;
    }

    public String getBdw() {
        return bdw;
    }

    public void setBdw(String bdw) {
        this.bdw = bdw;
    }

    public String getSzcs() {
        return szcs;
    }

    public void setSzcs(String szcs) {
        this.szcs = szcs;
    }

    public String getSzcsmc() {
        return szcsmc;
    }

    public void setSzcsmc(String szcsmc) {
        this.szcsmc = szcsmc;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getDkyh() {
        return dkyh;
    }

    public void setDkyh(String dkyh) {
        this.dkyh = dkyh;
    }

    public String getPgjg() {
        return pgjg;
    }

    public void setPgjg(String pgjg) {
        this.pgjg = pgjg;
    }

    public String getCqz() {
        return cqz;
    }

    public void setCqz(String cqz) {
        this.cqz = cqz;
    }

    public String getFjbs() {
        return fjbs;
    }

    public void setFjbs(String fjbs) {
        this.fjbs = fjbs;
    }

    public String getXxsm() {
        return xxsm;
    }

    public void setXxsm(String xxsm) {
        this.xxsm = xxsm;
    }

    public String getZbyxsj() {
        return zbyxsj;
    }

    public void setZbyxsj(String zbyxsj) {
        this.zbyxsj = zbyxsj;
    }

    public String getZbyxsjtime() {
        return zbyxsjtime;
    }

    public void setZbyxsjtime(String zbyxsjtime) {
        this.zbyxsjtime = zbyxsjtime;
    }

    public Double getBc() {
        return bc;
    }

    public void setBc(double bc) {
        this.bc = bc;
    }

    public String getYdcs() {
        return ydcs;
    }

    public void setYdcs(String ydcs) {
        this.ydcs = ydcs;
    }


    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }
}
