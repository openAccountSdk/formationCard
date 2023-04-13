package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;
import com.uyou.copenaccount.bean.CommonPackageBean;
import com.uyou.copenaccount.bean.ProductBean;
import com.uyou.copenaccount.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zdd on 2019/5/27.
 * <p>
 * Description:
 */
public class CheckNumberResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<ProductBean> productNameList;
        private String numSection;
        private String ERPID;
        private String phonenumType;

        public List<ProductBean> getProductNameList() {
            return productNameList;
        }

        public String getNumSection() {
            return numSection;
        }

        public String getERPID() {
            return ERPID;
        }

        public String getPhonenumType() {
            return phonenumType;
        }

        private String productId;
        private String productName;
        private String submit_context;
        private ArrayList<OpenType> open_type;
        private String discern;

        private String remark;
        private String cardType; //1成卡 2可选套餐
        private String expenseDescription; //赠费说明
        private String gearDes; //低消说明
        private String userType; //用户类型

        public String getUserType() {
            return userType;
        }

        public String getGearDes() {
            return gearDes;
        }

        private List<CommonPackageBean> packages;

        public String getCardType() {
            return cardType;
        }

        public String getExpenseDescription() {
            return expenseDescription;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getSubmit_context() {
            return StringUtils.checkNull(submit_context);
        }

        public ArrayList<OpenType> getOpen_type() {
            return open_type;
        }

        public String getDiscern() {
            return StringUtils.checkNull(discern);
        }

        public String getRemark() {
            return StringUtils.checkNull(remark);
        }

        public List<CommonPackageBean> getPackages() {
            return packages;
        }

        public static class OpenType {
            private String name;
            private int code;

            public OpenType() {

            }

            public OpenType(String name, int code) {
                this.name = name;
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            @Override
            public String toString() {
                return "OpenType [name=" + name + ", code=" + code + "]";
            }

        }
    }
}
