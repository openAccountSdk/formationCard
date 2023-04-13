package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.utils.StringUtils;

/**
 * Created by zdd on 2019/7/12.
 * <p>
 * Description:
 */
public class OpenAccountResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OpenAccountBen [data=" + data.toString() + "]";
    }

    public static class Data {
        private String is_pay;
        private String request_id;
        private String ICCID;

        public String getOpenId() {
            return openId;
        }

        private String openId;

        public String getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getRequest_id() {
            return StringUtils.checkNull(request_id);
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public String getICCID() {
            return ICCID;
        }

        public void setICCID(String ICCID) {
            this.ICCID = ICCID;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "is_pay='" + is_pay + '\'' +
                    ", request_id='" + request_id + '\'' +
                    ", ICCID='" + ICCID + '\'' +
                    '}';
        }

    }

}
