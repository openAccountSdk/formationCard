package com.uyou.copenaccount.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public class ULogger {

    private static boolean showLogger = false;

    public static void e(String tag, String message) {
        if (showLogger) {
            if (tag == null) {
                return;
            }
            if (TextUtils.isEmpty(message)) {
                return;
            }
            Log.e(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (showLogger) {
            if (tag == null) {
                return;
            }
            if (TextUtils.isEmpty(message)) {
                return;
            }
            Log.w(tag, message);
        }
    }

    public static void setLogger(boolean logger) {
        showLogger = logger;
    }

    public static boolean isShowLogger(){
        return showLogger;
    }
}
