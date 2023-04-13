package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;

import java.util.List;

/**
 * Created by zdd on 2019/7/12.
 * <p>
 * Description:
 */
public class GetAgreementResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public static class Data {
        public List<ProtocolBean> agreementList; //协议列表


    }

    public static class ProtocolBean {
        public String name;//协议名称
        public String url;//协议地址
        public boolean isSelect; //是否选择
        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
