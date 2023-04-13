package com.uyou.copenaccount.bean;

/**
 * Created by zdd on 2019/11/11.
 * <p>
 * Description:
 */
public class WebankFaceBean {

    private String faceId;
    private String orderId;
    private String appId;
    private String appVersion;
    private String nonce;
    private String userId;
    private String sign;
    private String keyLicence;
    private String openId;
    private String name;
    private String cardId;
    private String notes;
    private int valve;
    private String hint;//活体检测前提示语
    private String reqOrderId;
    private String validity;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getValve() {
        return valve;
    }

    public void setValve(int valve) {
        this.valve = valve;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getReqOrderId() {
        return reqOrderId;
    }

    public void setReqOrderId(String reqOrderId) {
        this.reqOrderId = reqOrderId;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getName() {
        return name;
    }

    public String getCardId() {
        return cardId;
    }

    public String getFaceId() {
        return faceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getNonce() {
        return nonce;
    }

    public String getUserId() {
        return userId;
    }

    public String getSign() {
        return sign;
    }

    public String getKeyLicence() {
        return keyLicence;
    }

    public String getOpenId() {
        return openId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setKeyLicence(String keyLicence) {
        this.keyLicence = keyLicence;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
