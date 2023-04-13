package com.uyou.copenaccount.utils.net;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public interface HttpRequest {

    /**
     * 网络请求开始
     *
     * @param threadId 请求编号
     */
    void onHttpStart(int threadId);

    /**
     * 网络请求结果
     *
     * @param threadId 请求编号
     * @param response 请求结果
     */
    void onHttpResponse(int threadId, String response);
    void onHttpResponse(int threadId, String response,String path);

    /**
     * 网络请求出错(本地错误)
     *
     * @param threadId 请求编号
     * @param error    错误信息
     */
    void onHttpError(int threadId, String error);

    /**
     * 网络请求结束
     *
     * @param threadId 请求编号
     */
    void onHttpFinished(int threadId);
}
