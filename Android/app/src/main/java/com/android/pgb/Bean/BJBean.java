package com.android.pgb.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kanghui on 2017/1/16.
 */

public class BJBean implements Parcelable {

    /**
     * userid : 3
     * nc : sxd
     * xb :
     * tx : /user/3
     * sjhm : 18668293758
     * wx_tx : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLNkzXHz0QPxKwbUNot0FLWPys5Cibl3GcBWM0utR1WoIIFq6JicXic4Gdy05ibFbicl7gJMbb0pJkx2dw/0
     * scrzbz : 2
     * fdcgjsbz : 2
     * tdgjsbz : 0
     * zcpgsbz : 0
     * xjs : 65
     * zbs : 18
     * bjs : 30
     * kcs : 7
     * hpl : 1.00
     * tx_img : https://www.yiqiyun.org/filepgb/getfile/getSfzImg.php?imgname=tx.jpg&imgdir=/user/3/
     * title : 房地产估价师
     */


    public BJBean(JSONObject jsonObject) {

        try {


            JSONArray array = JSONUtils.getJSONArray(jsonObject, "userinfo");

            if (array != null) {

                JSONObject object = (JSONObject) array.get(0);
                setNc(JSONUtils.getString(object, "nc"));
                setTitle(JSONUtils.getString(object, "title"));
                setSjhm(JSONUtils.getString(object, "sjhm"));
                setKcs(JSONUtils.getInt(object, "kcs", 0));
                setTx_img(JSONUtils.getString(object, "tx_img"));
                Log.e("wkh" + JSONUtils.getString(object, "nc") + "");
            }
            setZj(jsonObject.get("zj").toString());
            setBc(jsonObject.get("bc").toString());
            setXjlb(jsonObject.get("xjlb").toString());
            setZt(jsonObject.get("zt").toString());
            setBjid(Integer.parseInt(jsonObject.get("bjid").toString()));
            setBjuserid(jsonObject.get("bjuserid").toString());
            setCnbz(jsonObject.get("cnbz").toString());
            setDj(jsonObject.get("dj").toString());
            setZj(jsonObject.get("zj").toString());
            setXjbdw(JSONUtils.getString(jsonObject, "xjbdw"));
            setBz(JSONUtils.getString(jsonObject, "bz"));
            setBjsj(JSONUtils.getString(jsonObject, "bjsj"));
            setMj(jsonObject.get("mj").toString());
            setFjbs(jsonObject.get("fjbs").toString());
            //[[{"fj_img":"https:\/\/www.yiqiyun.org\/filepgb\/getfile\/getSfzImg.php?imgname=1.jpg&imgdir=\/报价\/报价附件\/\/{3252F2A3-A524-3A5B-4231-95B93526FF97}"}]] 数据结构
            if (!jsonObject.get("fj_img").toString().equals("")) {

                setFj_img((JSONArray)(( jsonObject.get("fj_img"))));


            } else {
                setFj_img(null);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String bjuserid;
    private String zj;
    private int bjid;
    private String bjsj;
    private String mj;
    private String dj;
    private int userid;
    private String nc;
    private String xb;
    private String tx;
    private String sjhm;
    private String wx_tx;
    private int scrzbz;
    private int fdcgjsbz;
    private int tdgjsbz;
    private int zcpgsbz;
    private int xjs;
    private int zbs;
    private int bjs;
    private int kcs;
    private String hpl;
    private String tx_img;
    private String title;

    private String bz;

    private String fjbs;
    private String cnbz;
    private String bc;
    private String xjbdw;
    private String xjlb;
    private String zt;
    private JSONArray fj_img;


    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBjuserid() {
        return bjuserid;
    }

    public void setBjuserid(String bjuserid) {
        this.bjuserid = bjuserid;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public int getBjid() {
        return bjid;
    }

    public void setBjid(int bjid) {
        this.bjid = bjid;
    }

    public String getBjsj() {
        return bjsj;
    }

    public void setBjsj(String bjsj) {
        this.bjsj = bjsj;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getFjbs() {
        return fjbs;
    }

    public void setFjbs(String fjbs) {
        this.fjbs = fjbs;
    }

    public String getCnbz() {
        return cnbz;
    }

    public void setCnbz(String cnbz) {
        this.cnbz = cnbz;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getXjbdw() {
        return xjbdw;
    }

    public void setXjbdw(String xjbdw) {
        this.xjbdw = xjbdw;
    }

    public String getXjlb() {
        return xjlb;
    }

    public void setXjlb(String xjlb) {
        this.xjlb = xjlb;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public JSONArray getFj_img() {
        return fj_img;
    }

    public void setFj_img(JSONArray fj_img) {
        this.fj_img = fj_img;
    }


    protected BJBean(Parcel in) {
    }

    public static final Creator<BJBean> CREATOR = new Creator<BJBean>() {
        @Override
        public BJBean createFromParcel(Parcel in) {
            return new BJBean(in);
        }

        @Override
        public BJBean[] newArray(int size) {
            return new BJBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getWx_tx() {
        return wx_tx;
    }

    public void setWx_tx(String wx_tx) {
        this.wx_tx = wx_tx;
    }

    public int getScrzbz() {
        return scrzbz;
    }

    public void setScrzbz(int scrzbz) {
        this.scrzbz = scrzbz;
    }

    public int getFdcgjsbz() {
        return fdcgjsbz;
    }

    public void setFdcgjsbz(int fdcgjsbz) {
        this.fdcgjsbz = fdcgjsbz;
    }

    public int getTdgjsbz() {
        return tdgjsbz;
    }

    public void setTdgjsbz(int tdgjsbz) {
        this.tdgjsbz = tdgjsbz;
    }

    public int getZcpgsbz() {
        return zcpgsbz;
    }

    public void setZcpgsbz(int zcpgsbz) {
        this.zcpgsbz = zcpgsbz;
    }

    public int getXjs() {
        return xjs;
    }

    public void setXjs(int xjs) {
        this.xjs = xjs;
    }

    public int getZbs() {
        return zbs;
    }

    public void setZbs(int zbs) {
        this.zbs = zbs;
    }

    public int getBjs() {
        return bjs;
    }

    public void setBjs(int bjs) {
        this.bjs = bjs;
    }

    public int getKcs() {
        return kcs;
    }

    public void setKcs(int kcs) {
        this.kcs = kcs;
    }

    public String getHpl() {
        return hpl;
    }

    public void setHpl(String hpl) {
        this.hpl = hpl;
    }

    public String getTx_img() {
        return tx_img;
    }

    public void setTx_img(String tx_img) {
        this.tx_img = tx_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}