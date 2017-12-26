package com.android.pinggubang.BroadCast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kanghui on 2017/1/6.
 */

public class Parse {

    public static JSONObject parseForObj(String msg)
            throws JSONException
    {
        JSONObject object = new JSONObject(msg);
          return object;

    }



}
