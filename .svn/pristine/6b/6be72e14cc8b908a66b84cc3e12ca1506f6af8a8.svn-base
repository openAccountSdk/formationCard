package com.uyou.copenaccount.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by zdd on 2019/3/25.
 * <p>
 * Description:
 */
public class AppConfigs {
    public static final String C_TIME_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    private static SharedPreferences mSharedPreferences;

    public AppConfigs() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final String SP_NAME = "share_data";

    public static String getCurrentTimeStamp_yyyyMMddHHmmssSSS() {
        return new SimpleDateFormat(C_TIME_FORMAT_YYYYMMDDHHMMSSSSS, Locale.CHINA).format(new Date());
    }

    public static String getReqOrderId() {
        return getCurrentTimeStamp_yyyyMMddHHmmssSSS() + getRandomNum(5);
    }

    /**
     * 获取任意长度随机数
     *
     * @param num
     * @return
     */
    public static String getRandomNum(int num) {
        Random random = new Random();
        return String.format("%0" + num + "d", random.nextInt(10000));
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    public static void setStringSF(Context context, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        if (value == null) {
            value = "";
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        String strEncrypt = XORUtils.stringXOREncode(value, "ASD");
        mSharedPreferences.edit().putString(key, strEncrypt).apply();
    }

    public static void setStringSFCommit(Context context, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        if (value == null) {
            value = "";
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        String strEncrypt = XORUtils.stringXOREncode(value, "ASD");
        mSharedPreferences.edit().putString(key, strEncrypt).commit();
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    public static String getStringSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        String strEncrypt = mSharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(strEncrypt)) {
            return "";
        }
        String result = "";
        try {
            result = XORUtils.stringXORDecode(strEncrypt, "ASD");
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    public static String getStringSF(Context context, String key, String defaultVal) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        String strEncrypt = mSharedPreferences.getString(key, defaultVal);
        if (defaultVal != null && defaultVal.equals(strEncrypt)) {
            return defaultVal;
        }
        if (TextUtils.isEmpty(strEncrypt)) {
            return defaultVal;
        }
        String result = "";
        try {
            result = XORUtils.stringXORDecode(strEncrypt, "ASD");
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    public static void setIntegerSF(Context context, String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    public static int getIntegerSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        return mSharedPreferences.getInt(key, -1);
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    public static void setBooleanSF(Context context, String key, boolean value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    public static boolean getBooleanSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 清除某个内容
     */
    public static void removeSF(Context context, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        key = XORUtils.stringXOREncode(key, "KEY");
        mSharedPreferences.edit().remove(key).apply();
    }

    /**
     * 清除SharePreference
     */
    public static void clearSharePreference(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().clear().apply();
    }

    public static void showToast(Context context,String content){
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
