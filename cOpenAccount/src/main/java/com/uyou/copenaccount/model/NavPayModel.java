package com.uyou.copenaccount.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zdd on 2019/4/26.
 * <p>
 * Description:
 */
public class NavPayModel implements Serializable {
    private static final long serialVersionUID = 7310882495633585434L;

    /**
     * 补货缴费(从补货过来的缴费)
     */
    private List<String> replenish;
    /**
     * 库存查询缴费(从tab3过来的缴费)
     */
    private List<String> batchs;

    /**
     * 缴费方式
     */
    private String payType;

    /**
     * 手机号码
     */
    private String phone;
    /**
     * 充值金额code
     */
    private String priceCode;
    /**
     * 充值金额
     */
    private int priceVal;

    /**
     * iccid 存在就是线上开户
     */
    private String ICCID;

    /**
     * 开户支付请求id
     */
    private String openAccountRequestId;

    /**
     * 开户界面跳转(有值),如果单独进入充值界面(默认为1)
     */
    private String isPayed;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    private String openId;
    private String payAmount;//支付金额

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 是否为未预录入号码, 未预录入pay_type  类型传2
     */
    private boolean isNotPreRecordCard;


    public List<String> getReplenish() {
        return replenish;
    }

    public void setReplenish(List<String> replenish) {
        this.replenish = replenish;
    }

    public List<String> getBatchs() {
        return batchs;
    }

    public void setBatchs(List<String> batchs) {
        this.batchs = batchs;
    }

    public String getPayType() {
        return payType == null ? "0" : payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public int getPriceVal() {
        return priceVal;
    }

    public void setPriceVal(int priceVal) {
        this.priceVal = priceVal;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getOpenAccountRequestId() {
        return openAccountRequestId;
    }

    public void setOpenAccountRequestId(String openAccountRequestId) {
        this.openAccountRequestId = openAccountRequestId;
    }

    public String getIsPayed() {
        return isPayed == null ? "1" : isPayed;
    }

    public void setIsPayed(String isPayed) {
        this.isPayed = isPayed;
    }

    public boolean isNotPreRecordCard() {
        return isNotPreRecordCard;
    }

    public void setNotPreRecordCard(boolean notPreRecordCard) {
        isNotPreRecordCard = notPreRecordCard;
    }

    @Override
    public String toString() {
        return "NavPayModel{" +
                "replenish=" + replenish +
                ", batchs=" + batchs +
                ", payType='" + payType + '\'' +
                ", phone='" + phone + '\'' +
                ", priceCode='" + priceCode + '\'' +
                ", priceVal=" + priceVal +
                ", ICCID='" + ICCID + '\'' +
                ", openAccountRequestId='" + openAccountRequestId + '\'' +
                ", isPayed='" + isPayed + '\'' +
                ", isNotPreRecordCard=" + isNotPreRecordCard +
                '}';
    }
}
