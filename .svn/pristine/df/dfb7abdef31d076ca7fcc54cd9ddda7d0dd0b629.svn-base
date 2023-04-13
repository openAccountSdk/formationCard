package com.uyou.copenaccount.model;


import com.uyou.copenaccount.base.BaseServiceModel;

import java.io.File;

/**
 * Created by zdd on 2019/3/28.
 * <p>
 * Description:
 */
public class UploadImageModel extends BaseServiceModel {
    private String url;
    private File file;
    private String code;
    private String loginNum;
    private String phone_num;
    // 1 预录入开户，2过户，3补换卡,4白卡，5 没有预入录的成卡（可选套餐开户），6 签名照片
    private String channel;
    private String open_id;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getCode() {
        return checkVal(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLoginNum() {
        return checkVal(loginNum);
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public String getPhone_num() {
        return checkVal(phone_num);
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getChannel() {
        return checkVal(channel);
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOpen_id() {
        return checkVal(open_id);
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getUrl() {
        return checkVal(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
