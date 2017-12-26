package com.android.pinggubang.Bean;

import java.util.List;

/**
 * Created by kanghui on 2017/1/20.
 */

public class NewBean  {


    /**
     * cmd : conn.getNewsList
     * code : 0
     * message : 获取行业资讯列表成功!
     * data : [{"id":"13","title":"中国商业银行不良资产处置策略研究","fbsj":"2017.07.14"},{"id":"12","title":"房地产投资信托基金物业评估指引（试行）","fbsj":"2017.07.14"},{"id":"11","title":"中华人民共和国资产评估法","fbsj":"2017.07.14"}]
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
         * id : 13
         * title : 中国商业银行不良资产处置策略研究
         * fbsj : 2017.07.14
         */

        private String id;
        private String title;
        private String fbsj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFbsj() {
            return fbsj;
        }

        public void setFbsj(String fbsj) {
            this.fbsj = fbsj;
        }
    }
}
