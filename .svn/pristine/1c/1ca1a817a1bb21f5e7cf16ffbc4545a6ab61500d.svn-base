package com.uyou.copenaccount.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;


/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public class PermissionUtils {


    public static boolean request(Activity activity, String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // 拒绝过一次之后，再次请求权限时的操作
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
            return false;
        } else {
            //已经获取到权限
            return true;
        }
    }

    public static boolean request(Activity activity, String[] permissions, int requestCode) {
        boolean hasPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            boolean hasPermission = ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (!hasPermission) {
                hasPermissions = false;
                break;
            }
        }
        if (hasPermissions) {
            return true;
        }
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
        return false;
    }

}
