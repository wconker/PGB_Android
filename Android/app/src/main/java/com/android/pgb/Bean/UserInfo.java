package com.android.pgb.Bean;

import com.android.pgb.Utils.JSONUtils;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kanghui on 2017/3/8.
 */

public class UserInfo implements Serializable {


    public UserInfo(JSONObject obj) {

        this.setQygly(JSONUtils.getInt(obj, "qygly", 0));
        this.setAdminSign(JSONUtils.getInt(obj, "adminSign", 0));
        this.setScrzbz(JSONUtils.getInt(obj, "scrzbz", 0));
        this.setFdcgjsbz(JSONUtils.getInt(obj, "fdcgjsbz", 0));
        this.setSjhmbdbz(JSONUtils.getInt(obj, "sjhmbdbz", 0));
        this.setGsmcbdbz(JSONUtils.getInt(obj, "gsmcbdbz", 0));
        this.setDzyxjhbz(JSONUtils.getInt(obj, "dzyxjhbz", 0));
        this.setTdgjsbz(JSONUtils.getInt(obj, "tdgjsbz", 0));
        this.setCity(JSONUtils.getString(obj, "city"));
        this.setNc(JSONUtils.getString(obj, "nc"));
        this.setCity_name(JSONUtils.getString(obj, "city_name"));

        this.setGsmc(JSONUtils.getString(obj, "gsmc"));
        this.setGsmcbdbz(JSONUtils.getInt(obj, "gsmcbdbz", 0));
        this.setSfzfm(JSONUtils.getString(obj, "sfzfm"));
        this.setTitle(JSONUtils.getString(obj, "title"));
        this.setDzyx(JSONUtils.getString(obj, "dzyx"));
        this.setSjhm(JSONUtils.getString(obj, "sjhm"));
        this.setTd_img(JSONUtils.getString(obj, "td_img"));
        this.setXm(JSONUtils.getString(obj, "xm"));
        this.setFdcgjszch(JSONUtils.getString(obj, "fdcgjszch"));
        this.setTdgjszch(JSONUtils.getString(obj, "tdgjszch"));
        this.setFc_img(JSONUtils.getString(obj, "fc_img"));
        this.setSmrzyj(JSONUtils.getString(obj, "smrzyj"));
        this.setFgrzyj(JSONUtils.getString(obj, "fgrzyj"));
        this.setTgrzyj(JSONUtils.getString(obj, "tgrzyj"));
        this.setTx_img(JSONUtils.getString(obj, "tx_img"));
        this.setSfzzm(JSONUtils.getString(obj, "sfzzm"));
        this.setSfzh(JSONUtils.getString(obj, "sfzh"));
        this.setSfzfm_img(JSONUtils.getString(obj, "sfzfm_img"));
        this.setSfzzm_img(JSONUtils.getString(obj, "sfzzm_img"));
        this.setCity(JSONUtils.getString(obj, "city"));
        this.setZhye(JSONUtils.getString(obj, "zhye"));
        this.setJf(JSONUtils.getInt(obj, "jf", 0));
        this.setSrzhye(JSONUtils.getString(obj, "srzhye"));
        this.setQm(JSONUtils.getString(obj, "qm"));


    }

    /**
     * adminSign : 1
     * adminflag : 1
     * bjs : 12
     * city : 330100
     * city_name : 杭州市
     * djje : 1.20
     * dzyx : 296491335@qq.com
     * dzyxjhbz : 1
     * fc_img : https://www.yiqiyun.org/filepgb/getfile/getSfzImg.php?imgname=fdcgjs.jpg&imgdir=/user/2
     * fdcgjsbz : 2
     * fdcgjszch : 123456
     * fdcgjszgz : /user/2
     * fgrzyj :
     * gsmc : 北京安泰祥土地房地产评估有限公司
     * gsmcbdbz : 1
     * hpl : 1.00
     * jf : 0
     * kcs : 7
     * lastbbh :
     * lastqd : 4
     * lasttime : 2017-03-08 16:41:42
     * lxdz : null
     * money_error : 0
     * nc : conker
     * scrzbz : 2
     * scsfz :
     * sfzfm : /user/2
     * sfzfm_img : https://www.yiqiyun.org/filepgb/getfile/getSfzImg.php?imgname=sfzfm.jpg&imgdir=/user/2
     * sfzh : 332522199206051933
     * sfzzm : /user/2
     * sfzzm_img : https://www.yiqiyun.org/filepgb/getfile/getSfzImg.php?imgname=sfzzm.jpg&imgdir=/user/2
     * sjhm : 15658690695
     * sjhmbdbz : 1
     * smrzyj :
     * smsflag : 1
     * sq_num : 0
     * srzhye : 1.39
     * td_img : https://www.yiqiyun.org/filepgb/getfile/getSfzImg.php?imgname=tdgjs.jpg&imgdir=/user/2
     * tdgjsbz : 0
     * tdgjszch : null
     * tdgjszgz : /user/2
     * tgrzyj : null
     * title : 房地产估价师
     * tone_md5 : 14230cad8446ee6122cf03a1ae0d1864
     * tone_url : https://www.yiqiyun.org/filepgb/getfile/recinfo.amr
     * tx : /user/2
     * tx_img : https://www.yiqiyun.org/filepgb/getfile/getSfzImg.php?imgname=tx.jpg&imgdir=/user/2&rand=duFMT
     * tx_md5 : aadf67b01fc95c88cacc70d22a1e419d
     * userid : 2
     * wx_tx : http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqDqgEXCF56mODGI5y1Puk4AOqf2twr8KUxK208zzaj5fZN3m0s2YT0fI1NmIQYz389y0jiceOdTiaQ/0
     * wxid : ojP3r0NwUthPHSQ3axcbWN7CoLWE
     * xb : null
     * xjs : 16
     * xm : 吴康辉
     * yhxytybz : 1
     * zbs : 7
     * zc_img :
     * zcpgsbz : 0
     * zcpgszch : null
     * zcpgszgz :
     * zgrzyj : null
     * zhye : 4.95
     */

