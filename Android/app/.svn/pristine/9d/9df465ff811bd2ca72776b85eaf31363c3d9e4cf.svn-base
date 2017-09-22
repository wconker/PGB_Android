package com.android.pgb.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2016/6/15.
 */
public class PreRequestClass {
    private final OkHttpClient client = new OkHttpClient();
    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/1") // This URL is served with a 1 second delay.
                .build();


        // Copy to customize OkHttp for this request.
        OkHttpClient copy = client.newBuilder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        copy.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Response 1 succeeded: " + response);
            }
        });


        OkHttpClient copy2 = client.newBuilder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .build();

        copy2.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Response 2 succeeded: " + response);
            }
        });
    }
}
