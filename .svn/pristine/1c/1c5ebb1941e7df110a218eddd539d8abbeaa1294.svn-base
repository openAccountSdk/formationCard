package com.uyou.copenaccount.ui;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.ACTION_ERROR_MSG;
import static com.uyou.copenaccount.base.UCConstants.ACTION_LIVE_RATE;
import static com.uyou.copenaccount.base.UCConstants.ACTION_SIMILARITY;
import static com.uyou.copenaccount.base.UCConstants.CHECK_BE_BRANDED_AS;
import static com.uyou.copenaccount.base.UCConstants.CREATE_CUST_INFO_AND_CHECK_NUM;
import static com.uyou.copenaccount.base.UCConstants.FACE_SIMILARITY_VAL;
import static com.uyou.copenaccount.base.UCConstants.NO_FINISHED_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.NO_INPUT_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.PRE_INPUT_CARD_OPEN;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_010;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_011;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_012;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_016;
import static com.uyou.copenaccount.utils.StringUtils.setViewText;
import static com.uyou.copenaccount.utils.net.Api.FACE_VERIFY;
import static com.uyou.copenaccount.utils.net.Api.FACE_VERIFY_FAIL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import com.uyou.copenaccount.response.CheckBeBrandedAsResponse;
import com.uyou.copenaccount.response.CreateCustInfoAndCheckNumResponse;
import com.uyou.copenaccount.response.FaceVerifyResponse;
import com.uyou.copenaccount.utils.DeviceIdUtils;
import com.uyou.copenaccount.utils.StringUtils;
import com.uyou.copenaccount.utils.WebankUtils;
import com.uyou.copenaccount.utils.net.UYouHttpClient;
import com.uyou.copenaccount.xpopup.XPopup;
import com.uyou.copenaccount.xpopup.core.BasePopupView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zdd on 2019/11/04
 * <p>
 * Description: 开户人脸识别结果页
 */
public class OpenAccountFaceVerifyResultActivity extends UOpenBaseActivity {

    @BindView(R2.id.bus_txt_face_verify_point)
    TextView txtVerifyPoint;
    @BindView(R2.id.bus_txt_face_pic_verify_point)
    TextView txtPicVerifyPoint;
    @BindView(R2.id.bus_txt_result)
    TextView txtResult;
    @BindView(R2.id.bus_btn_next)
    TextView btnNext;

    private OpenAccountAction mAction;

    private float mSimilarity;
    private String mStrSimilarity;
    private String mStrLiveRate;
    // 发生比对错误, 需要上报信息
    private boolean needUploadError;
    // 微众订单号, 用作失败上传时参数
    private String mOrderNo;

    private int mFaceSimilarityLimitScore = FACE_SIMILARITY_VAL;
    private WebankFaceBean data;


    @Override
    public String getPageTitle() {
        return getStringRes(R.string.bus_title_face_verify_result);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.bus_activity_face_verify_result_fail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mStrSimilarity = getIntent().getStringExtra(ACTION_SIMILARITY);
        mStrLiveRate = getIntent().getStringExtra(ACTION_LIVE_RATE);
        String errorMsg = getIntent().getStringExtra(ACTION_ERROR_MSG);
        setViewText(txtResult, errorMsg);

        Object object = getIntent().getSerializableExtra(ACTION_DATA);

        if (object != null) {
            if (object instanceof OpenAccountAction) {
                mAction = (OpenAccountAction) object;
            }
        }
        if (mAction == null) {
            showToast(getStringRes(R.string.common_err_data));
            finish();
            return;
        }

        needUploadError = mAction.needUploadError;

        mFaceSimilarityLimitScore = mAction.faceScoreLimit;

        setViewText(txtVerifyPoint, mStrLiveRate);

        mOrderNo = mAction.openAccountModel.getFaceOrderId();

        if (mStrSimilarity != null) {
            mSimilarity = StringUtils.toFloat(mStrSimilarity);
            setViewText(txtPicVerifyPoint, mStrSimilarity);
        }

    }

