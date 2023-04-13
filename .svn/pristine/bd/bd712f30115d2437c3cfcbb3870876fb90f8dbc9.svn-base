package com.uyou.copenaccount.ui;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.ACTION_PATH;
import static com.uyou.copenaccount.base.UCConstants.ACTION_SIGNATURE_UPLOAD_CODE;
import static com.uyou.copenaccount.base.UCConstants.OPEN_ACCOUNT_NEW;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_015;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.dialog.BaseConfirmDialog;
import com.uyou.copenaccount.model.NavPhoneRechargeModel;
import com.uyou.copenaccount.model.OpenAccountAction;
import com.uyou.copenaccount.model.OpenAccountActionModel;
import com.uyou.copenaccount.response.OpenAccountNewRequest;
import com.uyou.copenaccount.response.OpenAccountResponse;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.AppUtils;
import com.uyou.copenaccount.utils.LocationUtils;
import com.uyou.copenaccount.utils.net.UYouHttpClient;
import com.uyou.copenaccount.xpopup.XPopup;
import com.uyou.copenaccount.xpopup.core.BasePopupView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmAccountOpeningActivity extends UOpenBaseActivity {
    @BindView(R2.id.bus_confirm)
    TextView btnNext;


    // 提交成功标识, 通过此值判断, 在提交 成功后到打开支付页面间隔防止再次提交
    private boolean commitSuccess = false;
    private OpenAccountAction mAction;
    private String uploadCode;
    private String reqOrderId;

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.activity_confirm_account_opening;
    }

    @Override
    public String getPageTitle() {
        return getStringRes(R.string.bus_title_confirm_account_opening);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        ImageView btnBack = findViewById(R.id.ucommon_common_title_back);
        btnBack.setVisibility(View.GONE);

        Object object = getIntent().getSerializableExtra(ACTION_DATA);
        uploadCode = getIntent().getStringExtra(ACTION_SIGNATURE_UPLOAD_CODE);

        if (object != null) {
            if (object instanceof OpenAccountAction) {
                mAction = (OpenAccountAction) object;
            }
        }
    }

    /**
     * 确认办理
     */
    @OnClick(R2.id.bus_confirm)
    void confirmHandling() {
        btnNext.setEnabled(false);
        showLoading();
        toOpenAccount(uploadCode);
    }

    /**
     * 开户
     */
    private void toOpenAccount(String uploadCode) {
        if (commitSuccess) {
            commitSuccess = false;
            return;
        }
        if (mAction != null & mAction.openAccountModel != null) {
            OpenAccountActionModel model = mAction.openAccountModel;

            // 地址信息
            String phoneInProvince = LocationUtils.getProvince(getContext());
            String phoneInCity = LocationUtils.getCity(getContext());
            String phoneInDetail = LocationUtils.getDetail(getContext());
            String district = LocationUtils.getDistrict(getContext());
            String lat = LocationUtils.getLat(getContext());
            String lng = LocationUtils.getLng(getContext());
            if (!mAction.isOnline) {
                // 2019/10/14-成卡开户接口变更
                // 成卡开户
                OpenAccountNewRequest request = new OpenAccountNewRequest();
                request.setCustId(model.getCustId());
                request.setPhone_in_province(phoneInProvince);
                request.setPhone_in_city(phoneInCity);
                request.setPhone_in_detail(phoneInDetail);
                request.setDistrict(district);
                request.setLatitude(lat);
                request.setLongitude(lng);

                request.setPhone_num(model.getPhone_num());
                request.setUim_code(model.getUim_code());

                // 套餐信息
                request.setProductId(model.getProductId());
                request.setProductName(model.getProductName());
                request.setPackageJson(model.getPackageJson());

                request.setElec_sign_img(uploadCode);

//                request.setOrderNoWZ(model.getFaceOrderId());
                request.setOpenId(model.getFaceOrderId());

                // 身份证上传
                request.setCard_img(model.getImageCard());
                request.setCard_back_img(model.getImageCardBack());
                request.setCard_sim_img(model.getImageCardAndSim());

                // 身份证信息
                request.setName(model.getName());
                request.setSex(model.getSex());
                request.setNation(model.getNation());
                request.setBirth(model.getBirth());
                request.setAddress(model.getAddress());
                request.setCert_num(model.getCert_num());
                request.setUpload_code(model.getUpload_code());
                request.setReqFrom("B-JINLUN-CUSTOMERS");

                // 开卡设备
                request.setOpen_account_type(model.getOpen_account_type());
                request.setOpen_account_factory(model.getOpen_account_factory());
                request.setDevice_num(model.getDevice_num());

                request.setEncryption(model.getEncryption());
                request.setSecurekey(model.getSecurekey());
                request.setBasePhotoDir(model.getBasePhotoDir());
                request.setBaseVideoDir(model.getBaseVideoDir());
                if (AppUtils.isAdopt(this) || AppUtils.notHasBlueTooth()
                        || AppUtils.notHasLightSensorManager(this)
                        || AppUtils.isFeatures()
                        || AppUtils.checkIsNotRealPhone()
                        || AppUtils.checkPipes()) {
                    request.setSimulator("yes");
                } else {
                    request.setSimulator("no");
                }

                request.setSimCardOrderId(model.getReqOrderId());
                if (TextUtils.isEmpty(reqOrderId)) {
                    String reqOrderId = AppConfigs.getReqOrderId();
                    request.setReqOrderId(reqOrderId);
                    AppConfigs.setStringSF(ConfirmAccountOpeningActivity.this, "reqOrderId", reqOrderId);
                } else {
                    request.setReqOrderId(reqOrderId);
                }

                try {
                    String s = new Gson().toJson(request);
                    JSONObject params = new JSONObject(s);
                    UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_015, OPEN_ACCOUNT_NEW, params, SDK_VERSION, ConfirmAccountOpeningActivity.this);
                    btnNext.setEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 开户成功弹窗
     */
    private void showOpenSuccessDialog(String message) {
        if (TextUtils.isEmpty(message)) {
            message = "开户资料提交成功";
        }
        XPopup.get(ConfirmAccountOpeningActivity.this)
                .asCustom(
                        new BaseConfirmDialog(ConfirmAccountOpeningActivity.this)
                                .title(getStringRes(R.string.bus_upload_open_suc_dialog_title))
                                .content(message)
                                .hideCancel()
                                .confirm(getStringRes(R.string.common_confirm))
                                .listener(new BaseConfirmDialog.OnClickListener() {
                                    @Override
                                    public void onCancelClick(BasePopupView popupView, View view) {

                                    }

                                    @Override
                                    public void onConfirmClick(BasePopupView popupView, View view) {
                                        // 关闭所有页面跳转至开户进度页
                                        // TODO: 2022/9/15 开户结束
                                        popupView.dismiss();
                                        String path = AppConfigs.getStringSF(ConfirmAccountOpeningActivity.this, ACTION_PATH);
                                        Intent intent = new Intent();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.setClassName(getPackageName(),path);
                                        startActivity(intent);
                                        finish();

                                    }
                                })
                                .confirmClick()
                )
                .autoDismiss(true)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .show();
    }

    @Override
    public void onHttpError(int threadId, String error) {
        super.onHttpError(threadId, error);
        if (threadId == THREAD_ID_015) {
            btnNext.setEnabled(true);
            reqOrderId = AppConfigs.getStringSF(ConfirmAccountOpeningActivity.this, "reqOrderId");

        }
    }

    @Override
    protected void onHttpCodeError(int threadId, int code, String message) {
        super.onHttpCodeError(threadId, code, message);
        if (threadId == THREAD_ID_015) {
            if (997 == code) {
                //CountDownTimer 实现倒计时
                CountDownTimer countDownTimer = new CountDownTimer(30 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int s = (int) (millisUntilFinished / 1000);
                        if (btnNext != null) {
                            btnNext.setText(String.format(getResources().getString(R.string.bus_resubmit), s));
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (btnNext != null) {
                            btnNext.setText(R.string.bus_sign_confirm);
                            btnNext.setEnabled(true);
                        }
                    }
                };
                //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
                countDownTimer.start();
            } else {
                AppConfigs.setStringSF(ConfirmAccountOpeningActivity.this, "reqOrderId", "");
                showOpenSuccessDialog(message);
            }
        }
    }

    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {
        Gson gson = new Gson();
        if (threadId == THREAD_ID_015) {
            AppConfigs.setStringSF(ConfirmAccountOpeningActivity.this, "reqOrderId", "");
            OpenAccountResponse openAccountResponse = gson.fromJson(response, OpenAccountResponse.class);
            if (openAccountResponse.getData() != null && openAccountResponse.getData().getIs_pay() != null) {
                String isPay = openAccountResponse.getData().getIs_pay();
                if (!"-1".equals(isPay)) {
                    btnNext.postDelayed(() -> {
                        NavPhoneRechargeModel model = new NavPhoneRechargeModel();
                        model.setPhoneNum(mAction.mobileNum);
                        model.setRequestId(openAccountResponse.getData().getRequest_id());
                        model.setIsPay(isPay);
                        model.setOpenId(openAccountResponse.getData().getOpenId());
                        model.setPayType("1");
                        model.setIsOnLine(mAction.isOnline);
                        if (!mAction.noIsOnline) { // 白卡
                            model.setICCID(mAction.openAccountModel.getUim_code());
                        }
                        if (mAction.isNotPreRecordCard) {
                            model.setICCID(openAccountResponse.getData().getICCID());
                            model.setNotPreRecordCard(true);
                        }

                        btnNext.setEnabled(true);
                        commitSuccess = false;
                        Intent intent = new Intent(this, PhoneRechargeActivity.class);
                        intent.putExtra(ACTION_DATA, model);
                        startActivity(intent);
                        //提示用户暂时不能充值
//                            XPopup.get(OpenAccountSignatureActivity.this)
//                                    .asCustom(
//                                            new BaseConfirmDialog(OpenAccountSignatureActivity.this)
//                                                    .title(getStringRes(R.string.bus_upload_open_suc_dialog_title))
//                                                    .content("该号码需要在激活时充值，暂不支持该业务场景，请联系业务人员核实")
//                                                    .hideCancel()
//                                                    .confirm(getStringRes(R.string.common_confirm))
//                                                    .listener(new BaseConfirmDialog.OnClickListener() {
//                                                        @Override
//                                                        public void onCancelClick(BasePopupView popupView, View view) {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onConfirmClick(BasePopupView popupView, View view) {
//
//                                                            popupView.dismiss();
//
//
//                                                        }
//                                                    })
//                                                    .confirmClick()
//                                    )
//                                    .autoDismiss(true)
//                                    .dismissOnBackPressed(false)
//                                    .dismissOnTouchOutside(false)
//                                    .show();
                    }, 300);
                } else {
                    if (!TextUtils.isEmpty(openAccountResponse.getMessage()))
                        showOpenSuccessDialog(openAccountResponse.getMessage());
                    commitSuccess = false;
                }
            } else {
                if (!TextUtils.isEmpty(openAccountResponse.getMessage()))
                    showOpenSuccessDialog(openAccountResponse.getMessage());
                commitSuccess = false;
            }
        }
    }

    @Override
    public boolean superDispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }


}