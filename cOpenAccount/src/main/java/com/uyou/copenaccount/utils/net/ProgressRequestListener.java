package com.uyou.copenaccount.utils.net;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public interface ProgressRequestListener {

    void onRequestProgress(long write, long total);
}