    private int adminSign;
    private int qygly;
    private String bjs;
    private String city;
    private String city_name;
    private String djje;
    private String dzyx;
    private int dzyxjhbz;
    private String fc_img;
    private int fdcgjsbz;
    private String fdcgjszch;
    private String fdcgjszgz;
    private String fgrzyj;
    private String gsmc;
    private int gsmcbdbz;
    private String hpl;
    private int jf;
    private int kcs;
    private String lastbbh;
    private int lastqd;
    private String lasttime;
    private Object lxdz;
    private int money_error;
    private String nc;
    private int scrzbz;
    private String scsfz;
    private String sfzfm;
    private String sfzfm_img;
    private String sfzh;
    private String sfzzm;
    private String sfzzm_img;
    private String sjhm;
    private int sjhmbdbz;
    private String smrzyj;
    private int smsflag;
    private int sq_num;
    private String srzhye;
    private String td_img;
    private int tdgjsbz;
    private String tdgjszch;
    private String tdgjszgz;
    private String tgrzyj;
    private String title;
    private String tone_md5;
    private String tone_url;
    private String tx;
    private String tx_img;
    private String tx_md5;
    private int userid;
    private String wx_tx;
    private String wxid;
    private Object xb;
    private int xjs;
    private String xm;
    private int yhxytybz;
    private int zbs;
    private String zc_img;
    private int zcpgsbz;
    private Object zcpgszch;
    private String zcpgszgz;
    private Object zgrzyj;
    private String zhye;
    private String qm;

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public int getAdminSign() {
        return adminSign;
    }

    public void setAdminSign(int adminSign) {
        this.adminSign = adminSign;
    }

    public int getQygly() {
        return qygly;
    }

    public void setQygly(int qygly) {
        this.qygly = qygly;
    }

    public String getBjs() {
        return bjs;
    }

