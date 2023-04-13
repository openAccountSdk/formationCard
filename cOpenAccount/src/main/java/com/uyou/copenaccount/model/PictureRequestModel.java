package com.uyou.copenaccount.model;

import java.io.Serializable;

/**
 * Created by zdd on 2019/4/1.
 * <p>
 * Description:
 */
public class PictureRequestModel implements Serializable {
    private static final long serialVersionUID = 8017975870277133878L;

    /**
     * 普通弹框模式
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 直接调用相机
     */
    public static final int TYPE_CAMERA = 1;

    /**
     * 普通模式 - 弹窗选择, 相机模式 - 直接调用相机
     */
    private int requestType;
    /**
     * 是否使用自定义相机
     */
    private boolean useCustomCamera;
    /**
     * 1身份证正面, 2身份证反面, 3人证合一, 其他默认拍正方形图片
     */
    private int customStep;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getCustomStep() {
        return customStep;
    }

    public void setCustomStep(int customStep) {
        this.customStep = customStep;
    }

    public boolean isUseCustomCamera() {
        return useCustomCamera;
    }

    public void setUseCustomCamera(boolean useCustomCamera) {
        this.useCustomCamera = useCustomCamera;
    }

}