    @OnClick(R2.id.bus_btn_next)
    void toNext() {
        showLoading();
        if (needUploadError) {
            try {
                JSONObject params = new JSONObject();
                params.put("orderNo", mOrderNo);
                params.put("liveRate", mStrLiveRate);
                params.put("similarity", mStrSimilarity);
                params.put("source", "wz");
                params.put("from", "1");
                params.put("phoneCode", DeviceIdUtils.getid(OpenAccountFaceVerifyResultActivity.this));
                UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_016, FACE_VERIFY_FAIL, params, SDK_VERSION, OpenAccountFaceVerifyResultActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestFaceVerify();
        }
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

            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_012, FACE_VERIFY, params, SDK_VERSION, OpenAccountFaceVerifyResultActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openFace(WebankFaceBean bean) {
        WebankUtils.initWebank(OpenAccountFaceVerifyResultActivity.this, bean, new WebankUtils.OnWeBankInitListener() {
            @Override
            public void onSuccess() {
                WbCloudFaceVerifySdk.getInstance().startWbFaceVerifySdk(getContext(), result -> {
                    hideLoading();
                    mStrLiveRate = null;
                    mStrSimilarity = null;
                    mSimilarity = 0;
                    needUploadError = false;
                    mOrderNo = null;
                    String errorMsg = null;
                    if (result != null) {
                        if (result.isSuccess()) {
                            mStrSimilarity = result.getSimilarity();
                            mStrLiveRate = result.getLiveRate();
                        } else {
                            WbFaceError error = result.getError();
                            if (error != null) {
                                if (WbFaceError.WBFaceErrorDomainCompareServer.equals(error.getDomain())) {
                                    mStrSimilarity = result.getSimilarity();
                                    mStrLiveRate = result.getLiveRate();
                                    needUploadError = true;
                                    mOrderNo = result.getOrderNo();
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
                    } else {
                        errorMsg = "返回结果为空";
                        showToast(errorMsg);
                    }
                    WebankUtils.release();
                    // 做一个延迟, 防止内存过低时, activity被销毁后还未恢复成功时, view为空出现问题
                    String finalLiveRate = mStrLiveRate;
                    String finalErrorMsg = errorMsg;
                    new Handler().postDelayed(() -> {
                        if (finalErrorMsg == null) {
                            setViewText(txtResult, "");
                        } else {
                            setViewText(txtResult, finalErrorMsg);
                        }
                        if (TextUtils.isEmpty(finalLiveRate)) {
                            setViewText(txtVerifyPoint, "0");
                        } else {
                            setViewText(txtVerifyPoint, finalLiveRate);
                        }
                        if (TextUtils.isEmpty(mStrSimilarity)) {
                            setViewText(txtPicVerifyPoint, "0");
                        } else {
                            mSimilarity = StringUtils.toFloat(mStrSimilarity);
                            setViewText(txtPicVerifyPoint, mStrSimilarity);
                            if (mSimilarity >= mFaceSimilarityLimitScore) {
                                // 活体检测通过
                                mAction.openAccountModel.setFaceOpenId(bean.getOpenId());
                                mAction.openAccountModel.setFaceOrderId(bean.getOrderId());
                                checkBeBrandedAs();
                            }
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

    private void checkBeBrandedAs() {
        try {
            JSONObject params = new JSONObject();
            params.put("orderNo", data.getOrderId());
            params.put("from", "99");
            params.put("phoneNum", mAction.openAccountModel.getPhone_num());
            params.put("reqType", mAction.isOnline ? NO_FINISHED_CARD_OPEN : mAction.isNotPreRecordCard ? NO_INPUT_CARD_OPEN : PRE_INPUT_CARD_OPEN);
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_010, CHECK_BE_BRANDED_AS, params, SDK_VERSION, OpenAccountFaceVerifyResultActivity.this);
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

            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_011, CREATE_CUST_INFO_AND_CHECK_NUM, params, SDK_VERSION, OpenAccountFaceVerifyResultActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {
        Gson gson = new Gson();
        switch (threadId) {
            case THREAD_ID_012:
                FaceVerifyResponse faceVerifyResponse = gson.fromJson(response, FaceVerifyResponse.class);
                data = faceVerifyResponse.getData();
                mFaceSimilarityLimitScore = data.getValve();
                new Handler().postDelayed(() -> openFace(data), 100);
                break;
            case THREAD_ID_016:
                requestFaceVerify();
                break;

            case THREAD_ID_010:
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
                                                    XPopup.get(OpenAccountFaceVerifyResultActivity.this).dismiss();
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
                CreateCustInfoAndCheckNumResponse createCustInfoAndCheckNumResponse = gson.fromJson(response, CreateCustInfoAndCheckNumResponse.class);
                mAction.openAccountModel.setCustId(createCustInfoAndCheckNumResponse.getData().getCustId());
                // 相似度>=阈值 签名页面
                Intent intent = new Intent(this, OpenAccountSignatureActivity.class);
                intent.putExtra(ACTION_DATA, mAction);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (mAction == null) {
                Object object = savedInstanceState.getSerializable(ACTION_DATA);
                if ((object instanceof OpenAccountAction)) {
                    mAction = (OpenAccountAction) object;
                }
            }
            mStrSimilarity = savedInstanceState.getString(ACTION_SIMILARITY);
            mSimilarity = StringUtils.toFloat(mStrSimilarity);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAction != null) {
            outState.putSerializable(ACTION_DATA, mAction);
        }
        if (mStrSimilarity != null) {
            outState.putString(ACTION_SIMILARITY, mStrSimilarity);
        }
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        // 页面重启可提交
        if (btnNext != null) {
            btnNext.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        WebankUtils.release();
        super.onDestroy();
    }
}
