package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.bean.WebankFaceBean;

/**
 * Created by zdd on 2019/11/11.
 * <p>
 * Description:
 */
public class CheckPicResponse extends BaseResponse {
    private WebankFaceBean data;

    public WebankFaceBean getData() {
        return data;
    }
}
