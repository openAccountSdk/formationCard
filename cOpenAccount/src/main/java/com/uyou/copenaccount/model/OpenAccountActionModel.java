package com.uyou.copenaccount.model;

import java.io.Serializable;

/**
 * Created by zdd on 2019/7/10.
 * <p>
 * Description:
 */
public class OpenAccountActionModel implements Serializable {
    private static final long serialVersionUID = -6090530881099587859L;

    /**
     * 套餐json
     */
    private String packageJson;

    private String faceOpenId;
    private String faceOrderId;
    private String imageCard;
    private String imageCardBack;
    private String imageCardAndSim;
    private String custId;
    private String reqOrderId;
    private String remark;//资费说明

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getReqOrderId() {
        return reqOrderId;
    }

    public void setReqOrderId(String reqOrderId) {
        this.reqOrderId = reqOrderId;
    }

    /**
     * 主套餐
     */
    private String productId;
    /**
     * 主套餐
     */
    private String productName;

    /**
     * 开卡手机号
     */
    private String phone_num;
    /**
     * 开卡校验号(iccid后三位)
     */
    private String uim_code;
    /**
     * 身份证名
     */
    private String name;
    /**
     * 身份证性别
     */
    private String sex;
    /**
     * 身份证民族
     */
    private String nation;
    /**
     * 身份证生日
     */
    private String birth;
    /**
     * 身份证地址
     */
    private String address;
    /**
     * 身份证号
     */
    private String cert_num;
    /**
     * 头像上传加密号
     */
    private String encryption;
    /**
     * 头像上传码
     */
    private String upload_code;
    /**
     * 开卡读取设备名
     */
    private String open_account_factory;
    /**
     * 开卡读取方式
     */
    private String open_account_type;
    /**
     * 外接设备码
     */
    private String device_num;

    /**
     * 亿数流水号(已弃用)
     */
    private String accept;
    /**
     * 秘钥
     */
    private String securekey;

    private String open_id;
    private String requestId;
    private String discern;
    private String phonenumType;

    /**
     * 定位信息
     */
    private String phone_in_province;
    private String phone_in_city;
    private String phone_in_detail;
    private String longitude;
    private String latitude;
    private String basePhotoDir;//活体截图目录
    private String baseVideoDir;//活体视频目录

    public void setBasePhotoDir(String basePhotoDir) {
        this.basePhotoDir = basePhotoDir;
    }

    public void setBaseVideoDir(String baseVideoDir) {
        this.baseVideoDir = baseVideoDir;
    }

    public String getBasePhotoDir() {
        return basePhotoDir;
    }

    public String getBaseVideoDir() {
        return baseVideoDir;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPackageJson() {
        return packageJson;
    }

    public void setPackageJson(String packageJson) {
        this.packageJson = packageJson;
    }

    public String getFaceOrderId() {
        return faceOrderId;
    }

    public void setFaceOrderId(String faceOrderId) {
        this.faceOrderId = faceOrderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public String getImageCardAndSim() {
        return imageCardAndSim;
    }

    public void setImageCardAndSim(String imageCardAndSim) {
        this.imageCardAndSim = imageCardAndSim;
    }

    public String getImageCard() {
        return imageCard;
    }

    public void setImageCard(String imageCard) {
        this.imageCard = imageCard;
    }

    public String getImageCardBack() {
        return imageCardBack;
    }

    public void setImageCardBack(String imageCardBack) {
        this.imageCardBack = imageCardBack;
    }

    public String getFaceOpenId() {
        return faceOpenId;
    }

    public void setFaceOpenId(String faceOpenId) {
        this.faceOpenId = faceOpenId;
    }

    public String getPhone_in_province() {
        return phone_in_province;
    }

    public void setPhone_in_province(String phone_in_province) {
        this.phone_in_province = phone_in_province;
    }

    public String getPhone_in_city() {
        return phone_in_city;
    }

    public void setPhone_in_city(String phone_in_city) {
        this.phone_in_city = phone_in_city;
    }

    public String getPhone_in_detail() {
        return phone_in_detail;
    }

    public void setPhone_in_detail(String phone_in_detail) {
        this.phone_in_detail = phone_in_detail;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getUim_code() {
        return uim_code;
    }

    public void setUim_code(String uim_code) {
        this.uim_code = uim_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCert_num() {
        return cert_num;
    }

    public void setCert_num(String cert_num) {
        this.cert_num = cert_num;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getUpload_code() {
        return upload_code;
    }

    public void setUpload_code(String upload_code) {
        this.upload_code = upload_code;
    }

    public String getOpen_account_factory() {
        return open_account_factory;
    }

    public void setOpen_account_factory(String open_account_factory) {
        this.open_account_factory = open_account_factory;
    }

    public String getOpen_account_type() {
        return open_account_type;
    }

    public void setOpen_account_type(String open_account_type) {
        this.open_account_type = open_account_type;
    }

    public String getDevice_num() {
        return device_num;
    }

    public void setDevice_num(String device_num) {
        this.device_num = device_num;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getSecurekey() {
        return securekey;
    }

    public void setSecurekey(String securekey) {
        this.securekey = securekey;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDiscern() {
        return discern;
    }

    public void setDiscern(String discern) {
        this.discern = discern;
    }

    public String getPhonenumType() {
        return phonenumType;
    }

    public void setPhonenumType(String phonenumType) {
        this.phonenumType = phonenumType;
    }

    @Override
    public String toString() {
        return "OpenAccountActionModel{" +
                "packageJson='" + packageJson + '\'' +
                "faceOpenId='" + faceOpenId + '\'' +
                ", imageCard='" + imageCard + '\'' +
                ", imageCardBack='" + imageCardBack + '\'' +
                ", productId='" + productId + '\'' +
                ", open_id='" + open_id + '\'' +
                ", requestId='" + requestId + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", uim_code='" + uim_code + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", cert_num='" + cert_num + '\'' +
                ", encryption='" + encryption + '\'' +
                ", upload_code='" + upload_code + '\'' +
                ", open_account_factory='" + open_account_factory + '\'' +
                ", open_account_type='" + open_account_type + '\'' +
                ", device_num='" + device_num + '\'' +
                ", accept='" + accept + '\'' +
                ", securekey='" + securekey + '\'' +
                ", discern='" + discern + '\'' +
                ", phonenumType='" + phonenumType + '\'' +
                ", phone_in_province='" + phone_in_province + '\'' +
                ", phone_in_city='" + phone_in_city + '\'' +
                ", phone_in_detail='" + phone_in_detail + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", custId='" + custId + '\'' +
                '}';
    }
}
