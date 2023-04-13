package com.uyou.copenaccount.utils.net;


import com.uyou.copenaccount.utils.ULogger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public class OkLogger implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        ULogger.w("UHttp", "┌────── Response ────────────────────────");
        ULogger.w("UHttp", "│ URL: " + request.url().toString());
        ULogger.w("UHttp", "│ Received in: " + duration + "ms");
        ULogger.w("UHttp", "│ body : " + content);
        ULogger.w("UHttp", "└───────────────────────────────────");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