    public void setBjs(String bjs) {
        this.bjs = bjs;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDjje() {
        return djje;
    }

    public void setDjje(String djje) {
        this.djje = djje;
    }

    public String getDzyx() {
        return dzyx;
    }

    public void setDzyx(String dzyx) {
        this.dzyx = dzyx;
    }

    public int getDzyxjhbz() {
        return dzyxjhbz;
    }

    public void setDzyxjhbz(int dzyxjhbz) {
        this.dzyxjhbz = dzyxjhbz;
    }

    public String getFc_img() {
        return fc_img;
    }

    public void setFc_img(String fc_img) {
        this.fc_img = fc_img;
    }

    public int getFdcgjsbz() {
        return fdcgjsbz;
    }

    public void setFdcgjsbz(int fdcgjsbz) {
        this.fdcgjsbz = fdcgjsbz;
    }

    public String getFdcgjszch() {
        return fdcgjszch;
    }

    public void setFdcgjszch(String fdcgjszch) {
        this.fdcgjszch = fdcgjszch;
    }

    public String getFdcgjszgz() {
        return fdcgjszgz;
    }

    public void setFdcgjszgz(String fdcgjszgz) {
        this.fdcgjszgz = fdcgjszgz;
    }

    public String getFgrzyj() {
        return fgrzyj;
    }

    public void setFgrzyj(String fgrzyj) {
        this.fgrzyj = fgrzyj;
    }

    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    public int getGsmcbdbz() {
        return gsmcbdbz;
    }

    public void setGsmcbdbz(int gsmcbdbz) {
        this.gsmcbdbz = gsmcbdbz;
    }

    public String getHpl() {
        return hpl;
    }

    public void setHpl(String hpl) {
        this.hpl = hpl;
    }

    public int getJf() {
        return jf;
    }

    public void setJf(int jf) {
        this.jf = jf;
    }

    public int getKcs() {
        return kcs;
    }

    public void setKcs(int kcs) {
        this.kcs = kcs;
    }

    public String getLastbbh() {
        return lastbbh;
    }

    public void setLastbbh(String lastbbh) {
        this.lastbbh = lastbbh;
    }

    public int getLastqd() {
        return lastqd;
    }

    public void setLastqd(int lastqd) {
        this.lastqd = lastqd;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public Object getLxdz() {
        return lxdz;
    }

    public void setLxdz(Object lxdz) {
        this.lxdz = lxdz;
    }

    public int getMoney_error() {
        return money_error;
    }

    public void setMoney_error(int money_error) {
        this.money_error = money_error;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public int getScrzbz() {
        return scrzbz;
    }

    public void setScrzbz(int scrzbz) {
        this.scrzbz = scrzbz;
    }

    public String getScsfz() {
        return scsfz;
    }

    public void setScsfz(String scsfz) {
        this.scsfz = scsfz;
    }

    public String getSfzfm() {
        return sfzfm;
    }

    public void setSfzfm(String sfzfm) {
        this.sfzfm = sfzfm;
    }

    public String getSfzfm_img() {
        return sfzfm_img;
    }

    public void setSfzfm_img(String sfzfm_img) {
        this.sfzfm_img = sfzfm_img;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getSfzzm() {
        return sfzzm;
    }

    public void setSfzzm(String sfzzm) {
        this.sfzzm = sfzzm;
    }

    public String getSfzzm_img() {
        return sfzzm_img;
    }

    public void setSfzzm_img(String sfzzm_img) {
        this.sfzzm_img = sfzzm_img;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public int getSjhmbdbz() {
        return sjhmbdbz;
    }

    public void setSjhmbdbz(int sjhmbdbz) {
        this.sjhmbdbz = sjhmbdbz;
    }

    public String getSmrzyj() {
        return smrzyj;
    }

    public void setSmrzyj(String smrzyj) {
        this.smrzyj = smrzyj;
    }

    public int getSmsflag() {
        return smsflag;
    }

    public void setSmsflag(int smsflag) {
        this.smsflag = smsflag;
    }

    public int getSq_num() {
        return sq_num;
    }

    public void setSq_num(int sq_num) {
        this.sq_num = sq_num;
    }

    public String getSrzhye() {
        return srzhye;
    }

    public void setSrzhye(String srzhye) {
        this.srzhye = srzhye;
    }

    public String getTd_img() {
        return td_img;
    }

    public void setTd_img(String td_img) {
        this.td_img = td_img;
    }

    public int getTdgjsbz() {
        return tdgjsbz;
    }

    public void setTdgjsbz(int tdgjsbz) {
        this.tdgjsbz = tdgjsbz;
    }

    public String getTdgjszch() {
        return tdgjszch;
    }

    public void setTdgjszch(String tdgjszch) {
        this.tdgjszch = tdgjszch;
    }

    public String getTdgjszgz() {
        return tdgjszgz;
    }

    public void setTdgjszgz(String tdgjszgz) {
        this.tdgjszgz = tdgjszgz;
    }

    public String getTgrzyj() {
        return tgrzyj;
    }

    public void setTgrzyj(String tgrzyj) {
        this.tgrzyj = tgrzyj;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTone_md5() {
        return tone_md5;
    }

    public void setTone_md5(String tone_md5) {
        this.tone_md5 = tone_md5;
    }

    public String getTone_url() {
        return tone_url;
    }

    public void setTone_url(String tone_url) {
        this.tone_url = tone_url;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getTx_img() {
        return tx_img;
    }

    public void setTx_img(String tx_img) {
        this.tx_img = tx_img;
    }

    public String getTx_md5() {
        return tx_md5;
    }

    public void setTx_md5(String tx_md5) {
        this.tx_md5 = tx_md5;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getWx_tx() {
        return wx_tx;
    }

    public void setWx_tx(String wx_tx) {
        this.wx_tx = wx_tx;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Object getXb() {
        return xb;
    }

    public void setXb(Object xb) {
        this.xb = xb;
    }

    public int getXjs() {
        return xjs;
    }

    public void setXjs(int xjs) {
        this.xjs = xjs;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public int getYhxytybz() {
        return yhxytybz;
    }

    public void setYhxytybz(int yhxytybz) {
        this.yhxytybz = yhxytybz;
    }

    public int getZbs() {
        return zbs;
    }

    public void setZbs(int zbs) {
        this.zbs = zbs;
    }

    public String getZc_img() {
        return zc_img;
    }

    public void setZc_img(String zc_img) {
        this.zc_img = zc_img;
    }

    public int getZcpgsbz() {
        return zcpgsbz;
    }

    public void setZcpgsbz(int zcpgsbz) {
        this.zcpgsbz = zcpgsbz;
    }

    public Object getZcpgszch() {
        return zcpgszch;
    }

    public void setZcpgszch(Object zcpgszch) {
        this.zcpgszch = zcpgszch;
    }

    public String getZcpgszgz() {
        return zcpgszgz;
    }

    public void setZcpgszgz(String zcpgszgz) {
        this.zcpgszgz = zcpgszgz;
    }

    public Object getZgrzyj() {
        return zgrzyj;
    }

    public void setZgrzyj(Object zgrzyj) {
        this.zgrzyj = zgrzyj;
    }

    public String getZhye() {
        return zhye;
    }

    public void setZhye(String zhye) {
        this.zhye = zhye;
    }
}
