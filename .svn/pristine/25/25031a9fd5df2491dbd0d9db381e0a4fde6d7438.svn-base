package com.uyou.copenaccount.ui;


import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.ACTION_ERROR_MSG;
import static com.uyou.copenaccount.base.UCConstants.ACTION_LIVE_RATE;
import static com.uyou.copenaccount.base.UCConstants.ACTION_SIMILARITY;
import static com.uyou.copenaccount.base.UCConstants.APP_DOMAIN;
import static com.uyou.copenaccount.base.UCConstants.CHECK_BE_BRANDED_AS;
import static com.uyou.copenaccount.base.UCConstants.CHECK_PIC;
import static com.uyou.copenaccount.base.UCConstants.CHECK_SIM_CARD;
import static com.uyou.copenaccount.base.UCConstants.CODE_ID_CARD_AND_CARD_BOARD;
import static com.uyou.copenaccount.base.UCConstants.CODE_PIC_CARD;
import static com.uyou.copenaccount.base.UCConstants.CODE_PIC_CARD_BACK;
import static com.uyou.copenaccount.base.UCConstants.CREATE_CUST_INFO_AND_CHECK_NUM;
import static com.uyou.copenaccount.base.UCConstants.ERROR_DATA;
import static com.uyou.copenaccount.base.UCConstants.NO_FINISHED_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.NO_INPUT_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.PRE_INPUT_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.TAG_DIALOG_LOADING;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_008;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_009;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_010;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_011;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_012;
import static com.uyou.copenaccount.utils.net.Api.FACE_VERIFY;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.Gson;
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceVerifySdk;
import com.tencent.cloud.huiyansdkface.facelight.api.result.WbFaceError;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.bean.WebankFaceBean;
import com.uyou.copenaccount.dialog.BaseConfirmDialog;
import com.uyou.copenaccount.model.OpenAccountAction;
import com.uyou.copenaccount.model.OpenAccountActionModel;
import com.uyou.copenaccount.model.PictureRequestModel;
import com.uyou.copenaccount.model.PictureResultModel;
import com.uyou.copenaccount.model.UploadImageModel;
import com.uyou.copenaccount.progress.ProgressInfo;
import com.uyou.copenaccount.progress.ProgressListener;
import com.uyou.copenaccount.progress.ProgressManager;
import com.uyou.copenaccount.response.CheckBeBrandedAsResponse;
import com.uyou.copenaccount.response.CheckPicResponse;
import com.uyou.copenaccount.response.CheckSimCardResponse;
import com.uyou.copenaccount.response.CreateCustInfoAndCheckNumResponse;
import com.uyou.copenaccount.response.FaceVerifyResponse;
import com.uyou.copenaccount.response.UploadImageServiceResponse;
import com.uyou.copenaccount.ui.pic.PictureSelectorActivity;
import com.uyou.copenaccount.utils.AccountUtils;
import com.uyou.copenaccount.utils.PhoneUtils;
import com.uyou.copenaccount.utils.StringUtils;
import com.uyou.copenaccount.utils.WebankUtils;
import com.uyou.copenaccount.utils.net.Api;
import com.uyou.copenaccount.utils.net.UYouHttpClient;
import com.uyou.copenaccount.xpopup.XPopup;
import com.uyou.copenaccount.xpopup.animator.EmptyAnimator;
import com.uyou.copenaccount.xpopup.core.BasePopupView;
import com.uyou.copenaccount.xpopup.core.LoadingDialog;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zdd on 2019/04/16
 * <p>
 * Description: 上传开户照片 activity
 */
public class UploadOpenAccountPicActivity extends UOpenBaseActivity {

    private static final String ACTION_CODE_CARD = "action_code_card";
    private static final String ACTION_CODE_CARD_BACK = "action_code_card_back";
    private static final String ACTION_CODE_CARD_SIM = "action_code_card_sim";

    @BindView(R2.id.bus_image_card)
    AppCompatImageView imageCard;
    @BindView(R2.id.bus_image_card_back)
    AppCompatImageView imageCardBack;
    @BindView(R2.id.id_card_and_sim)
    AppCompatImageView imageCardAndSim;

    @BindView(R2.id.bus_btn_commit)
    AppCompatTextView btnCommit;


    private OpenAccountAction mAction;
    private String mCodeCard, mCodeCardBack, mCodeIDCardAndMobilePhoneCard;
    private String mDiscern;
    private WebankFaceBean data;


