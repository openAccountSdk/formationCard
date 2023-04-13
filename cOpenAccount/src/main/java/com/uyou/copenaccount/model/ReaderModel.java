package com.uyou.copenaccount.model;


import com.uyou.copenaccount.base.BaseServiceModel;

import java.io.Serializable;

/**
 * Created by zdd on 2019/7/8.
 * <p>
 * Description:
 */
public class ReaderModel extends BaseServiceModel implements Serializable {
    private static final long serialVersionUID = 7123239922118349524L;

    /**
     * 请求码
     */
    private int requestCode;

    /**
     * 开卡方式
     * 1.OTG
     * 2.NFC
     * 3.蓝牙
     * 4.云识别
     * 5.本地扫描
     */
    private int typeCode;
    /**
     * 设备编号
     */
    private String deviceCode;
    /**
     * activity action
     */
    private String action;
    /**
     * 版本
     */
    private int version;
    /**
     * 下载地址
     */
    private String URL;
    /**
     * uri
     */
    private String URI;
    /**
     * 蓝牙地址
     */
    private String bluetoothAddress;
    /**
     * 蓝牙名称
     */
    private String blueName;

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public String getDeviceCode() {
        return checkVal(deviceCode);
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getAction() {
        return checkVal(action);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getURL() {
        return checkVal(URL);
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURI() {
        return checkVal(URI);
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getBluetoothAddress() {
        return checkVal(bluetoothAddress);
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getBlueName() {
        return blueName;
    }

    public void setBlueName(String blueName) {
        this.blueName = blueName;
    }
}
