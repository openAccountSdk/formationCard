package com.uyou.copenaccount.model;


import com.uyou.copenaccount.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by zdd on 2019/6/17.
 * <p>
 * Description:
 */
public class OpenTypeModel implements Serializable {

    private String name;
    /**
     * 开卡方式
     * 1.OTG
     * 2.NFC
     * 3.蓝牙
     * 4.云识别
     * 5.本地扫描
     */
    private int code;

    public OpenTypeModel(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return StringUtils.checkNull(name);
    }

    public int getCode() {
        return code;
    }
}
