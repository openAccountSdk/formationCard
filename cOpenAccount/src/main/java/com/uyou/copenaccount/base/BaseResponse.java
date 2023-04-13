package com.uyou.copenaccount.base;


import com.uyou.copenaccount.utils.StringUtils;

/**
 * Created by zdd on 2019/3/11.
 * <p>
 * Description: 请求相应model基类
 */
public class BaseResponse {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return StringUtils.checkNull(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
