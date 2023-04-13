package com.uyou.copenaccount.utils;

import static com.uyou.copenaccount.base.UCConstants.ACTION_PATH;

import android.content.Context;
import android.content.Intent;

import com.uyou.copenaccount.ui.OpenAccountActivity;

public class COpenAccountSdk {

    private static COpenAccountSdk cOpenAccountSdk;

    private COpenAccountSdk() {
    }


    public static COpenAccountSdk getInstance() {
        if (cOpenAccountSdk == null) {
            synchronized (COpenAccountSdk.class) {
                if (cOpenAccountSdk == null) {
                    cOpenAccountSdk = new COpenAccountSdk();
                }
            }
        }
        return cOpenAccountSdk;
    }


    /**
     * 进入成卡开户流程
     *
     * @param context
     * @param path    开户完成后所要跳到下一个页面的包名+类名
     */
    public void start(Context context, String path) {
        AppConfigs.setStringSF(context, ACTION_PATH, path);
        Intent intent = new Intent(context, OpenAccountActivity.class);
        context.startActivity(intent);
    }
}
