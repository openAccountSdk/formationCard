package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.utils.StringUtils;

/**
 * Created by zdd on 2019/4/29.
 * <p>
 * Description:
 */
public class QueryPhonePayInfoResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetRechargeBen [data=" + data + "]";
    }

    public static class Data {
        public String type;
        public String pay_value;
        public String my_integral;
        public String discount;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getMy_integral() {
            return StringUtils.checkNull(my_integral);
        }

        public void setMy_integral(String my_integral) {
            this.my_integral = my_integral;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPay_value() {
            return pay_value;
        }

        public void setPay_value(String pay_value) {
            this.pay_value = pay_value;
        }

        @Override
        public String toString() {
            return "Data [type=" + type + ", pay_value=" + pay_value + ", my_integral=" + my_integral + "]";
        }

    }
}
