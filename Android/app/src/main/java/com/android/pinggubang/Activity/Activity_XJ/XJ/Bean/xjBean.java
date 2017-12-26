package com.android.pinggubang.Activity.Activity_XJ.XJ.Bean;

import java.util.List;

/**
 * Created by softsea on 17/8/7.
 */

public class xjBean {


    /**
     * cmd : business.getxjList
     * code : 0
     * message : 获取询价列表成功!
     * data : [{"xjid":3036,"xjuserid":780,"xjlb":1,"xjsj":"2017-08-04 17:21","zt":3,"sqyz":null,"xjbdw":"杭州市余杭区东湖街道玲珑家园6-3-101室","szcs":"330100","szscmc":"杭州市","zctp":null,"ydcs":3,"xjyxsj":60,"xjyxsjtime":"2017-08-04 18:21","xjfw":"对外","jsbjtj":1,"bc":0.85,"jj":"0.00","bz":"1/6F，精装修，坐北朝南","zcs":null,"szc":null,"sjyt":null,"cqz":"{0AB8B96E-6A2D-7320-F804-4AEED3BADB25}","deletetime":"2017-08-04 17:31","tdyt":null,"rjl":null,"mj":"85.47","sqylx":null,"zclx":null,"ly":null,"cpxh":null,"scqy":null,"gmnf":null,"cx":null,"syzk":null,"wylx":"住宅","ztms":"过期","fwxm":""}]
     */

    private String cmd;
    private int code;
    private String message;
    private List<DataBean> data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * xjid : 3036
         * xjuserid : 780
         * xjlb : 1
         * xjsj : 2017-08-04 17:21
         * zt : 3
         * sqyz : null
         * xjbdw : 杭州市余杭区东湖街道玲珑家园6-3-101室
         * szcs : 330100
         * szscmc : 杭州市
         * zctp : null
         * ydcs : 3
         * xjyxsj : 60
         * xjyxsjtime : 2017-08-04 18:21
         * xjfw : 对外
         * jsbjtj : 1
         * bc : 0.85
         * jj : 0.00
         * bz : 1/6F，精装修，坐北朝南
         * zcs : null
         * szc : null
         * sjyt : null
         * cqz : {0AB8B96E-6A2D-7320-F804-4AEED3BADB25}
         * deletetime : 2017-08-04 17:31
         * tdyt : null
         * rjl : null
         * mj : 85.47
         * sqylx : null
         * zclx : null
         * ly : null
         * cpxh : null
         * scqy : null
         * gmnf : null
         * cx : null
         * syzk : null
         * wylx : 住宅
         * ztms : 过期
         * fwxm :
         */

        private int xjid;
        private int xjuserid;
        private int xjlb;
        private String xjsj;
        private int zt;
        private String xjbdw;
        private String szcs;
        private String szscmc;
        private int ydcs;
        private int xjyxsj;
        private String xjyxsjtime;
        private String xjfw;
        private int jsbjtj;
        private double bc;
        private String jj;
        private String bz;
        private String cqz;
        private String deletetime;
        private String mj;
        private String sqylx;
        private String wylx;
        private String ztms;
        private String fwxm;

        public int getXjid() {
            return xjid;
        }

        public void setXjid(int xjid) {
            this.xjid = xjid;
        }

        public int getXjuserid() {
            return xjuserid;
        }

        public void setXjuserid(int xjuserid) {
            this.xjuserid = xjuserid;
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

        public int getZt() {
            return zt;
        }

        public void setZt(int zt) {
            this.zt = zt;
        }



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



        public int getYdcs() {
            return ydcs;
        }

        public void setYdcs(int ydcs) {
            this.ydcs = ydcs;
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

        public String getXjfw() {
            return xjfw;
        }

        public void setXjfw(String xjfw) {
            this.xjfw = xjfw;
        }

        public int getJsbjtj() {
            return jsbjtj;
        }

        public void setJsbjtj(int jsbjtj) {
            this.jsbjtj = jsbjtj;
        }

        public double getBc() {
            return bc;
        }

        public void setBc(double bc) {
            this.bc = bc;
        }

        public String getJj() {
            return jj;
        }

        public void setJj(String jj) {
            this.jj = jj;
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

        public String getDeletetime() {
            return deletetime;
        }

        public void setDeletetime(String deletetime) {
            this.deletetime = deletetime;
        }



        public String getMj() {
            return mj;
        }

        public void setMj(String mj) {
            this.mj = mj;
        }

        public Object getSqylx() {
            return sqylx;
        }


        public String getWylx() {
            return wylx;
        }

        public void setWylx(String wylx) {
            this.wylx = wylx;
        }

        public String getZtms() {
            return ztms;
        }

        public void setZtms(String ztms) {
            this.ztms = ztms;
        }

        public String getFwxm() {
            return fwxm;
        }

        public void setFwxm(String fwxm) {
            this.fwxm = fwxm;
        }
    }
}