    @Override
    public String getPageTitle() {
        return getStringRes(R.string.bus_title_upload_open_account);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.bus_activity_upload_open_account_pic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Object object = getIntent().getSerializableExtra(ACTION_DATA);

        if (object != null) {
            if (object instanceof OpenAccountAction) {
                mAction = (OpenAccountAction) object;

                mDiscern = mAction.discern;
            }
        }
    }

    /**
     * 选择身份证照
     */
    @OnClick(R2.id.bus_image_card)
    void toSelectCard() {
        navCamera(1, CODE_PIC_CARD);
    }

    /**
     * 选择身份证背面照
     */
    @OnClick(R2.id.bus_image_card_back)
    void toSelectBack() {
        if (checkUploadState(1)) {
            navCamera(2, CODE_PIC_CARD_BACK);
        }
    }

    /**
     * 选择身份证+sim卡
     */
    @OnClick(R2.id.id_card_and_sim)
    void toSelectCardAndSim() {
        if (checkUploadState(2)) {
            navCamera(5, CODE_ID_CARD_AND_CARD_BOARD);
        }
    }

    private void navCamera(int step, int requestCode) {
        PictureRequestModel model = new PictureRequestModel();
        if ("1".equals(mDiscern)) {
            model.setRequestType(PictureRequestModel.TYPE_CAMERA);
        }
        model.setUseCustomCamera(true);
        model.setCustomStep(step == 1 ? 4 : step);

        Intent intent = new Intent(this, PictureSelectorActivity.class);
        intent.putExtra(ACTION_DATA, model);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(0, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Object result = null;
            if (data != null) {
                result = data.getSerializableExtra(ACTION_DATA);
            }
            switch (requestCode) {
                case CODE_PIC_CARD_BACK:
                    dealPictureResult(requestCode, "0007", result);
                    break;
                case CODE_PIC_CARD:
                    dealPictureResult(requestCode, "0006", result);
                    break;
                case CODE_ID_CARD_AND_CARD_BOARD:
                    dealPictureResult(requestCode, "0013", result);
                    break;
            }
        }
    }

    /**
     * 拍照/选择处理结果
     *
     * @param request 请求码
     * @param code    标识码
     * @param result  结果
     */
    private void dealPictureResult(int request, String code, Object result) {
        if (result instanceof PictureResultModel) {
            PictureResultModel model = (PictureResultModel) result;
            String path = model.getPath();
            String loginName = AccountUtils.getUserName(getContext());
            if (mAction != null && mAction.openAccountModel != null) {
                try {
                    File file = new File(path);
                    if (file == null || !file.exists()) {
                        showMessage(getString(R.string.common_err_file_not_exists));
                        return;
                    }
                    UploadImageModel uploadImageModel = new UploadImageModel();
                    uploadImageModel.setUrl(rebuildUploadUrl());
                    uploadImageModel.setFile(file);
                    uploadImageModel.setLoginNum(loginName);
                    uploadImageModel.setCode(code);
                    uploadImageModel.setChannel(mAction.isOnline ? "4" : mAction.isNotPreRecordCard ? "5" : "1");
                    uploadImageModel.setPhone_num(mAction.openAccountModel.getPhone_num());
                    uploadImageModel.setOpen_id(mAction.openAccountModel.getOpen_id());
                    UYouHttpClient.getInstance().uploadImage(
                            uploadImageModel,
                            request,
                            UploadOpenAccountPicActivity.this
                    );

                } catch (Exception e) {
                    showToast("图片转换错误, 请重试");
                }
            }
        } else {
            showToast(getStringRes(R.string.common_err_file_not_exists));
        }
    }

