package com.uyou.copenaccount.response;

import com.uyou.copenaccount.base.BaseServiceModel;
import com.uyou.copenaccount.base.BaseServiceResponse;

/**
 * Created by zdd on 2019/4/3.
 * <p>
 * Description:
 */
public class UploadHeadImageServiceResponse extends BaseServiceResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data extends BaseServiceModel {
        private String key;
        private String address;
        private String birthday;
        private String cardno;
        private String folk;
        private String issue_authority;
        private String name;
        private String result;
        private String sex;
        private String type;
        private String upload_code;
        private String valid_period;
        private String encryption;
        private boolean editSwitch;// true 打开，可编辑    false 关闭，不可编辑  注：测试环境打开可编辑开关


        public String getEncryption() {
            return checkVal(encryption);
        }

        public void setEncryption(String encryption) {
            this.encryption = encryption;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getFolk() {
            return folk;
        }

        public void setFolk(String folk) {
            this.folk = folk;
        }

        public String getIssue_authority() {
            return issue_authority;
        }

        public void setIssue_authority(String issue_authority) {
            this.issue_authority = issue_authority;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpload_code() {
            return upload_code;
        }

        public void setUpload_code(String upload_code) {
            this.upload_code = upload_code;
        }

        public String getValid_period() {
            return valid_period;
        }

        public void setValid_period(String valid_period) {
            this.valid_period = valid_period;
        }

        public boolean isEditSwitch() {
            return editSwitch;
        }

        @Override
        public String toString() {
            return "Data [address=" + address + ", birthday=" + birthday + ", cardno=" + cardno + ", folk=" + folk + ", issue_authority=" + issue_authority + ", name=" + name + ", result=" + result + ", sex=" + sex + ", type=" + type + ", upload_code=" + upload_code + ", valid_period="
                    + valid_period + "]";
        }
    }
}
