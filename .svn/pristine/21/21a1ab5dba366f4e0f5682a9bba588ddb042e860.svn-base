package com.uyou.copenaccount.utils;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by zdd on 2020/1/15.
 * <p>
 * Description:
 */
public class FileUtils {

    public static void deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }
}
