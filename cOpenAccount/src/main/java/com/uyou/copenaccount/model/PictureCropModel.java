package com.uyou.copenaccount.model;

import java.io.Serializable;

/**
 * Created by zdd on 2019/8/23.
 * <p>
 * Description:
 */
public class PictureCropModel implements Serializable {
    private static final long serialVersionUID = 4203321153644317064L;

    private String path;
    private int requestCode;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
