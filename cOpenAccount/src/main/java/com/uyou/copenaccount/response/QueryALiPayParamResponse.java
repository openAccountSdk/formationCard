package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;

/**
 * Created by zdd on 2019/5/12.
 * <p>
 * Description:
 */
public class QueryALiPayParamResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PaymentSubmitApiBen [data=" + data + "]";
    }

    public static class Data {
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "Data [result=" + result + "]";
        }

    }
}