    private String rebuildUploadUrl() {
        return ProgressManager.getInstance().addDiffRequestListenerOnSameUrl(APP_DOMAIN + Api.URL_UPLOAD_IMAGE, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                showProgressLoading(progressInfo.getPercent());
            }

            @Override
            public void onError(long id, Exception e) {
            }
        });
    }

    @Override
    protected void onHttpCodeError(int threadId, int code, String message) {
        super.onHttpCodeError(threadId, code, message);
        if (threadId == THREAD_ID_008) {
            removeSimCardCode();
        }
    }

    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {
        Gson gson = new Gson();
        switch (threadId) {
            case THREAD_ID_008:
                if (!TextUtils.isEmpty(response)) {
                    CheckSimCardResponse checkSimCardResponse = gson.fromJson(response, CheckSimCardResponse.class);
                    if (checkSimCardResponse != null) {
                        String clues = checkSimCardResponse.getData().clues;
                        if (!"".equals(clues)) {
                            showToast(checkSimCardResponse.getMessage());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    XPopup.get(getContext())
                                            .asCustom(
                                                    new BaseConfirmDialog(getContext())
                                                            .title(getStringRes(R.string.common_dialog_sim_card_title))
                                                            .content(clues)
                                                            .confirm(getStringRes(R.string.common_retry))
                                                            .cancel(getStringRes(R.string.common_continue))
                                                            .listener(new BaseConfirmDialog.OnClickListener() {
                                                                @Override
                                                                public void onCancelClick(BasePopupView popupView, View view) {
                                                                    //继续
                                                                    XPopup.get(UploadOpenAccountPicActivity.this).dismiss();
                                                                    toCommit();
                                                                }

                                                                @Override
                                                                public void onConfirmClick(BasePopupView popupView, View view) {
                                                                    //重试
                                                                    XPopup.get(UploadOpenAccountPicActivity.this).dismiss();
                                                                    btnCommit.setEnabled(false);

                                                                }
                                                            })
                                                            .confirmClick()
                                                            .cancelClick()
                                            )
                                            .autoDismiss(true)
                                            .dismissOnBackPressed(false)
                                            .dismissOnTouchOutside(false)
                                            .show();
                                }
                            }, 2000);
                        } else {
                            btnCommit.setEnabled(true);
                        }
                    }


                }
                break;
            case THREAD_ID_009:
                if (TextUtils.isEmpty(response)) {
                    showToast(ERROR_DATA);
                    return;
                }
                CheckPicResponse checkPicResponse = gson.fromJson(response, CheckPicResponse.class);
                data = checkPicResponse.getData();
                if (data == null) {
                    showToast(getStringRes(R.string.common_err_data));
                } else {
                    String hint = data.getHint();
                    if (!"".equals(hint)) {
                        XPopup.get(getContext())
                                .asCustom(
                                        new BaseConfirmDialog(getContext())
                                                .title(getStringRes(R.string.common_dialog_important_title))
                                                .content(hint)
                                                .confirm(getStringRes(R.string.common_i_know))
                                                .hideCancelBtn()
                                                .listener(new BaseConfirmDialog.OnClickListener() {
                                                    @Override
                                                    public void onCancelClick(BasePopupView popupView, View view) {
                                                    }

                                                    @Override
                                                    public void onConfirmClick(BasePopupView popupView, View view) {
                                                        XPopup.get(UploadOpenAccountPicActivity.this).dismiss();
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                openLive(data);
                                                            }
                                                        }, 100);
                                                    }
                                                })
                                                .confirmClick()
                                )
                                .autoDismiss(true)
                                .dismissOnBackPressed(false)
                                .dismissOnTouchOutside(false)
                                .show();
                    }
                }
                break;
            case THREAD_ID_010:
                if (TextUtils.isEmpty(response)) {
                    showToast(ERROR_DATA);
                    return;
                }
                CheckBeBrandedAsResponse checkBeBrandedAsResponse = gson.fromJson(response, CheckBeBrandedAsResponse.class);
                String clues = checkBeBrandedAsResponse.getData().getClues();
                String basePhotoDir = checkBeBrandedAsResponse.getData().getBasePhotoDir();
                String baseVideoDir = checkBeBrandedAsResponse.getData().getBaseVideoDir();
                mAction.openAccountModel.setBasePhotoDir(basePhotoDir);
                mAction.openAccountModel.setBaseVideoDir(baseVideoDir);
                if (!"".equals(clues)) {
                    XPopup.get(getContext())
                            .asCustom(
                                    new BaseConfirmDialog(getContext())
                                            .title(getStringRes(R.string.common_dialog_important_title))
                                            .content(clues)
                                            .confirm(getStringRes(R.string.common_retry))
                                            .hideCancel()
                                            .listener(new BaseConfirmDialog.OnClickListener() {
                                                @Override
                                                public void onCancelClick(BasePopupView popupView, View view) {
                                                }

                                                @Override
                                                public void onConfirmClick(BasePopupView popupView, View view) {
                                                    //重试
                                                    XPopup.get(UploadOpenAccountPicActivity.this).dismiss();
                                                    requestFaceVerify();

                                                }
                                            })
                                            .confirmClick()
                                            .cancelClick()
                            )
                            .autoDismiss(true)
                            .dismissOnBackPressed(false)
                            .dismissOnTouchOutside(false)
                            .show();
                } else {
                    createCustInfo();
                }
                break;

            case THREAD_ID_011:
                if (TextUtils.isEmpty(response)) {
                    showToast(ERROR_DATA);
                    return;
                }
                CreateCustInfoAndCheckNumResponse createCustInfoAndCheckNumResponse = gson.fromJson(response, CreateCustInfoAndCheckNumResponse.class);
                hideLoading();
                mAction.openAccountModel.setCustId(createCustInfoAndCheckNumResponse.getData().getCustId());
                // 活体检测通过
                // 相似度>=阈值 签名页面
                Intent intent = new Intent(this, OpenAccountSignatureActivity.class);
                intent.putExtra(ACTION_DATA, mAction);
                startActivity(intent);
                break;

            case THREAD_ID_012:
                if (TextUtils.isEmpty(response)) {
                    showToast(ERROR_DATA);
                    return;
                }
                FaceVerifyResponse faceVerifyResponse = gson.fromJson(response, FaceVerifyResponse.class);
                data = faceVerifyResponse.getData();
                if (data != null) {
                    openLive(data);
                }
                break;
        }
    }

    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response, String path) {
        Gson gson = new Gson();
        UploadImageServiceResponse uploadImageServiceResponse = gson.fromJson(response, UploadImageServiceResponse.class);
        String dataCode = uploadImageServiceResponse.getData().getUpload_code();

        switch (threadId) {

            case CODE_PIC_CARD:
                mCodeCard = dataCode;
                showToast(getStringRes(R.string.bus_suc_upload_card));
                dealUploadSuccess(path, imageCard);
                break;
            case CODE_PIC_CARD_BACK:
                mCodeCardBack = dataCode;
                showToast(getStringRes(R.string.bus_suc_upload_card_back));
                dealUploadSuccess(path, imageCardBack);
                break;
            case CODE_ID_CARD_AND_CARD_BOARD:
                mCodeIDCardAndMobilePhoneCard = dataCode;
                showToast(getStringRes(R.string.bus_suc_upload_id_card_and_board));

                OpenAccountActionModel model = mAction.openAccountModel;
                // CHENG-CARD：预录入成卡开户    NO-CHENG-CARD：未预录入成卡开户
                // BAN-CHENG-CARD：半成卡开户    WHITE-CARD：白卡开户
                dealUploadSuccess(path, imageCardAndSim);
                try {
                    JSONObject params = new JSONObject();
                    params.put("phoneNum", model.getPhone_num());
                    params.put("simCardPicName", mCodeIDCardAndMobilePhoneCard);
                    params.put("portraitPicName", mCodeCard);
                    params.put("portraitNum", model.getCert_num());
                    params.put("portraitName", model.getName());
                    if (mAction.isOnline) {
                        params.put("iccid", mAction.openAccountModel.getUim_code());
                    }
                    if (!mAction.isOnline) {
                        if (mAction.isNotPreRecordCard) {
                            //未预录入成卡开户
                            params.put("reqType", "NO-CHENG-CARD");

                        } else {
                            params.put("reqType", "CHENG-CARD");//预录入成卡开户
                        }
                    } else {
                        //半成卡卡开户
                        params.put("reqType", "BAN-CHENG-CARD");
                    }

                    UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_008, CHECK_SIM_CARD, params, SDK_VERSION, UploadOpenAccountPicActivity.this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 处理上传结果
     */
    private void dealUploadSuccess(String path, ImageView imageView) {
        if (imageView != null) {
            RequestOptions requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(PhoneUtils.dip2px(getContext(), 10)));
            Glide.with(UploadOpenAccountPicActivity.this)
                    .load(path)
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 检查上一步是否上传
     *
     * @param step
     * @return
     */
    private boolean checkUploadState(int step) {
        if (step >= 1) {
            if (TextUtils.isEmpty(mCodeCard)) {
                showToast(getStringRes(R.string.bus_toast_need_upload_card));
                return false;
            }
        }
        if (step >= 2) {
            if (TextUtils.isEmpty(mCodeCardBack)) {
                showToast(getStringRes(R.string.bus_toast_need_upload_card_back));
                return false;
            }
        }
        if (step >= 3) {
            if (TextUtils.isEmpty(mCodeIDCardAndMobilePhoneCard)) {
                showToast(getStringRes(R.string.bus_toast_need_upload_card_and_sim));
                return false;
            }
        }
        return true;
    }

    /**
     * 提交资料
     */
    @OnClick(R2.id.bus_btn_commit)
    void toCommit() {
        if (TextUtils.isEmpty(mCodeCard)) {
            showToast(getStringRes(R.string.bus_toast_positive_upload_full));
            return;
        }
        if (TextUtils.isEmpty(mCodeCardBack)) {
            showToast(getStringRes(R.string.bus_toast_obverse_upload_full));
            return;
        }
        if (TextUtils.isEmpty(mCodeIDCardAndMobilePhoneCard)) {
            showToast(getStringRes(R.string.bus_toast_id_card_and_sim_upload_full));
            return;
        }
        if (mAction != null && mAction.openAccountModel != null) {
            OpenAccountActionModel model = mAction.openAccountModel;
            try {
                JSONObject params = new JSONObject();
                params.put("phoneNum", model.getPhone_num());
                params.put("name", model.getName());
                params.put("address", model.getAddress());
                params.put("cardID", model.getCert_num());
                params.put("card_img", mCodeCard);
                params.put("card_back_img", mCodeCardBack);
                params.put("card_sim_img", mCodeIDCardAndMobilePhoneCard);
                params.put("openId", model.getOpen_id());
                params.put("from", "99");

                if (!mAction.isOnline) {
                    if (mAction.isNotPreRecordCard) {
                        params.put("cartType", "4"); // 未预录入可选套餐
                    } else {
                        params.put("cartType", "1");// 预录入成卡
                    }
                } else {
                    if (mAction.isReserved) {
                        params.put("cartType", "3"); // 白卡-预留卡
                    } else {
                        params.put("cartType", "2"); // 白卡-普通卡
                    }
                }
                UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_009, CHECK_PIC, params, SDK_VERSION, UploadOpenAccountPicActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void removeSimCardCode() {
        mCodeIDCardAndMobilePhoneCard = null;
        btnCommit.setEnabled(false);
    }


    private void checkBeBrandedAs() {
        showLoading();
        try {
            JSONObject params = new JSONObject();
            params.put("orderNo", data.getOrderId());
            params.put("from", "99");
            params.put("phoneNum", mAction.openAccountModel.getPhone_num());
            params.put("reqType", mAction.isOnline ? NO_FINISHED_CARD_OPEN : mAction.isNotPreRecordCard ? NO_INPUT_CARD_OPEN : PRE_INPUT_CARD_OPEN);
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_010, CHECK_BE_BRANDED_AS, params, SDK_VERSION, UploadOpenAccountPicActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建客户资料
     */
    private void createCustInfo() {
        try {
            OpenAccountActionModel model = mAction.openAccountModel;
            JSONObject params = new JSONObject();
            params.put("name", model.getName());
            params.put("address", model.getAddress());
            params.put("cardId", model.getCert_num());
            params.put("productId", model.getProductId());
            params.put("type", "2");
            params.put("phoneNum", model.getPhone_num());
            params.put("reqType", mAction.isOnline ? "3" : mAction.isNotPreRecordCard ? "2" : "1");

            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_011, CREATE_CUST_INFO_AND_CHECK_NUM, params, SDK_VERSION, UploadOpenAccountPicActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openLive(WebankFaceBean data) {
        hideLoading();
        WebankUtils.initWebank(UploadOpenAccountPicActivity.this, data, new WebankUtils.OnWeBankInitListener() {
            @Override
            public void onSuccess() {
                WbCloudFaceVerifySdk.getInstance().startWbFaceVerifySdk(getContext(), result -> {
                    String liveRate = null;
                    String similarity = null;
                    boolean needUploadError = false;
                    String errorMsg = null;
                    if (result != null) {
                        if (result.isSuccess()) {
                        } else {
                            WbFaceError error = result.getError();
                            if (error != null) {
                                if (WbFaceError.WBFaceErrorDomainCompareServer.equals(error.getDomain())) {
                                    needUploadError = true;
                                }
                                if (WbFaceError.WBFaceErrorDomainCompareNetwork.equals(error.getDomain())) {
                                    showToast("网络异常, 请稍后重试, code:" + error.getCode());
                                } else {
                                    showToast(error.getDesc() + ", code:" + error.getCode() + ", reason:" + error.getReason());
                                }
                                errorMsg = error.getDesc();
                            } else {
                                errorMsg = "活体检测失败！sdk返回error为空";
                                showToast(errorMsg);
                            }
                        }
                        similarity = result.getSimilarity();
                        liveRate = result.getLiveRate();
                    } else {
                        errorMsg = "返回结果为空";
                        showToast(errorMsg);
                    }
                    WebankUtils.release();
                    if (TextUtils.isEmpty(similarity)) {
                        similarity = "0";
                    }
                    if (TextUtils.isEmpty(liveRate)) {
                        liveRate = "0";
                    }
                    mAction.openAccountModel.setOpen_id(data.getOrderId());
                    mAction.openAccountModel.setFaceOpenId(data.getOpenId());
                    mAction.openAccountModel.setFaceOrderId(data.getOrderId());
                    mAction.openAccountModel.setImageCard(mCodeCard);
                    mAction.openAccountModel.setImageCardBack(mCodeCardBack);
                    mAction.openAccountModel.setImageCardAndSim(mCodeIDCardAndMobilePhoneCard);
                    mAction.openAccountModel.setReqOrderId(data.getReqOrderId());

                    mAction.faceScoreLimit = data.getValve();
                    mAction.faceTip = data.getNotes();
                    mAction.needUploadError = needUploadError;


                    String finalSimilarity = similarity;
                    String finalLiveRate = liveRate;
                    String finalErrorMsg = errorMsg;
                    new Handler().postDelayed(() -> {

                        float similarityInt = StringUtils.toFloat(finalSimilarity);
                        if (similarityInt < data.getValve()) {
                            // 活体检测不通过
                            Intent intent = new Intent(UploadOpenAccountPicActivity.this, OpenAccountFaceVerifyResultActivity.class);
                            intent.putExtra(ACTION_LIVE_RATE, finalLiveRate);
                            intent.putExtra(ACTION_SIMILARITY, finalSimilarity);
                            intent.putExtra(ACTION_ERROR_MSG, finalErrorMsg);
                            intent.putExtra(ACTION_DATA, mAction);
                            startActivity(intent);
                        } else {
                            checkBeBrandedAs();
                        }
                    }, 300);
                });
            }

            @Override
            public void onFail(String message) {
                hideLoading();
                showToast(message);
            }
        });
    }


    private void requestFaceVerify() {
        // 相似度小于阙值 重新请求识别
        try {
            JSONObject params = new JSONObject();
            params.put("name", mAction.openAccountModel.getName());
            params.put("openId", mAction.openAccountModel.getFaceOpenId());
            params.put("cardID", mAction.openAccountModel.getCert_num());
            params.put("from", "99");
            if (!mAction.isOnline) {
                if (mAction.isNotPreRecordCard) {
                    params.put("cartType", "4");
                    // 未预录入成卡
                } else {
                    params.put("cartType", "1"); // 成卡
                }
            } else {
                if (mAction.isReserved) {
                    params.put("cartType", "3"); // 预留卡
                } else {
                    params.put("cartType", "2"); // 普通卡
                }
            }

            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_012, FACE_VERIFY, params, SDK_VERSION, UploadOpenAccountPicActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressLoading(int progress) {
        BasePopupView popupView = XPopup.getShowTagPop(TAG_DIALOG_LOADING);
        if (popupView == null) {
            XPopup.get(getContext())
                    .asCustom(new LoadingDialog(getContext()).setTitle("上传中.."))
                    .hasShadowBg(false)
                    .dismissOnTouchOutside(false)
                    .dismissOnBackPressed(canBack)
                    .customAnimator(new EmptyAnimator())
                    .show(TAG_DIALOG_LOADING);

        } else {
            if (popupView instanceof LoadingDialog) {
                LoadingDialog pop = (LoadingDialog) popupView;
                pop.resetTitle(progress + "%");
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (mAction == null) {
                Object object = savedInstanceState.getSerializable(ACTION_DATA);
                if (object != null && (object instanceof OpenAccountAction)) {
                    mAction = (OpenAccountAction) object;
                    mDiscern = mAction.discern;
                }
            }
            mCodeCard = savedInstanceState.getString(ACTION_CODE_CARD, "");
            mCodeCardBack = savedInstanceState.getString(ACTION_CODE_CARD_BACK, "");
            mCodeIDCardAndMobilePhoneCard = savedInstanceState.getString(ACTION_CODE_CARD_SIM, "");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAction != null) {
            outState.putSerializable(ACTION_DATA, mAction);
        }
        if (mCodeCard != null) {
            outState.putString(ACTION_CODE_CARD, mCodeCard);
        }
        if (mCodeCardBack != null) {
            outState.putString(ACTION_CODE_CARD_BACK, mCodeCardBack);
        }
        if (mCodeIDCardAndMobilePhoneCard != null) {
            outState.putString(ACTION_CODE_CARD_SIM, mCodeIDCardAndMobilePhoneCard);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        WebankUtils.release();
        super.onDestroy();
    }


}
