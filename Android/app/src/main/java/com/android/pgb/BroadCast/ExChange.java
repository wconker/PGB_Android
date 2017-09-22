package com.android.pgb.BroadCast;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.pgb.Bean.TBBean;
import com.android.pgb.Bean.XJBean;
import com.android.pgb.Bean.ZBBean;
import com.android.pgb.Utils.HTTPSUtils;
import com.android.pgb.Utils.JSONUtils;
import com.android.pgb.Utils.MCrypt;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

import static okhttp3.ws.WebSocket.TEXT;

/**
 * Created by kanghui on 2017/1/5.
 */

public class ExChange {
    //参数类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private MCrypt mCrypt;
    public OkHttpClient client;
    private CallBackForData _backForData;
    private String encryptStr;
    private JSONObject jsonObj;
    public Integer userId = 0;
    public String sign = "";
    public String pwd = "";
    public String phone = "";
    public String versioncode = "";
    private JSONObject jsonObjArr;


    private void InstanceHttp(Context context) {

        if (HTTPSUtils.client == null) {
            HTTPSUtils.getInstance(context);
        }
        this.client = HTTPSUtils.client;
    }

    public ExChange(Context context, String flag) {
        InstanceHttp(context);
    }

    public ExChange(CallBackForData backForData, Context context) {
        InstanceHttp(context);
        _backForData = backForData;
    }

    public ExChange(CallBackForData backForData) {
        _backForData = backForData;
        this.client = HTTPSUtils.client;
        if (HTTPSUtils.USERID > 0) {
            this.userId = HTTPSUtils.USERID;
        }
        if (!HTTPSUtils.Sign.equals("")) {

            this.sign = HTTPSUtils.Sign;
        }
        if (!HTTPSUtils.PWD.equals("")) {
            this.pwd = HTTPSUtils.PWD;
        }
        if (!HTTPSUtils.PHONE.equals("")) {
            this.phone = HTTPSUtils.PHONE;
        }
        if (!HTTPSUtils.VersionCode.equals("")) {
            this.versioncode = HTTPSUtils.VersionCode;
        }
    }

