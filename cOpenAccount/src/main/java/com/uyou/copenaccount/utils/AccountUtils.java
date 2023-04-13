package com.uyou.copenaccount.utils;

import static com.uyou.copenaccount.base.UCConstants.SHARE_DISPLAY_NAME;

import android.content.Context;


/**
 * Created by zdd on 2019/5/27.
 * <p>
 * Description:
 */
public class AccountUtils {



    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName(Context context) {
        return getConfigStr(context, SHARE_DISPLAY_NAME);
    }





    private static String getConfigStr(Context context, String key) {
        if (context == null) {
            return "";
        }
        return AppConfigs.getStringSF(context, key);
    }
}
