package com.uyou.copenaccount.model;


import com.uyou.copenaccount.base.BaseServiceModel;

import java.io.Serializable;

/**
 * Created by zdd on 2019/4/1.
 * <p>
 * Description:
 */
public class PictureResultModel extends BaseServiceModel implements Serializable {
    private static final long serialVersionUID = 6143862306574125792L;

    private String path;

    public String getPath() {
        return checkVal(path);
    }

    public void setPath(String path) {
        this.path = path;
    }
}
