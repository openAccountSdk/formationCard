package com.uyou.copenaccount.model;


import com.uyou.copenaccount.base.BaseServiceModel;

import java.io.Serializable;

/**
 * Created by zdd on 2019/4/22.
 * <p>
 * Description:
 */
public class NavPhoneRechargeModel extends BaseServiceModel implements Serializable {

    private static final long serialVersionUID = -455907285304270722L;

    private String requestId;
    private String phoneNum;
    private String isPay;
    private boolean isOnLine;
    private String ICCID;
    private String payType;
    private String openId;
    private boolean isNotPreRecordCard;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRequestId() {
        return checkVal(requestId);
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPhoneNum() {
        return checkVal(phoneNum);
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getIsPay() {
        return checkVal(isPay);
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public boolean getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(boolean isOnLine) {
        this.isOnLine = isOnLine;
    }

    public String getICCID() {
        return checkVal(ICCID);
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getPayType() {
        return checkVal(payType);
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public boolean isNotPreRecordCard() {
        return isNotPreRecordCard;
    }

    public void setNotPreRecordCard(boolean notPreRecordCard) {
        isNotPreRecordCard = notPreRecordCard;
    }

    @Override
    public String toString() {
        return "NavPhoneRechargeModel{" +
                "requestId='" + requestId + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", isPay='" + isPay + '\'' +
                ", isOnLine=" + isOnLine +
                ", ICCID='" + ICCID + '\'' +
                ", payType='" + payType + '\'' +
                ", isNotPreRecordCard=" + isNotPreRecordCard +
                '}';
    }
}
