package com.uyou.copenaccount.model;

import java.io.Serializable;

/**
 * Created by zdd on 2019/7/10.
 * <p>
 * Description:
 */
public class OpenAccountAction implements Serializable {
    private static final long serialVersionUID = 3800884322728090274L;
    //
    public OpenAccountActionModel openAccountModel;
    public String isWiteList;
    public int Select_Shibie_FangShi;
    public String mobileNum;
    // 按钮展示文字
    public String showText;
    // 相机参数
    public String discern;
    // 是否成卡
    public boolean noIsOnline;
    // 是否白卡
    public boolean isOnline;
    // 是否预留卡 ---- 5.3版本废弃
    public boolean isReserved;
    // 活体阈值
    public int faceScoreLimit;
    // 活体页面提示
    public String faceTip;


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String remark;
    public String userType;// 1是企业用户 2是普通用户
    // 是否是未预录入成卡
    public boolean isNotPreRecordCard;
    // 入网协议
//    public String agreementUrl;
//    public String commitmentUrl;
//    public String premiumNumberUrl;//优质号码服务协议
    /**
     * 需要上报错误
     */
    public boolean needUploadError;

    @Override
    public String toString() {
        return "OpenAccountAction{" +
                "openAccountModel=" + openAccountModel +
                ", isWiteList='" + isWiteList + '\'' +
                ", Select_Shibie_FangShi=" + Select_Shibie_FangShi +
                ", mobileNum='" + mobileNum + '\'' +
                ", showText='" + showText + '\'' +
                ", discern='" + discern + '\'' +
                ", noIsOnline=" + noIsOnline +
                ", isOnline=" + isOnline +
                ", isReserved=" + isReserved +
                ", faceScoreLimit=" + faceScoreLimit +
                ", faceTip='" + faceTip + '\'' +
                ", isNotPreRecordCard=" + isNotPreRecordCard +
                ", needUploadError=" + needUploadError +
                '}';
    }
}
