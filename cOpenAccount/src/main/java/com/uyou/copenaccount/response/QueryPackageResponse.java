package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.bean.CommonPackageBean;
import com.uyou.copenaccount.utils.StringUtils;

import java.util.List;

/**
 * Created by zdd on 2019/5/27.
 * <p>
 * Description:
 */
public class QueryPackageResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        private String remark;
        private String cardType; //1成卡 2可选套餐
        private String expenseDescription; //赠费说明
        private String gearDes; //低消说明

        public String getGearDes() {
            return gearDes;
        }

        private List<CommonPackageBean> packages;

        public String getCardType() {
            return cardType;
        }

        public String getRemark() {
            return StringUtils.checkNull(remark);
        }

        public String getExpenseDescription() {
            return expenseDescription;
        }

        public void setExpenseDescription(String expenseDescription) {
            this.expenseDescription = expenseDescription;
        }

        public List<CommonPackageBean> getPackages() {
            return packages;
        }
    }
}
