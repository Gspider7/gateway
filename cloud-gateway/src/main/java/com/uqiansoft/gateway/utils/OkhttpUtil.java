package com.uqiansoft.gateway.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http请求客户端
 * @author xutao
 * @date 2018-12-06 16:10
 */
public class OkhttpUtil {

    public static final Integer TIME_OUT = 3000;

    public static String post(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                // ...;
            }
        } catch (IOException e) {
            // ...
        }

        return null;
    }
}