    /* *
    获取手机验证码
   */
    public void getVertifyCode(String PhoneNum) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("sjhm", PhoneNum);
            jsonObj.put("cmd", "conn.getVertifyCode");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* *
    获取版本号
   */
    public void getVersion() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObj.put("cmd", "conn.getVersion");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* *
       更新经纬度
      */
    public void UpdateSite(double jd, double wd) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("jd", jd);
            jsonObjArr.put("wd", wd);
            jsonObj.put("cmd", "user.UpdateSite");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    *
       修改密码
      */
    public void UpdatePass(String sjhm, String password, String yzm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("sjhm", sjhm);
            jsonObjArr.put("password", password);
            jsonObjArr.put("yzm", yzm);
            jsonObj.put("cmd", "conn.UpdatePass");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取新闻列表
     *
     * @param rows
     */
    public void getNewsList(int rows) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("rows", rows);
            jsonObj.put("cmd", "conn.getNewsList");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* *
    获取推荐公司
   */
    public void getPggsTjList() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObj.put("cmd", "user.getPggsTjList");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* *
    用户注册
   */
    public void Register(String PhoneNum, String nc, String password, String yzm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {


            jsonObjArr.put("sjhm", PhoneNum);
            jsonObjArr.put("nc", nc);
            jsonObjArr.put("password", password);
            jsonObjArr.put("yzm", yzm);
            jsonObj.put("cmd", "user.Register");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    快速绑定
       */
    public void setUserinfo(String nc, String xm, String fgzch, String tgzch, String zczch) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("nc", nc);
            jsonObjArr.put("xm", xm);
            jsonObjArr.put("fgzch", fgzch);
            jsonObjArr.put("tgzch", tgzch);
            jsonObjArr.put("zczch", zczch);
            jsonObj.put("cmd", "user.setUserinfo");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
    添加账号
       */
    public void addAccount(String zh, String xm, int lx) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zh", zh);
            jsonObjArr.put("xm", xm);
            jsonObjArr.put("zhlx", lx);

            jsonObj.put("cmd", "user.addAccount");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 文库列表
     */
    public void getDocumentLists() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObj.put("cmd", "business.getDocuments");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 文档下载
     */
    public void loadDocuments(int id) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("id", id);
            jsonObj.put("cmd", "business.loadDocuments");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
   删除账号
       */
    public void deleteAccount(int Id) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("Id", Id);

            jsonObj.put("cmd", "user.deleteAccount");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    获取账号列表
       */
    public void getAccount() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);


            jsonObj.put("cmd", "user.getAccount");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    获取新闻详情
       */
    public void getNewsInfo(int newId) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObjArr.put("id", newId);


            jsonObj.put("cmd", "conn.getNewsInfo");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    充值记录
       */
    public void getMyCzjl() {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObj.put("cmd", "user.getMyCzjl");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
    现金流水
       */
    public void getXjlsList() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "user.getXjlsList");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    退款
       */
    public void getTklist() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "user.getTklist");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    我对提现记录
       */
    public void getMyTxjl() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "user.getMyTxjl");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
      更新签名
         */
    public void updateSign(String qm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("qm", qm);

            jsonObj.put("cmd", "user.updateSign");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 快速自动认证
     *
     * @param xm   姓名
     * @param gsmc 公司名称
     */
    public void Certification(String xm, String gsmc) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xm", xm);
            jsonObjArr.put("gsmc", gsmc);


            jsonObj.put("cmd", "user.Certification");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 实名认证
     *
     * @param xm   姓名
     * @param sfzh 公司名称
     **/

    public void Certification(String xm, String sfzh, int isIdCard) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xm", xm);
            jsonObjArr.put("sfzh", sfzh);
            jsonObjArr.put("isIdCard", isIdCard);

            Log.e("CCC", "cf+" + jsonObjArr);
            jsonObj.put("cmd", "user.Certification");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取勘察工具之勘察列表
     */

    public void getCkbList() {


        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "business.getCkbList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
    检测公司
       */
    public void verifyCompany(String jgmc) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("jgmc", jgmc);

            jsonObj.put("cmd", "user.verifyCompany");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
       向我申请
          */
    public void getToMeApplyrz() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);


            jsonObj.put("cmd", "user.getToMeApplyrz");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
       同意添加好友
          */
    public void agreeAddFriend(String jgmc, String squserid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("jgmc", jgmc);
            jsonObjArr.put("squserid", squserid);

            jsonObj.put("cmd", "user.agreeAddFriend");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
       移除好友
          */
    public void removeMember(int memberUserid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("memberUserid", memberUserid);

            jsonObj.put("cmd", "user.removeMember");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
       设置部门
          */
    public void setMemberInfo(int memberUserid, int adminFlag, String bm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("adminFlag", adminFlag);
            jsonObjArr.put("memberUserid", memberUserid);
            jsonObjArr.put("bm", bm);

            jsonObj.put("cmd", "user.setMemberInfo");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    获取email 验证码
       */
    public void getEmailCode(String address) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("email", address);

            jsonObj.put("cmd", "conn.getEmailCode");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    房产评估师认证
       */
    public void Qualification_fc(String fdcgjszch) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("rzlx", 1);
            jsonObjArr.put("fdcgjszch", fdcgjszch);

            jsonObj.put("cmd", "user.Qualification");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
        土地评估师认证
           */
    public void Qualification_td(String tdgjszch) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("rzlx", 2);
            jsonObjArr.put("tdgjszch", tdgjszch);

            jsonObj.put("cmd", "user.Qualification");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
       绑定手机号码
       */
    public void bindSjhm(String PhoneNum, String yzm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("sjhm", PhoneNum);
            jsonObjArr.put("yzm", yzm);
            jsonObj.put("cmd", "user.bindSjhm");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
       绑定邮箱
         */
    public void bindEmail(String email, String yzm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("email", email);
            jsonObjArr.put("yzm", yzm);
            jsonObj.put("cmd", "user.bindEmail");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     绑定公司
       */
    public void bindCompany(String jgmc, int sqbz) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("jgmc", jgmc);
            jsonObjArr.put("sqbz", sqbz);
            jsonObj.put("cmd", "user.bindCompany");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* *
    城市列表
   */
    public void getCityList() {


        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "business.getCityList");//再将这个json格式的的数组放到最终的json对象中。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 登录模块
     *
     * @param login_mode
     * @param nc
     * @param password
     * @param yzm
     * @param sjhm
     */
    public void Login(String login_mode, String nc, String password, String yzm, String sjhm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("qd", 1);
            jsonObjArr.put("login_mode", login_mode);
            jsonObjArr.put("sjhm", sjhm);
            jsonObjArr.put("password", password);
            jsonObjArr.put("yzm", yzm);


            jsonObjArr.put("bbh", HTTPSUtils.VersionCode);
            jsonObjArr.put("nc", nc);
            jsonObjArr.put("finger", "");
            jsonObj.put("cmd", "user.Login");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取用户信息
     */
    public void getUserinfo() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "user.getUserinfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 下载附件
     */
    public void download_zb_file(int zbid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);


            jsonObj.put("cmd", "business.download_zb_file");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 发送至邮箱
     */
    public void AgainSendEmail_kcfile(int zbid, String email) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);
            jsonObjArr.put("email", email);

            jsonObj.put("cmd", "business.AgainSendEmail_kcfile");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取地区列表
     *
     * @param cityType
     * @param preCode
     */

    public void getCityList(String cityType, String preCode) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObjArr.put("preCode", preCode);
            jsonObjArr.put("type", cityType);

            jsonObj.put("cmd", "conn.getCityList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 智能评估
     *
     * @param cmd
     * @param val
     */

    public void selectCommunity(String cmd, String... val) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObj.put("cmd", cmd);//命令名称。
            if (cmd.equals("conn.selectCommunity")) {
                jsonObjArr.put("xqmc", val[0]);
            }

            if (cmd.equals("conn.selectBuildingNo")) {
                jsonObjArr.put("xqbh", val[0]);

            }
            if (cmd.equals("conn.selectUnitNo")) {
                jsonObjArr.put("xqbh", val[0]);
                jsonObjArr.put("zh", val[1]);

            }
            if (cmd.equals("conn.selectFloor")) {
                jsonObjArr.put("xqbh", val[0]);
                jsonObjArr.put("zh", val[1]);
                jsonObjArr.put("dy", val[2]);

            }
            if (cmd.equals("conn.selectDoorNo")) {
                jsonObjArr.put("xqbh", val[0]);
                jsonObjArr.put("zh", val[1]);
                jsonObjArr.put("dy", val[2]);
                jsonObjArr.put("lcs", val[3]);
            }


            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取订阅城市列表
     *
     * @param userid
     */

    public void getCollectCity(int userid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);


            jsonObj.put("cmd", "business.getYwCity");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 开始评估
     *
     * @param parm
     */

    public void evaluate(String... parm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("hbh", parm[0]);
            jsonObjArr.put("jzmj", parm[1]);
            jsonObjArr.put("zxqk", parm[2]);
            jsonObjArr.put("tsqk", parm[3]);
            Log.e("DDD", "" + jsonObjArr);
            jsonObj.put("cmd", "conn.evaluate");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 设置订阅城市
     *
     * @param userid
     * @param dqdm
     */

    public void setCollectCity(int userid, String dqdm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", userid);
            jsonObjArr.put("dqdm", dqdm);


            jsonObj.put("cmd", "business.setYwCity");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 添加询价
     *
     * @param xjBean
     */

    public void addxj(XJBean xjBean, String typeCmd) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("szcs", xjBean.getSzcs());
            jsonObjArr.put("xjbdw", xjBean.getXjbdw());
            jsonObjArr.put("zcs", xjBean.getZcs());
            jsonObjArr.put("xjdx", xjBean.getXjdx());
            jsonObjArr.put("szc", xjBean.getSzc());
            jsonObjArr.put("mj", xjBean.getMj());
            jsonObjArr.put("bz", xjBean.getBz());
            jsonObjArr.put("bc", xjBean.getBc());
            jsonObjArr.put("nbxj", xjBean.getNbxj());
            jsonObjArr.put("fwxm", xjBean.getFwxm());
            jsonObjArr.put("wylx", xjBean.getWylx());
            jsonObjArr.put("xjlb", xjBean.getXjlb());
            jsonObjArr.put("xjyxsj", xjBean.getXjyxsj());
            jsonObjArr.put("yxbjs", xjBean.getYxbjs());
            jsonObjArr.put("zhlx", xjBean.getZhlx());
            jsonObjArr.put("xjid", xjBean.getXjid());
            jsonObjArr.put("xjyxsj", xjBean.getXjyxsj());

            jsonObj.put("cmd", typeCmd);//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());

        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 更新询价
     *
     * @param xjBean
     */

    public void addxj(XJBean xjBean) {


        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("szcs", xjBean.getSzcs());
            jsonObjArr.put("xjbdw", xjBean.getXjbdw());
            jsonObjArr.put("zcs", xjBean.getZcs());
            jsonObjArr.put("xjdx", xjBean.getXjdx());
            jsonObjArr.put("szc", xjBean.getSzc());
            jsonObjArr.put("mj", xjBean.getMj());
            jsonObjArr.put("bz", xjBean.getBz());
            jsonObjArr.put("bc", xjBean.getBc());
            jsonObjArr.put("nbxj", xjBean.getNbxj());
            jsonObjArr.put("fwxm", xjBean.getFwxm());
            jsonObjArr.put("wylx", xjBean.getWylx());
            jsonObjArr.put("xjlb", xjBean.getXjlb());
            jsonObjArr.put("xjyxsj", xjBean.getXjyxsj());
            jsonObjArr.put("yxbjs", xjBean.getYxbjs());
            jsonObjArr.put("zhlx", xjBean.getZhlx());
            jsonObjArr.put("xjyxsj", xjBean.getXjyxsj());


            jsonObj.put("cmd", "business.addxj");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        Log.e("wwww", encryptStr);
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取模板
     *
     * @param mbbh
     */

    public void getKcmbList(String mbbh) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("mbbh", mbbh);


            jsonObj.put("cmd", "business.getKcmbList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        System.out.println("wkh_____" + encryptStr);
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取询价列表
     */
    public void getxjList(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("page", page);
            jsonObjArr.put("sign", this.sign);
            jsonObj.put("cmd", "business.getxjList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。


            Log.e("COnker", jsonObjArr + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取我的询价
     *
     * @param page
     */

    public void getMyxjList(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("page", page);

            jsonObj.put("cmd", "business.getMyxjList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 系统参数
     *
     * @param type
     */

    public void getSyType(String type) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("type", type);

            //构造cmd
            jsonObj.put("cmd", "conn.getSyType");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取评估机构列表
     *
     * @param word
     */

    public void getPggsList(String word) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("gsmc", word);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("userid", this.userId);
            //构造cmd
            jsonObj.put("cmd", "user.getPggsList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取我的报价详细信息
     *
     * @param userid
     * @param bjid
     */

    public void getMybjInfo(int userid, int bjid) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("bjid", bjid);


            //构造cmd
            jsonObj.put("cmd", "business.getMybjInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取我的报价列表详细信息
     *
     * @param page
     */

    public void getMybjList(int page) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("page", page);
            //构造cmd
            jsonObj.put("cmd", "business.getMybjList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取向我报价详细信息
     *
     * @param userid
     * @param bjid
     */

    public void getbjInfo(int userid, String bjid) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据

            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("bjid", bjid);
//            jsonObjArr.put("xjid", xjid);


            //构造cmd
            jsonObj.put("cmd", "business.getbjInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 询价详情
     *
     * @param userid
     * @param xjid
     */
    public void getxjInfo(int userid, int xjid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xjid", xjid);
            //构造cmd
            jsonObj.put("cmd", "business.getxjInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 申请终止询价
     *
     * @param userid
     * @param xjid
     */
    public void SqendXj(int userid, int xjid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xjid", xjid);
            //构造cmd
            jsonObj.put("cmd", "business.SqendXj");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 结束询价
     *
     * @param xjid
     */
    public void endXj(int xjid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xjid", xjid);
            //构造cmd
            jsonObj.put("cmd", "business.endXj");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 添加报价
     *
     * @param xjid
     * @param xjlb
     * @param mj
     * @param dj
     * @param zj
     * @param fjbs
     * @param bz
     */
    public void addbj(int xjid, int xjlb, Float mj, Float dj, Float zj, int fjbs, String bz) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xjid", xjid);
            jsonObjArr.put("mj", mj);
            jsonObjArr.put("xjlb", xjlb);
            jsonObjArr.put("dj", dj);
            jsonObjArr.put("zj", zj);
            jsonObjArr.put("fjbs", fjbs);
            jsonObjArr.put("bz", bz);
            //构造cmd
            jsonObj.put("cmd", "business.addbj");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 结束询价
     *
     * @param userid
     * @param xjid
     */

    public void closeXJ(int userid, int xjid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", userid);
            jsonObjArr.put("xjid", xjid);

            //构造cmd
            jsonObj.put("cmd", "business.closeXj");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // FIXME: 2017/1/18  招标

    /***
     * 添加招标
     */

    public void AddZB(ZBBean zb) {


        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zblb", zb.getZblb());
            jsonObjArr.put("szcs", zb.getSzcs());
            jsonObjArr.put("bdw", zb.getBdw());
            jsonObjArr.put("kcbmb", zb.getKcbmb());
            jsonObjArr.put("lxr", zb.getLxr());
            jsonObjArr.put("lxdh", zb.getLxdh());
            jsonObjArr.put("dkyh", zb.getDkyh());
            jsonObjArr.put("pgjg", zb.getPgjg());
            jsonObjArr.put("xxsm", zb.getXxsm());
            jsonObjArr.put("zbyxsj", zb.getZbyxsj());
            jsonObjArr.put("bgjzsj", zb.getBgjzsj());
            jsonObjArr.put("bc", zb.getBc());
            jsonObjArr.put("zhlx", zb.getZhlx());
            jsonObjArr.put("zbdx", zb.getZbdx());
            jsonObjArr.put("nbzb", zb.getNbzb());
            jsonObjArr.put("form_id", zb.getZblb());
            //构造cmd
            jsonObj.put("cmd", "business.addZb");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取招标列表
     */

    public void getZbList(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("page", page);
            //构造cmd
            jsonObj.put("cmd", "business.getZbList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取投标列表
     */

    public void getMytbList(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("page", page);
            //构造cmd
            jsonObj.put("cmd", "business.getMytbList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 添加投标
     */

    public void addtb(TBBean tbBean) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", tbBean.getZbid());
            jsonObjArr.put("tbj", tbBean.getTbbj());
            jsonObjArr.put("tbsm", tbBean.getTbsm());
            jsonObjArr.put("fjbs", 1);
            //构造cmd
            jsonObj.put("cmd", "business.addtb");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 终止招标
     */

    public void endZb(int zbid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);
            //构造cmd
            jsonObj.put("cmd", "business.endZb");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取投标信息
     *
     * @param tbId
     */

    public void getTbInfo(int tbId) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("tbid", tbId);
            jsonObjArr.put("sign", this.sign);
            //构造cmd
            jsonObj.put("cmd", "business.getTbInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取我的招标列表
     *
     * @param page
     */

    public void getMyZbList(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("page", page);
            jsonObjArr.put("sign", this.sign);
            //构造cmd
            jsonObj.put("cmd", "business.getMyZbList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取招标详情
     *
     * @param zbid
     */

    public void getZbInfo(int zbid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);
            //构造cmd
            jsonObj.put("cmd", "business.getZbInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 现场签到
     *
     * @param zbid
     */

    public void xcSign(int zbid, Double jd, Double wd, String qddd) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);
            jsonObjArr.put("jd", jd);
            jsonObjArr.put("wd", jd);
            jsonObjArr.put("qddd", qddd);
            //构造cmd
            jsonObj.put("cmd", "business.xcSign");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 设置地区
     *
     * @param dqdm
     */

    public void setYwCity(int dqdm) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("dqdm", dqdm);
            //构造cmd
            jsonObj.put("cmd", "business.setYwCity");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取地区
     */

    public void getYwCity() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            //构造cmd
            jsonObj.put("cmd", "business.getYwCity");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * 提交查勘表
     *
     * @param zbid
     */

    public void submitKcjl(int zbid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);
            //构造cmd
            jsonObj.put("cmd", "business.submitKcjl");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取我的招标详情
     *
     * @param zbid
     */

    public void getMyZbInfo(int zbid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbid);
            //构造cmd
            jsonObj.put("cmd", "business.getMyZbInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取我的任务列表
     *
     * @param page
     */

    public void getMyTaskList(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("page", page);
            //构造cmd
            jsonObj.put("cmd", "business.getMyTaskList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取我的任务详情
     *
     * @param page
     */

    public void getMyTask(int page) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("page", page);
            //构造cmd
            jsonObj.put("cmd", "business.getMyTaskList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取位置
     */

    public void getWzList() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            //构造cmd
            jsonObj.put("cmd", "admin.getWzList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取勘查表信息
     *
     * @param zbId
     */

    public void getKcjlList(int zbId) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbId);
            //构造cmd
            jsonObj.put("cmd", "business.getKcjlList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 获取勘查表信息_to
     *
     * @param id
     */

    public void getKcjlList_to(int id) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("id", id);
            //构造cmd
            jsonObj.put("cmd", "business.getKcjlList_to");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 勘察工具之提交勘察表
     *
     * @param id
     */

    public void Submit_Ck(int id) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("id", id);
            //构造cmd
            jsonObj.put("cmd", "business.Submit_Ck");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 更新勘察表信息
     *
     * @param zbId
     */

    public void updateKcjl(int zbId, String sjxMc, String sjXz) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbId);
            jsonObjArr.put("sjxmc", sjxMc);
            jsonObjArr.put("sjxz", sjXz);

            //构造cmd
            jsonObj.put("cmd", "business.updateKcjl");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 更新勘察表信息
     *
     * @param zbId
     */

    public void updateKcjl_to(int zbId, String sjxMc, String sjXz) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbId);
            jsonObjArr.put("sjxmc", sjxMc);
            jsonObjArr.put("sjxz", sjXz);

            //构造cmd
            jsonObj.put("cmd", "business.updateKcjl_to");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 确认查勘报告
     *
     * @param zbId ysjg 1 确认 -1 退回
     *             zk 目前1
     */
    public void Confirm_kcbg(int zbId, int ysjg, String fkyj, double zk) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("zbid", zbId);
            jsonObjArr.put("ysjg", ysjg);
            jsonObjArr.put("fkyj", fkyj);
            jsonObjArr.put("zk", zk);

            //构造cmd
            jsonObj.put("cmd", "business.Confirm_kcbg");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /***
     * 获取企业内部员工
     */

    public void getCompanyMemberList(int userid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            //构造cmd
            jsonObj.put("cmd", "user.getCompanyMemberList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除图片
     */

    public void del_ftpimg(int img_Id) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("id", img_Id);
            //构造cmd
            jsonObj.put("cmd", "business.del_ftpimg");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取求职列表
     */

    public void getRcList() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            ;
            //构造cmd
            jsonObj.put("cmd", "business.getRcList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 是否在地图上显示当前我的位置信息
     *
     * @param isshow
     */
    public void isShow(int isshow) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("isshow", isshow);
            ;
            //构造cmd
            jsonObj.put("cmd", "user.isShow");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取招聘列表
     */

    public void getGwList() {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            ;
            //构造cmd
            jsonObj.put("cmd", "business.getGwList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取我的招聘列表
     */

    public void getMyGwList() {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            ;
            //构造cmd
            jsonObj.put("cmd", "business.getMyGwList");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除我对求职
     */

    public void delRc() {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            //构造cmd
            jsonObj.put("cmd", "business.delRc");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除我的招聘
     */

    public void delGw(int gwid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("gwid", gwid);

            //构造cmd
            jsonObj.put("cmd", "business.delGw");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 添加求职
     */
    public void addRc(String xm, String lxsj, String gw, String gzxz, String gzdd, String lxyx, String xl, String gzjy, String bz, String xcyq, String yyzg) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xm", xm);
            jsonObjArr.put("lxsj", lxsj);
            jsonObjArr.put("gw", gw);
            jsonObjArr.put("gzxz", gzxz);
            jsonObjArr.put("gzdd", gzdd);
            jsonObjArr.put("lxyx", lxyx);
            jsonObjArr.put("xl", xl);
            jsonObjArr.put("gzjy", gzjy);
            jsonObjArr.put("bz", bz);
            jsonObjArr.put("xcyq", xcyq);
            jsonObjArr.put("yyzg", yyzg);


            //构造cmd
            jsonObj.put("cmd", "business.addRc");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加求职
     */
    public void addGw(String gsmc, String lxdh, String gw, String gzxz, String gzdd, String zgyq, String bz, String qyyx, String gwyq, String xcbz) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("gsmc", gsmc);
            jsonObjArr.put("lxdh", lxdh);
            jsonObjArr.put("gw", gw);
            jsonObjArr.put("gzxz", gzxz);
            jsonObjArr.put("gzdd", gzdd);
            jsonObjArr.put("zgyq", zgyq);
            jsonObjArr.put("bz", bz);
            jsonObjArr.put("qyyx", qyyx);
            jsonObjArr.put("gwyq", gwyq);
            jsonObjArr.put("xcbz", xcbz);


            //构造cmd
            jsonObj.put("cmd", "business.addGw");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 岗位信息
     */
    public void getGwInfo(int gwid) {

        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {

            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("gwid", gwid);


            //构造cmd
            jsonObj.put("cmd", "business.getGwInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 求职要求
     */
    public void getRcInfo(int rcid) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("rcid", rcid);
            //构造cmd
            jsonObj.put("cmd", "business.getRcInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取公司详情
     */
    public void getComDetail(int company_id) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("company_id", company_id);
            //构造cmd
            jsonObj.put("cmd", "business.getComDetail");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // FIXME: 2017/3/30 支付模块

    /***
     * 调起支付宝支付
     */

    public void applyAliPay(double total_fee, int xjid) {


        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xjid", xjid);
            jsonObjArr.put("total_fee", total_fee);

            jsonObj.put("cmd", "user.applyLiXjPay");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 调起支付宝支付
     */

    public void applyLiZbPay(double total_fee, int tbid, int zbid) {


        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("tbid", tbid);
            jsonObjArr.put("zbid", zbid);
            jsonObjArr.put("total_fee", total_fee);

            jsonObj.put("cmd", "user.applyLiZbPay");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 在线文库
     */
    public void getDocuments() {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObj.put("cmd", "business.getDocuments");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 提现
     */
    public void ApplyLiTransfer(double fee, String re_user_name, String account) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("fee", fee);
            jsonObjArr.put("re_user_name", re_user_name);
            jsonObjArr.put("account", account);
            jsonObj.put("cmd", "user.ApplyLiTransfer");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发起微信支付
     *
     * @param total_fee
     * @param body
     */
    public void applyWxPay(Double total_fee, String body) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("total_fee", total_fee);
            jsonObjArr.put("body", body);
            jsonObjArr.put("qd", "APP");
            //构造cmd
            jsonObj.put("cmd", "user.applyWxPay");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结案
     *
     * @param xjid
     */
    public void closeXj(int xjid) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("xjid", xjid);

            //构造cmd
            jsonObj.put("cmd", "business.closeXj");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 勘察工具之添加勘察表
     */
    public void addCkb(String cklb, String ckdz, String mbbh, String bz) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObjArr.put("cklb", cklb);
            jsonObjArr.put("ckdz", ckdz);
            jsonObjArr.put("mbbh", mbbh);
            jsonObjArr.put("bz", bz);
            //构造cmd
            jsonObj.put("cmd", "business.addCkb");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 勘察工具之获取勘察信息
     */
    public void getCkbInfo(String kcid) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            jsonObjArr.put("id", kcid);

            //构造cmd
            jsonObj.put("cmd", "business.getCkbInfo");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 勘察工具之计税工具
     */

    public void computeTheDisposalOfTaxAndFee(HashMap value) {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);
            jsonObjArr.put("pgdj", value.get("pgdj"));
            jsonObjArr.put("pgzj", value.get("pgzj"));
            jsonObjArr.put("yj", value.get("yj"));
            jsonObjArr.put("zzlx", value.get("zzlx"));
            jsonObjArr.put("cslx", value.get("cslx"));
            jsonObjArr.put("zznx", value.get("zznx"));
            jsonObjArr.put("bxl", value.get("bxl"));

            //构造cmd
            jsonObj.put("cmd", "business.computeTheDisposalOfTaxAndFee");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        try {
            run(encryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getMessageInfo(String str)

    {

        JSONObject obj = null;
        String result = "";
        try {
            obj = new JSONObject(str);
            result = obj.getString("message").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public JSONObject getDatas(String msg)

    {

        JSONObject obj = null;
        JSONObject temp = null;
        try {
            obj = new JSONObject(msg);
            temp = (JSONObject) obj.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void Test(String url) {

        Request request2 = new Request.Builder()
                .url(url)
                .get().build();
        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                com.android.pgb.Utils.Log.sys("_backForData" + "eeer");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                String Data2 = response.body().string();
                com.android.pgb.Utils.Log.sys("_backForData" + Data2);
                if (_backForData != null)
                    _backForData.onMessage(Data2, "", 0);

            }
        });

    }


    private void SaveSignAndUserid(int userid, String sign) {
        HTTPSUtils.Sign = sign;
        HTTPSUtils.USERID = userid;
        this.sign = HTTPSUtils.Sign;
        this.userId = HTTPSUtils.USERID;

    }


// FIXME: 2017/4/21 websocket

    /**
     * websocket链接
     */
    public String Connect() {
        jsonObj = new JSONObject();//创建json格式的数据
        jsonObjArr = new JSONObject();
        try {
            //data中的数据
            jsonObjArr.put("userid", this.userId);
            jsonObjArr.put("sign", this.sign);

            //构造cmd
            jsonObj.put("cmd", "connect.Connect");//命令名称。
            jsonObj.put("data", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送加密请求协议的命令
        encryptStr = jsonObj.toString();  //mCrypt.encrypt(JSONUtils.mapToJSON(map).toString());
        return encryptStr;
    }


// FIXME: 2017/3/14  Run


    public void send(File files, Map<String, String> params) {

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        multipartBodyBuilder.addFormDataPart("file", files.getName(), RequestBody.create(MEDIA_TYPE_PNG, files));

        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        //        if (files != null) {
        //            for (File file : files) {
        //
        //            }
        //        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url("https://www.yiqiyun.org/apppgb/");// 添加URL地址
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                com.android.pgb.Utils.Log.e("网络连接失败");
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String Data = response.body().string();
                JSONObject obj;
                try {
                    obj = new JSONObject(Data);
                    String cmd = JSONUtils.getString(obj, "cmd").toString();
                    int code = JSONUtils.getInt(obj, "code", 0);
                    if (code == 1111) {
                        //当遇到111的时候并且密码帐号都不为空
                        if (!pwd.equals("") && !phone.equals("")) {
                            Log.e("进入到1111中了", obj.get("message") + "");
                            Login("", "", pwd, "", phone);
                        }

                    } else if (code == 9999) {
                        if (_backForData != null)
                            _backForData.onMessage("", "", 9999);
                    } else {
                        if (cmd.equals("user.Login") && code == 0) {
                            JSONObject object = (JSONObject) obj.get("data");
                            int userid = (int) object.get("userid");
                            String sign = object.get("sign").toString();
                            SaveSignAndUserid(userid, sign);

                        }
                        if (_backForData != null)
                            _backForData.onMessage(Data, cmd, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void WebSocket() {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder()
                .url("wss://www.yiqiyun.org/wspgb/").build();
        WebSocketCall webSocketCall = WebSocketCall.create(okHttpClient, request);
        webSocketCall.enqueue(new WebSocketListener() {

            @Override
            public void onOpen(WebSocket webSocket, Response response) {

//                try {
//                   // webSocket.sendMessage(RequestBody.create(TEXT, "你好"));
//                    System.out.println("MESSAGE: " + "成功连接");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(IOException e, Response response) {


            }

            @Override
            public void onMessage(ResponseBody message) throws IOException {


                String msg = message.string();
                JSONObject OBJ = JSONUtils.StringToJSON(msg);

                Log.e("Conker", "============" + msg);

                switch (JSONUtils.getString(OBJ, "cmd")) {
                    case "NewXjInfo":

                        _backForData.onMessage(msg, JSONUtils.getString(OBJ, "cmd"), JSONUtils.getInt(OBJ, "code", -1));

                        break;
                    case "NewZbInfo":

                        _backForData.onMessage(msg, JSONUtils.getString(OBJ, "cmd"), JSONUtils.getInt(OBJ, "code", -1));

                        break;
                    default:

                        break;

                }

                if (message.contentType() == TEXT) {
                    System.out.println("MESSAGE: " + msg);
                }
            }


            @Override
            public void onPong(Buffer payload) {

            }

            @Override
            public void onClose(int code, String reason) {
                System.out.println("MESSAGE: Code" + code);
            }
        });
    }

    public void run(String encryptStr) throws Exception {
        mCrypt = new MCrypt();

        Request request = new Request.Builder()
                .url("https://www.yiqiyun.org/apppgb/")
                .post(RequestBody.create(MediaType.parse("application/json"),
                        encryptStr
                )).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("错误", "onFailure===========" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("异常代码" + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                String Data = response.body().string();
                JSONObject obj;
                try {
                    obj = new JSONObject(Data);
                    String cmd = JSONUtils.getString(obj, "cmd");
                    //打印输出
                    com.android.pgb.Utils.Log.e("cmd=" + obj.get("cmd") + ";code=" + obj.get("code"));
                    int code = JSONUtils.getInt(obj, "code", 0);
                    if (code == 1111) {
                        Log.e("错误", "进入code======1111=====" + pwd + phone);
                        //当遇到111的时候并且密码帐号都不为空
                        if (!pwd.isEmpty() && !phone.isEmpty()) {
                            Log.e("错误", "进入code======Login=====");
                            Login("", "", pwd, "", phone);
                        }
                    } else if (code == 9999) {
                        if (_backForData != null)
                            _backForData.onMessage("", "", 9999);
                    } else {
                        if (cmd.equals("user.Login") && code == 0) {
                            JSONObject object = (JSONObject) obj.get("data");
                            int userid = (int) object.get("userid");
                            String sign = object.get("sign").toString();
                            SaveSignAndUserid(userid, sign);
                        }
                        if (_backForData != null)
                            _backForData.onMessage(Data, cmd, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface CallBackForData {

        void onMessage(String str, String cmd, int code);
    }

}
