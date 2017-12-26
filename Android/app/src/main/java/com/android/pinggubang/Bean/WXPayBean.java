package com.android.pinggubang.Bean;

import org.json.JSONObject;

/**
 * Created by wu on 2017/3/30.
 */

public class WXPayBean {
    public WXPayBean(JSONObject object) {

    }

    /**
     * appId : wx11a9721b16987d13
     * timeStamp : 1490857283
     * nonceStr : DmmtN8iCBFW9u3Di
     * package : prepay_id=wx2017033015032689e98ee2b20962626537
     * signType : MD5
     * paySign : EBAC27E33188C79EBD7256EF5907291B
     * code : 0
     * out_trade_no : KqpKC2rhyOuIPERCQA4bV3sNNaNTUF1i
     */

    private String appId;
    private int timeStamp;
    private String nonceStr;
    private String packageX;
    private String signType;
    private String paySign;
    private int code;
    private String out_trade_no;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
