package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseServiceModel;
import com.uyou.copenaccount.base.BaseServiceResponse;

/**
 * Created by zdd on 2019/4/3.
 * <p>
 * Description:
 */
public class UploadImageServiceResponse extends BaseServiceResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseServiceModel {
        private String upload_code;

        public String getUpload_code() {
            return checkVal(upload_code);
        }

        public void setUpload_code(String upload_code) {
            this.upload_code = upload_code;
        }

    }
}
