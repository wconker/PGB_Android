package com.android.pgb.Bean;

import java.util.List;

/**
 * Created by softsea on 17/9/19.
 */

public class CompanyInfoBean {
    /**
     * cmd : business.getComDetail
     * code : 0
     * message : 获取详情信息成功!
     * data : {"company_id":102,"company_name":"软洋科技有限公司","logo":"https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","business":"物流信息服务","address":"滨江亚科中心","telephone":"13396551997","description":"软洋科技软洋科技软洋科技","image":["https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg"]}
     */

    private String cmd;
    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * company_id : 102
         * company_name : 软洋科技有限公司
         * logo : https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg
         * business : 物流信息服务
         * address : 滨江亚科中心
         * telephone : 13396551997
         * description : 软洋科技软洋科技软洋科技
         * image : ["https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg","https://www.yiqiyun.org/filepgb/getfile/59b20a934db44.jpg"]
         */

        private int company_id;
        private String company_name;
        private String logo;
        private String business;
        private String address;
        private String telephone;
        private String description;
        private List<String> image;

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }
    }
}
