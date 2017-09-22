package com.android.pgb.Bean;

import java.util.List;

/**
 * Created by softsea on 17/9/19.
 */

public class DocumentBean {
    /**
     * cmd : business.getDocuments
     * code : 0
     * message : 获取文档列表成功!
     * data : [{"id":1,"filename":"test1","filetype":"document/docx","size":"220kb","uploader":"白耀强","uploadtime":"2017.09.07","downloads":"150","url":"https://www.yiqiyun.org/filepgb/getfile/get_doc.php?&file_name=test1.docx&file_dir=/测试/test1 "},{"id":2,"filename":"test2","filetype":"document/docx","size":"220kb","uploader":"白耀强","uploadtime":"2017.09.07","downloads":"50","url":"https://www.yiqiyun.org/filepgb/getfile/getSfzDoc.php?docname=test1.docx&docdir=/documents"},{"id":3,"filename":"test3","filetype":"document/docx","size":"220kb","uploader":"白耀强","uploadtime":"2017.09.07","downloads":"50","url":"https://www.yiqiyun.org/filepgb/getfile/get_doc.php?&file_name=test1.docx&file_dir=/测试/test1"},{"id":4,"filename":"test4","filetype":"document/docx","size":"220kb","uploader":"白耀强","uploadtime":"2017.09.07","downloads":"50","url":"https://www.yiqiyun.org/filepgb/getfile/getSfzDoc.php?docname=test1.docx&docdir=/documents"},{"id":5,"filename":"test5","filetype":"document/docx","size":"220kb","uploader":"白耀强","uploadtime":"2017.09.07","downloads":"50","url":"https://www.yiqiyun.org/filepgb/getfile/getSfzDoc.php?docname=test1.docx&docdir=/documents"}]
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
         * id : 1
         * filename : test1
         * filetype : document/docx
         * size : 220kb
         * uploader : 白耀强
         * uploadtime : 2017.09.07
         * downloads : 150
         * url : https://www.yiqiyun.org/filepgb/getfile/get_doc.php?&file_name=test1.docx&file_dir=/测试/test1
         */

        private int id;
        private String filename;
        private String filetype;
        private String size;
        private String uploader;
        private String uploadtime;
        private String downloads;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFiletype() {
            return filetype;
        }

        public void setFiletype(String filetype) {
            this.filetype = filetype;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUploader() {
            return uploader;
        }

        public void setUploader(String uploader) {
            this.uploader = uploader;
        }

        public String getUploadtime() {
            return uploadtime;
        }

        public void setUploadtime(String uploadtime) {
            this.uploadtime = uploadtime;
        }

        public String getDownloads() {
            return downloads;
        }

        public void setDownloads(String downloads) {
            this.downloads = downloads;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
