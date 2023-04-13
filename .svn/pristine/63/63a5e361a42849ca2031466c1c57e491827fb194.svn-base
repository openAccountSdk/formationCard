package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;

/**
 * Created by zdd on 2019/5/12.
 * <p>
 * Description:
 */
public class QueryWeChatPayParamResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WeiXinPaymentBen [data=" + data + "]";
    }

    public static class Data {
        private String partnerId;
        private String prepayId;
        private String nonceStr;
        private String timeStamp;
        private String package_;
        private String sign;
        private String mwebUrl;

        public String getMwebUrl() {
            return mwebUrl;
        }

        public void setMwebUrl(String mwebUrl) {
            this.mwebUrl = mwebUrl;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getPackage_() {
            return package_;
        }

        public void setPackage_(String package_) {
            this.package_ = package_;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "Data [partnerId=" + partnerId + ", prepayId=" + prepayId + ", nonceStr=" + nonceStr + ", timeStamp=" + timeStamp + ", package_=" + package_ + ", sign=" + sign + "]";
        }

    }
}
