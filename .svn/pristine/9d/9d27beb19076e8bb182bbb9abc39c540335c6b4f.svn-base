package com.uyou.copenaccount.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceContant;
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceVerifySdk;
import com.tencent.cloud.huiyansdkface.facelight.api.listeners.WbCloudFaceVerifyLoginListener;
import com.tencent.cloud.huiyansdkface.facelight.api.result.WbFaceError;
import com.tencent.cloud.huiyansdkface.facelight.process.FaceVerifyStatus;
import com.tencent.cloud.huiyansdkface.facelivesdk.BuildConfig;
import com.uyou.copenaccount.bean.WebankFaceBean;


/**
 * Created by zdd on 2020/1/13.
 * <p>
 * Description:
 */
public class WebankUtils {
    private static final String TAG = "WebankUtils";

    public static void initWebank(Context context, WebankFaceBean bean, final OnWeBankInitListener listener) {
        String faceId = bean.getFaceId();
        String orderId = bean.getOrderId();
        String appId = bean.getAppId();
        String appVersion = bean.getAppVersion();
        String nonce = bean.getNonce();
        String userId = bean.getUserId();
        String sign = bean.getSign();
        String keyLicence = bean.getKeyLicence();
        String openId = bean.getOpenId();

        ULogger.e("WebankUtils", "faceId: " + faceId +
                " , orderId: " + orderId +
                " , appId: " + appId +
                " , appVersion: " + appVersion +
                " , nonce : " + nonce +
                " , userId: " + userId +
                " , sign: " + sign +
                " , keyLicence: " + keyLicence);

        Bundle data = new Bundle();

        FaceVerifyStatus.Mode type = FaceVerifyStatus.Mode.GRADE;
        // 新版活体
        WbCloudFaceVerifySdk.InputData inputData = new WbCloudFaceVerifySdk.InputData(
                faceId,
                orderId,
                appId,
                appVersion,
                nonce,
                userId,
                sign,
                type, // 活体类别
                keyLicence);

        data.putSerializable(WbCloudFaceContant.INPUT_DATA, inputData);
        //是否展示刷脸成功页面，默认展示
        data.putBoolean(WbCloudFaceContant.SHOW_SUCCESS_PAGE, false);
        //是否展示刷脸失败页面，默认展示
        data.putBoolean(WbCloudFaceContant.SHOW_FAIL_PAGE, false);
        //颜色设置     默认选择黑色模式
        data.putString(WbCloudFaceContant.COLOR_MODE, WbCloudFaceContant.BLACK);
        //是否录制视频存证,默认录制
        data.putBoolean(WbCloudFaceContant.VIDEO_UPLOAD, true);
        //是否播放提示音，默认播放
        data.putBoolean(WbCloudFaceContant.PLAY_VOICE, true);
        //此处将设置人脸采集时的个性化提示语
        data.putString(WbCloudFaceContant.CUSTOMER_TIPS_LIVE, "活体检测中..");
        //此处将设置上传人脸时的个性化提示语
        data.putString(WbCloudFaceContant.CUSTOMER_TIPS_UPLOAD, "正在校验，请耐心等待");
        //设置合作方定制提示语的位置，默认为识别框的下方
        //识别框的下方：WbCloudFaceContant.CUSTOMER_TIPS_LOC_BOTTOM
        //识别框的上方：WbCloudFaceContant.CUSTOMER_TIPS_LOC_TOP
        data.putInt(WbCloudFaceContant.CUSTOMER_TIPS_LOC, WbCloudFaceContant.CUSTOMER_TIPS_LOC_BOTTOM);
        //设置选择的比对类型  默认为公安网纹图片对比
        //公安网纹图片比对 WbCloudFaceContant.ID_CRAD
        //自带比对源比对  WbCloudFaceContant.SRC_IMG
        //仅活体检测  WbCloudFaceContant.NONE
        //默认公安网纹图片比对
        data.putString(WbCloudFaceContant.COMPARE_TYPE, WbCloudFaceContant.ID_CARD);
        data.putBoolean(WbCloudFaceContant.IS_ENABLE_LOG, BuildConfig.DEBUG);
        //拉起刷脸页面
        //【特别注意】请使用activity context拉起sdk
        //【特别注意】请在主线程拉起sdk！
        WbCloudFaceVerifySdk.getInstance().initSdk(context, data, new WbCloudFaceVerifyLoginListener() {
            @Override
            public void onLoginSuccess() {
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onLoginFailed(WbFaceError error) {
                String message = "";
                if (error != null) {
                    ULogger.e(TAG, error.getCode() + ", " + error.getReason() + ", " + error.getDesc());
                    if (error.getDomain().equals(WbFaceError.WBFaceErrorDomainParams)) {
                        message = "传入参数有误！code:" + error.getCode();
                    } else {
                        message = "登录刷脸sdk失败！code=" + error.getCode();
                    }
                } else {
                    message = "登录刷脸sdk失败！SDK返回为空";
                }
                if (listener != null) {
                    listener.onFail(message);
                }
            }
        });
    }

    private static String getLocation(Context context) {
        String lgt = "lgt=xxx.xxx;lat=xxx.xxx";
        String lat = LocationUtils.getLat(context);
        String lng = LocationUtils.getLng(context);
        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)) {
            if (lat.length() > 9) {
                lat = lat.substring(0, 9);
            }
            if (lng.length() > 9) {
                lng = lng.substring(0, 9);
            }

            lgt = "lgt=" + lng + ";lat=" + lat;
        }
        return lgt;
    }

    public static void release() {
        WbCloudFaceVerifySdk.getInstance().release();
    }


    public interface OnWeBankInitListener {
        void onSuccess();

        void onFail(String message);
    }
}
