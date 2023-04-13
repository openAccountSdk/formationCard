package com.uyou.copenaccount.utils.net;

/**
 * Created by zdd on 2019/2/27.
 * <p>
 * Description: base api
 */
public interface Api {

    /**
     * 上传图片
     */
    String URL_UPLOAD_IMAGE = "notice/imgUpload";
    /**
     * 上传头像图片
     */
    String UPLOAD_HEAD_PIC = "out_line/upload_ym";

    String HEADER_JSON = "Content-Type: application/json; charset=utf-8";

    // 使用封装请求头
    String HEADER_REQUEST = "uyou_request";
    String HEADER_NORMAL = "uyou_normal_request";
    String HEADER_FILE = "uyou_file_request";
    // 凭据头使用规则 1:使用临时凭据, 0:使用空凭据, 2:当凭据为空时,使用临时凭据
    String HEADER_USE_TEMP_CREDENTIAL = "credential_temp";
    String HEADER_NORMAL_REQUEST = HEADER_REQUEST + ": " + HEADER_NORMAL;
    String HEADER_USE_TEMP_CREDENTIAL_REQUEST = HEADER_USE_TEMP_CREDENTIAL + ": 1";
    String HEADER_USE_EMPTY_CREDENTIAL_REQUEST = HEADER_USE_TEMP_CREDENTIAL + ": 0";
    String HEADER_USE_TEMP_CREDENTIAL_IF_CREDENTIAL_EMPTY_REQUEST = HEADER_USE_TEMP_CREDENTIAL + ": 2";
    String HEADER_FILE_REQUEST = HEADER_REQUEST + ": " + HEADER_FILE;

    /**
     * 获取人脸识别sdk参数接口
     */
    String FACE_VERIFY = "out_line/activationSDK";
    /**
     * 上报活体失败
     */
    String FACE_VERIFY_FAIL = "out_line/activationSDK_fail";


}
