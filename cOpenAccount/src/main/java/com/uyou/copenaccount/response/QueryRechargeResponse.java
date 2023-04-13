package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.bean.RechargeBean;
import com.uyou.copenaccount.utils.StringUtils;

import java.util.List;

/**
 * Created by zdd on 2019/4/22.
 * <p>
 * Description:
 */
public class QueryRechargeResponse extends BaseResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String giftFromMoney;
        private String giftMoney;
        private String giftRule;
        private List<RechargeBean> rechargeAmountMoney;

        public String getGiftFromMoney() {
            return StringUtils.checkNull(giftFromMoney);
        }

        public void setGiftFromMoney(String giftFromMoney) {
            this.giftFromMoney = giftFromMoney;
        }

        public String getGiftMoney() {
            return StringUtils.checkNull(giftMoney);
        }

        public void setGiftMoney(String giftMoney) {
            this.giftMoney = giftMoney;
        }

        public String getGiftRule() {
            return StringUtils.checkNull(giftRule);
        }

        public void setGiftRule(String giftRule) {
            this.giftRule = giftRule;
        }

        public List<RechargeBean> getRechargeAmountMoney() {
            return rechargeAmountMoney;
        }

        public void setRechargeAmountMoney(List<RechargeBean> rechargeAmountMoney) {
            this.rechargeAmountMoney = rechargeAmountMoney;
        }

    }
}
