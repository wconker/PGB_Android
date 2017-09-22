package com.android.pgb.Utils.Net;

import com.android.pgb.Utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

/**
 * Created by wu on 2017/4/21.
 */

public class WebSocket implements WebSocketListener {

    @Override
    public void onOpen(okhttp3.ws.WebSocket webSocket, Response response) {


    }

    @Override
    public void onFailure(IOException e, Response response) {

    }

    @Override
    public void onMessage(ResponseBody message) throws IOException {

    }

    @Override
    public void onPong(Buffer payload) {

    }

    @Override
    public void onClose(int code, String reason) {

    }
}