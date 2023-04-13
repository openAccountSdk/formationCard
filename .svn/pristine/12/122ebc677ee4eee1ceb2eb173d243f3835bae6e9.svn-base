package com.uyou.copenaccount.utils.net;

import static com.uyou.copenaccount.base.UCConstants.APP_DOMAIN;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR;
import static com.uyou.copenaccount.base.UCConstants.NET_NO_NET;
import static com.uyou.copenaccount.base.UCConstants.NET_PARAM_ERROR;
import static com.uyou.copenaccount.base.UCConstants.NET_URL_EMPTY;
import static com.uyou.copenaccount.base.UCConstants.SHARE_CREDENTIAL;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.uyou.copenaccount.model.UploadImageModel;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.DeviceIdUtils;
import com.uyou.copenaccount.utils.LocationUtils;
import com.uyou.copenaccount.utils.MD5Utils;
import com.uyou.copenaccount.utils.NetUtils;
import com.uyou.copenaccount.utils.PhoneUtils;
import com.uyou.copenaccount.utils.RSAUtils;
import com.uyou.copenaccount.utils.RegexUtils;
import com.uyou.copenaccount.utils.ULogger;
import com.uyou.copenaccount.utils.net.tool.Sign;
import com.uyou.copenaccount.utils.net.tool.StringUtil;
import com.uyou.copenaccount.utils.net.tool.TransactionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public class UYouHttpClient {

    private static boolean isDebug = true;

    private static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCR9rs0NHnn2BCQZBCGBoEYfvrs88VLGwV/3+qAOnlyBB8YxyjCAn0AGpaJK5UfHAQyyx6HWUJWqKG6QOFR78bdhUBmQNDPUTQTsHolwC9C3WdHU+SYazSfNDyHFukn/zhWIDNL8tzX2+6BItJ6YsoIv9XlIFeguZLC1ysZGxmOTQIDAQAB";

    private OkHttpClient mOkHttpClient;
    private static UYouHttpClient mUYouClient;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public static UYouHttpClient getInstance() {
        if (mUYouClient == null) {
            synchronized (UYouHttpClient.class) {
                if (mUYouClient == null) {
                    mUYouClient = new UYouHttpClient();
                }
            }
        }
        return mUYouClient;
    }

    private UYouHttpClient() {
    }

    /**
     * 设置其他okHttpClient
     *
     * @param okHttpClient okHttpClient
     */
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    public void uploadImage(UploadImageModel model, int threadId, HttpRequest listener) {

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        MultipartBody.Part part = MultipartBody.Part.createFormData(
                "file",
                model.getFile().getName(),
                RequestBody.create(MediaType.parse("application/octet-stream"), model.getFile())
        );
        bodyBuilder.addPart(part);

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if (!TextUtils.isEmpty(model.getChannel())) {
            bodyBuilder.addFormDataPart("channel", model.getChannel());
            builder.append("\"channel\":");
            builder.append(model.getChannel());
        }
        if (!TextUtils.isEmpty(model.getCode())) {
            bodyBuilder.addFormDataPart("code", model.getCode());
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"code\":");
            builder.append(model.getCode());
        }
        if (!TextUtils.isEmpty(model.getLoginNum())) {
            bodyBuilder.addFormDataPart("loginNum", model.getLoginNum());
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"loginNum\":");
            builder.append(model.getLoginNum());
        }
        if (!TextUtils.isEmpty(model.getOpen_id())) {
            bodyBuilder.addFormDataPart("open_id", model.getOpen_id());
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"open_id\":");
            builder.append(model.getOpen_id());
        }
        if (!TextUtils.isEmpty(model.getPhone_num())) {
            bodyBuilder.addFormDataPart("phone_num", model.getPhone_num());
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"phone_num\":");
            builder.append(model.getPhone_num());
        }

        builder.append("}");
        // Post 请求
        Request request = new Request.Builder()
                .url(model.getUrl())
                .post(bodyBuilder.build())
                .build();

        // 执行异步请求
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requestFailure(call, model.getUrl(), listener, threadId, e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                // 上传完毕

                onSuccess(listener, threadId, result, model.getFile().getPath());
            }
        });


    }


    public void uploadHeadImage(String url, String loginName, String phoneNum, String cardName, String cardNumber, String cardEndDate, final String path, String open_account_type, int threadId, HttpRequest listener) {

        File file = null;
        if (!TextUtils.isEmpty(path)) {
            file = new File(path);
        }
        String jointNum = MD5Utils.MD5Encode("BAPP:" + loginName, "UTF-8");

        if (null == cardEndDate) {// 处理宇捷通 设备读卡
            cardEndDate = "20501111";
        } else {
            if (cardEndDate.contains("-")) {
                String[] str = cardEndDate.split("-");
                if (str[1].contains(".")) {
                    cardEndDate = str[1].replace(".", "");
                } else {
                    cardEndDate = str[1];
                }
            }
        }

        if (!RegexUtils.isNumeric(cardEndDate)) {
            cardEndDate = "20501111";
        }

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        if (file != null) {
            MultipartBody.Part part = MultipartBody.Part.createFormData(
                    "file",
                    file.getName(),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file)
            );
            bodyBuilder.addPart(part);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if (!TextUtils.isEmpty(cardNumber)) {
            bodyBuilder.addFormDataPart("cert_num", cardNumber);
            builder.append("\"cert_num\":");
            builder.append(cardNumber);
        }
        if (!TextUtils.isEmpty("1")) {
            bodyBuilder.addFormDataPart("from", "1");
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"from\":");
            builder.append("1");
        }
        if (!TextUtils.isEmpty(jointNum)) {
            bodyBuilder.addFormDataPart("jointNum", jointNum);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"jointNum\":");
            builder.append(jointNum);
        }
        if (!TextUtils.isEmpty(loginName)) {
            bodyBuilder.addFormDataPart("loginName", loginName);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"loginName\":");
            builder.append(loginName);
        }
        if (!TextUtils.isEmpty(cardName)) {
            bodyBuilder.addFormDataPart("name", cardName);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"name\":");
            builder.append(cardName);
        }
        if (!TextUtils.isEmpty(phoneNum)) {
            bodyBuilder.addFormDataPart("phone_num", phoneNum);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"phone_num\":");
            builder.append(phoneNum);
        }
        if (!TextUtils.isEmpty("0")) {
            bodyBuilder.addFormDataPart("type", "0");
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"type\":");
            builder.append("0");
        }
        if (!TextUtils.isEmpty(cardEndDate)) {
            bodyBuilder.addFormDataPart("validity", cardEndDate);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"validity\":");
            builder.append(cardEndDate);
        }
        if (!TextUtils.isEmpty(open_account_type)) {
            bodyBuilder.addFormDataPart("deviceName", open_account_type);
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"deviceName\":");
            builder.append(open_account_type);
        }
        builder.append("}");


        // Post 请求
        Request request = new Request.Builder()
                .url(APP_DOMAIN + url)
                .post(bodyBuilder.build())
                .build();
        getOkHttpClient();
        // 执行异步请求
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requestFailure(call, url, listener, threadId, e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                // 上传完毕
                onSuccess(listener, threadId, result);
            }
        });


    }

    /**
     * post表单提交
     *
     * @param context  上下文
     * @param tag      tag
     * @param threadId 请求编号
     * @param urls     请求url
     * @param params   请求参数
     * @param listener 监听回调
     */
    public void requestPost(final Context context, final Object tag, final int threadId, final String urls, final JSONObject params, final String version, final HttpRequest listener) {
        if (checkBeforeRequest(context, listener, threadId, urls)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request.Builder builder = new Request.Builder();

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    String url = urls;
                    JSONObject json = params;
                    if (json == null) {
                        json = new JSONObject();
                    }
                    JSONObject jsonWrap = new JSONObject();

                    String baseUrl = AppConfigs.getStringSF(context, "share_dev_white_sdk_url", APP_DOMAIN);
                    //Log.e("tag", baseUrl);
                    if (isDebug) {
                        url = baseUrl + (baseUrl.endsWith("/") ? "" : "/") + url;
                    } else {
                        url = baseUrl;
                    }

                    try {
                        JSONObject header = new JSONObject();
                        String imei = DeviceIdUtils.getIMEI(context);
                        String phone_number = PhoneUtils.getSimNumber(context);

                        header.put("imei", imei);
                        header.put("client_type", "android_" + android.os.Build.MODEL);
                        header.put("version", version);
                        header.put("screen_height", PhoneUtils.getScreenHeight(context));
                        header.put("screen_width", PhoneUtils.getScreenWidth(context));
                        header.put("sim_number", phone_number);
                        if (json.has("loginName")) {
                            header.put("loginName", json.getString("loginName"));
                        }
                        if (json.has("openId")) {
                            header.put("openId", json.getString("openId"));
                        }
                        header.put("latitude", LocationUtils.getLat(context));
                        header.put("longitude", LocationUtils.getLng(context));
                        header.put("country", LocationUtils.getCountry(context));
                        header.put("province", LocationUtils.getProvince(context));
                        header.put("city", LocationUtils.getCity(context));
                        header.put("district", LocationUtils.getDistrict(context));
                        header.put("detail", LocationUtils.getDetail(context));
                        header.put("towns", LocationUtils.getTownship(context));
                        header.put("ip", StringUtil.checkNull(PhoneUtils.getIPAddress(context)));

                        header.put("credential", AppConfigs.getStringSF(context, SHARE_CREDENTIAL));
                        // 空表示好U, 1表示APP
                        if ("com.aisidi.crm".equals(context.getPackageName())) {
                            header.put("source", "1");
                        } else if ("com.haoyouinternet.application".equals(context.getPackageName())) {
                            header.put("source", "2");
                        } else {
                            header.put("source", "3");
                        }


                        jsonWrap.put("header", header);
                        jsonWrap.put("body", json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ULogger.setLogger(true);
                    // 打印请求头
                    ULogger.w("UHttp", "┌────── Request ────────────────────────");
                    ULogger.w("UHttp", "│ URL: " + url);
                    String jsonString = jsonWrap.toString();
                    ULogger.w("UHttp", "│ body : " + jsonString);
                    ULogger.w("UHttp", "└───────────────────────────────────");


                    JSONObject data = new JSONObject();
                    try {
                        String encode = URLEncoder.encode(jsonString, "UTF-8");
                        String params = RSAUtils.RSAEncode(RSAUtils.getPublicKey(PUBLIC_KEY), encode);
                        data.put("request", params);
                    } catch (Exception e) {
                    }

                    JSONObject resultJson = new JSONObject();
                    if (!isDebug) {
                        JSONObject tcp = new JSONObject();
                        JSONObject svc = new JSONObject();
                        String result = "";
                        try {
                            // 构建tcp
                            String transid = TransactionUtil.getTransid();
                            String reqtime = StringUtil.getEightTimeStamp();
                            String custid = "asd_hu_002"; // 测试asd_u_001 正式
                            String servername = urls;
                            String sign = Sign.getSign(transid, reqtime, custid, servername);
                            tcp.put("transid", transid);
                            tcp.put("reqtime", reqtime);
                            tcp.put("remark", "");
                            tcp.put("custid", custid);
                            tcp.put("servername", servername);
                            tcp.put("sign", sign);
                            // 构建svc
                            svc.put("data", data);
                            resultJson.put("tcp", tcp);
                            resultJson.put("svc", svc);
                        } catch (Exception e) {

                        }

                        ULogger.w("UHttp", resultJson.toString());
                    }

                    RequestBody requestBody = null;
                    FormBody.Builder build = null;
                    if (isDebug) {
                        requestBody = RequestBody.create(JSON, data.toString());
                    } else {
                        build = new FormBody.Builder();
                        build.add("params", resultJson.toString());
                    }

                    try {
                        if (tag != null) {
                            builder.tag(tag);
                        }
                        Request request = null;
                        if (isDebug) {
                            request = builder.post(requestBody).url(url).build();
                        } else {
                            request = builder.post(build.build()).url(url).build();
                        }

                        final String finalUrl = url;
                        mOkHttpClient
                                .newCall(request)
                                .enqueue(
                                        new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                requestFailure(call, finalUrl, listener, threadId, e);
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                String result = response.body().string();
                                                onSuccess(listener, threadId, result);
                                            }
                                        }
                                );
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(listener, threadId, getErrorMsg(e));
                    }
                }
            }).start();
        }
    }

    /**
     * 请求前的检查
     *
     * @param context  上下文
     * @param listener 请求回调
     * @param threadId 请求编号
     * @param url      请求地址
     * @return 是否符合请求条件
     */
    private boolean checkBeforeRequest(Context context, HttpRequest listener, int threadId, String url) {
        if (isNetConnect(context, listener, threadId)) {
            if (checkUrl(listener, url, threadId)) {
                getOkHttpClient();
                return true;
            }
        }
        return false;
    }

    /**
     * 发出的请求出现了错误
     *
     * @param call     请求
     * @param url      请求地址
     * @param listener 请求回调
     * @param threadId 请求编号
     * @param e        异常信息
     */
    private void requestFailure(Call call, String url, HttpRequest listener, int threadId, Exception e) {
        if (call != null && call.isCanceled()) {
            // 取消的请求, 不做失败处理
            ULogger.e("UHttp", "请求取消 : " + url);
            onFinish(listener, threadId);
        } else {
            e.printStackTrace();
            onError(listener, threadId, getErrorMsg(e));
        }
    }

    private String getErrorMsg(Exception e) {
        String msg = NET_ERROR;
        if (e != null) {
            if (e instanceof ConnectException) {
                msg = "网络连接失败";
            } else if (e instanceof UnknownHostException) {
                msg = "网络不可用";
            } else if (e instanceof SocketTimeoutException) {
                msg = "请求网络超时";
            }
        }
        return msg;
    }

    /**
     * 取消所有请求
     *
     * @waring 此方法慎用, 因为可能存在 SDK 外部传进来的 OkHttpClient, 使用的话, 可能影响 SDK 外部的网络请求
     */
    public void cancelAll() {
        if (mOkHttpClient != null) {
            Dispatcher dispatcher = mOkHttpClient.dispatcher();
            synchronized (dispatcher) {
                for (Call call : dispatcher.queuedCalls()) {
                    call.cancel();
                }
                for (Call call : dispatcher.runningCalls()) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 取消请求
     *
     * @param object tag
     */
    public void cancel(Object object) {
        if (mOkHttpClient != null) {
            Dispatcher dispatcher = mOkHttpClient.dispatcher();
            synchronized (dispatcher) {
                // 取消队列中的
                for (Call call : dispatcher.queuedCalls()) {
                    if (object.equals(call.request().tag())) {
                        call.cancel();
                    }
                }
                // 取消正在请求的
                for (Call call : dispatcher.runningCalls()) {
                    if (object.equals(call.request().tag())) {
                        call.cancel();
                    }
                }
            }
        }
    }

    /**
     * 网络是否连接
     *
     * @param context  上下文
     * @param listener 请求回调
     * @param threadId 请求编号
     * @return 是否连接
     */
    private boolean isNetConnect(Context context, final HttpRequest listener, final int threadId) {
        if (listener != null) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onHttpStart(threadId);
                }
            });
        }
        if (context != null) {
            if (NetUtils.isConnected(context)) {
                return true;
            } else {
                onError(listener, threadId, NET_NO_NET);
                ULogger.e("UHttp", "没有连接网络, 取消请求");
                return false;
            }
        } else {
            onError(listener, threadId, NET_PARAM_ERROR);
            ULogger.e("UHttp", "context 为空");
            return false;
        }
    }

    /**
     * @param listener 请求回调
     * @param url      请求地址
     * @param threadId 请求编号
     * @return url是否可用
     */
    private boolean checkUrl(HttpRequest listener, String url, int threadId) {
        if (url == null || "".equals(null)) {
            onError(listener, threadId, NET_URL_EMPTY);
            ULogger.e("UHttp", "URL为空, 取消请求");
            return false;
        }
        return true;
    }

    private void onSuccess(final HttpRequest listener, final int threadId, final String result) {
        if (listener != null) {
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onHttpFinished(threadId);
                    listener.onHttpResponse(threadId, result);
                }
            }, 200);
        }
    }

    private void onSuccess(final HttpRequest listener, final int threadId, final String result, String path) {
        if (listener != null) {
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onHttpFinished(threadId);
                    listener.onHttpResponse(threadId, result, path);
                }
            }, 200);
        }
    }

    private void onFinish(final HttpRequest listener, final int threadId) {
        if (listener != null) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onHttpFinished(threadId);
                }
            });
        }
    }

    private void onError(final HttpRequest listener, final int threadId, final String msg) {
        if (listener != null) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onHttpFinished(threadId);
                    listener.onHttpError(threadId, msg);
                }
            });
        }
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = getUnsafeOkHttpClient();
        }
        return mOkHttpClient;
    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final X509TrustManager trustManager = new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new OkLogger());
            builder.connectTimeout(120, TimeUnit.SECONDS);
            builder.readTimeout(120, TimeUnit.SECONDS);
            builder.writeTimeout(120, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
