package com.uqiansoft.gateway.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * http请求客户端
 * @author xutao
 * @date 2018-12-06 16:10
 */
public class OkhttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkhttpUtil.class);

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PATH = "PATH";
    public static final Integer TIME_OUT = 1000;

    public static String sendOkhttpRequest(String url, String json, String method, Map<String, String> headersMap) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS).build();

        // 构建headers
        Headers headers = Headers.of(headersMap);

        // 封装Request
        Request request = null;
        switch (method) {
            case POST:
                request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                        .build();
                break;
            case PATH:
                request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .patch(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                        .build();
                break;
            case GET:
                request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .get()
                        .build();
                break;
            default:
                break;
        }

        String result = null;
        if (request != null) {
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    result = response.body().string();
                }
            } catch (IOException e) {
                logger.error("okhttp请求失败，url: {}, body: {}, method: {}, headers: {}", url, json, method, JSON.toJSONString(headers), e);
            }
        }
        return result;
    }
}
