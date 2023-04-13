package com.uyou.copenaccount.utils.net;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public interface HttpRequestProgress extends HttpRequest {
    void onHttpProgress(int threadId, long write, long total);
}
