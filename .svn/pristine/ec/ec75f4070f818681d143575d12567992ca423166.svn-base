package com.uyou.copenaccount.ui;


import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.PAY_TYPE_ALI;
import static com.uyou.copenaccount.base.UCConstants.PAY_TYPE_WECHAT;
import static com.uyou.copenaccount.base.UCConstants.QUERY_PHONE_PAY_ALI_PARAM;
import static com.uyou.copenaccount.base.UCConstants.QUERY_PHONE_PAY_INFO;
import static com.uyou.copenaccount.base.UCConstants.QUERY_PHONE_PAY_WECHAT_PARAM;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_019;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_020;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_021;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.model.NavPayModel;
import com.uyou.copenaccount.response.QueryALiPayParamResponse;
import com.uyou.copenaccount.response.QueryPhonePayInfoResponse;
import com.uyou.copenaccount.response.QueryWeChatPayParamResponse;
import com.uyou.copenaccount.utils.AccountUtils;
import com.uyou.copenaccount.utils.net.UYouHttpClient;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zdd on 2019/04/26
 * <p>
 * Description: 支付 activity
 */
public class PayActivity extends UOpenBaseActivity {

    private static final int FLAG_ALI_PAY = 999;
    @BindView(R2.id.pay_txt_confirm_tip)
    TextView txtTip;
    @BindView(R2.id.pay_btn_pay)
    Button btnPay;

    @BindView(R2.id.pay_layout_type_payment)
    LinearLayout layoutPayment;
    @BindView(R2.id.pay_layout_type_number)
    LinearLayoutCompat layoutNumber;

    @BindView(R2.id.pay_group_pay)
    RadioGroup groupPay;
    @BindView(R2.id.pay_radio_method_alipay)
    RadioButton radioAliPay;
    @BindView(R2.id.pay_radio_method_wechat)
    RadioButton radioWeChat;
    @BindView(R2.id.pay_txt_phone_num)
    TextView txtPhoneNum;
    @BindView(R2.id.pay_txt_amount)
    TextView txtAmount;
    @BindView(R2.id.pay_txt_pay_money)
    TextView txtPayMoney;

    @BindView(R2.id.pay_txt_payment_type)
    TextView txtPaymentType;
    @BindView(R2.id.pay_txt_payment_count)
    TextView txtPaymentCount;
    @BindView(R2.id.pay_txt_payment_price)
    TextView txtPaymentPrice;

    /**
     * 手机号
     */
    private String mPhone;
    /**
     * 充值金额code
     */
    private String mPayCode;
    /**
     * 充值金额
     */
    private int mPayPriceValue;
    /**
     * 付款方式
     */
    private String mPayType;
    private String mICCID;
    private String mOpenAccountRequestId;
    private String mIsPay;

    /**
     * 应付金额
     */
    private double mShowPayMoney = -1;
    /**
     * 付款折扣
     */
    private String mPayDiscount;

    /**
     * 数量
     */
    private String mAmount;
    /**
     * 是否为未预录入号码
     */
    private boolean isNotPreRecordCard;


    @Override
    public String getPageTitle() {
        return getStringRes(R.string.pay_title_confirm);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.pay_activity_pay;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initPayMethodTxt();
        init(getIntent());

        groupPay.check(R.id.pay_radio_method_alipay);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    /**
     * 初始化
     */
    private void init(Intent intent) {
        // 重置样式
        resetView();
        // 重置数据
        mShowPayMoney = -1;
        mAmount = "";
        mPayDiscount = "";
        if (intent != null) {
            Object obj = intent.getSerializableExtra(ACTION_DATA);
            if (obj != null && obj instanceof NavPayModel) {
                NavPayModel model = (NavPayModel) obj;
                mICCID = model.getICCID();
                mIsPay = model.getIsPayed();
                mPayCode = model.getPriceCode();
                mPayPriceValue = model.getPriceVal();
                mPhone = model.getPhone();
                mOpenAccountRequestId = model.getOpenAccountRequestId();
                mPayType = model.getPayType();
                isNotPreRecordCard = model.isNotPreRecordCard();
                initView();
                queryRechargeData();
            } else {
                resetErrorViews();
            }
        } else {
            resetErrorViews();
        }
    }

    private void resetErrorViews() {
        // 传参异常
        showToast(getStringRes(R.string.common_toast_param_error));
        mPhone = null;
        mPayCode = null;
        mPayPriceValue = 0;
        mPayType = null;
        mICCID = null;
        mOpenAccountRequestId = null;
        mIsPay = null;
        btnPay.setVisibility(View.GONE);
    }

    /**
     * 重置view
     */
    private void resetView() {
        resetPayMethodEnable(false);
        txtTip.setText(getStringRes(R.string.pay_confirm_come_on_pay));
        layoutNumber.setVisibility(View.VISIBLE);
        layoutPayment.setVisibility(View.GONE);
        txtPayMoney.setText("");
        txtAmount.setText("");
        txtPhoneNum.setText("");
    }

    /**
     * 初始化view状态
     */
    private void initView() {
        // 补货/缴费, 号码充值 显示状态
        btnPay.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化数据请求
     */
    private void queryRechargeData() {
        // 请求手机充值缴费信息
        if (!TextUtils.isEmpty(mICCID)) {
            // 白卡
            mPayType = "2";
        }
        if (isNotPreRecordCard) {
            // 未预录入号码
            mPayType = "2";
        }
        txtPhoneNum.setText(getResources().getString(R.string.pay_confirm_phone_num, mPhone));
        txtAmount.setText(getStringRes(R.string.pay_confirm_phone_amount, String.format("%.02f", mPayPriceValue / 100f)));
        txtPayMoney.setText(getStringRes(R.string.pay_confirm_phone_pay_money));

        try {
            JSONObject json = new JSONObject();
            // 请求手机充值缴费信息
            if (!TextUtils.isEmpty(mICCID)) {
                // 白卡
                mPayType = "2";
            }
            if (isNotPreRecordCard) {
                // 未预录入号码
                mPayType = "2";
            }

            json.put("request_id", mOpenAccountRequestId);
            json.put("mobile_no", mPhone);
            json.put("pay_code", mPayCode);
            json.put("pay_type", mPayType);
            json.put("is_pay", mIsPay);
            json.put("ICCID", mICCID);
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_019, QUERY_PHONE_PAY_INFO, json, SDK_VERSION, PayActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {
        Gson gson = new Gson();
        switch (threadId) {
            case THREAD_ID_019:
                //查询话费充值金额返回结果
                QueryPhonePayInfoResponse queryPhonePayInfoResponse = gson.fromJson(response, QueryPhonePayInfoResponse.class);

                double pay_money = 0;
                double pay_discount = 0;
                String pay = null;
                if (queryPhonePayInfoResponse.getData().getPay_value() != null) {
                    pay = queryPhonePayInfoResponse.getData().getPay_value();// 获取应付金额
                    pay_money = Double.parseDouble(pay);
                    if (null != queryPhonePayInfoResponse.getData().getDiscount()) {
                        pay_discount = Double.parseDouble(queryPhonePayInfoResponse.getData().getDiscount());
                    }
                }

                // 显示支付金额
                if (pay != null && pay.length() > 0) {
                    mShowPayMoney = pay_money;
                    if (pay_discount > 0) {
                        mPayDiscount = String.valueOf(pay_discount);
                        String s = "应付金额：<font color=\"#f39700\">" + String.format("%.02f元", mShowPayMoney / 100d)
                                + String.format("(%.02f折", pay_discount * 10) + ")" + "</font>";
                        CharSequence cs = Html.fromHtml(s);
                        txtPayMoney.setText(cs);
                    } else {
                        String s = "应付金额：<font color=\"#f39700\">" + String.format("%.02f元", mShowPayMoney / 100d) + "</font>";
                        CharSequence cs = Html.fromHtml(s);
                        txtPayMoney.setText(cs);
                    }

                    radioAliPay.setEnabled(true);
                    radioWeChat.setEnabled(true);
                }
                break;
            case THREAD_ID_020:
                QueryWeChatPayParamResponse queryWeChatPayParamResponse = gson.fromJson(response, QueryWeChatPayParamResponse.class);
                String mwebUrl = queryWeChatPayParamResponse.getData().getMwebUrl();
                Intent intent = new Intent(this, H5OpenAccountActivity.class);
                intent.putExtra(ACTION_DATA, mwebUrl);
                startActivity(intent);
                break;
            case THREAD_ID_021:
                QueryALiPayParamResponse queryALiPayParamResponse = gson.fromJson(response, QueryALiPayParamResponse.class);
                String result = queryALiPayParamResponse.getData().getResult();
                Intent intent1 = new Intent(this, H5OpenAccountActivity.class);
                intent1.putExtra(ACTION_DATA, result);
                startActivity(intent1);
                break;

        }
    }

    /**
     * 支付
     */
    @OnClick(R2.id.pay_btn_pay)
    void toPay() {
        if (mShowPayMoney < 0) {
            // 重新查询支付数据
            queryRechargeData();
            return;
        }
        // 根据选择的支付方式进行支付
        int id = groupPay.getCheckedRadioButtonId();
        if (id == R.id.pay_radio_method_alipay) {
            payByAliPay();
        } else if (id == R.id.pay_radio_method_wechat) {
            payByWeChat();
        }

    }

    /**
     * 通过支付宝充值
     */
    private void payByAliPay() {
        queryPayParams(PAY_TYPE_ALI);
    }

    /**
     * 通过微信充值
     */
    private void payByWeChat() {
        queryPayParams(PAY_TYPE_WECHAT);
    }


    /**
     * @param type 1-支付宝, 2-微信
     */
    private void queryPayParams(int type) {
        String openAccountType = TextUtils.isEmpty(mICCID) ? "" : "1"; // 白卡 1
        if (isNotPreRecordCard) { // 未预录入成卡 2
            openAccountType = "2";
        }
        //充值缴费支付参数查询
        btnPay.setEnabled(false);
        queryPhonePayParams(type, mPhone, mPayCode, mPayDiscount, mOpenAccountRequestId, openAccountType);
    }

    /**
     * 充值缴费支付参数查询
     *
     * @param type 1-支付宝  2-微信
     */
    public void queryPhonePayParams(int type, String phone, String payCode, String discount, String openAccountRequestId, String openAccountType) {
        JSONObject json = new JSONObject();
        try {
            // 请求手机充值缴费信息
            if (!TextUtils.isEmpty(mICCID)) {
                // 白卡
                mPayType = "2";
            }
            if (isNotPreRecordCard) {
                // 未预录入号码
                mPayType = "2";
            }
            if (TextUtils.isEmpty(openAccountRequestId)) {
                json.put("request_id", "110" + getTimeStr() + "1");

            } else {
                json.put("request_id", openAccountRequestId);
            }
            json.put("sys_code", AccountUtils.getUserName(getContext()));
            json.put("mobile_no", phone);
            json.put("code", payCode);
            json.put("notify_url", "");
            json.put("discount", discount);
            json.put("type", openAccountType);
            //交易类型 JSAPI(公众号支付)、NATIVE(扫码支付)、APP(APP支付)、MWEB(H5支付)
            json.put("trade_type", "MWEB");

        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (type) {
            case PAY_TYPE_ALI:
                UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_021, QUERY_PHONE_PAY_ALI_PARAM, json, SDK_VERSION, PayActivity.this);

                break;
            case PAY_TYPE_WECHAT:

                UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_020, QUERY_PHONE_PAY_WECHAT_PARAM, json, SDK_VERSION, PayActivity.this);
                break;
        }
    }

    /**
     * 获取指定格式的时间
     *
     * @return
     */
    private String getTimeStr() {
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        return simpleDateFormat.format(curDate);
    }

    /**
     * 设置支付方式文字
     */
    private void initPayMethodTxt() {
        String strAliPay = getStringRes(R.string.pay_confirm_method_alipay);
        radioAliPay.setText(getSpan(strAliPay, 3, true));

        String strWechat = getStringRes(R.string.pay_confirm_method_wechat);
        radioWeChat.setText(getSpan(strWechat, 4, true));
    }

    private SpannableString getSpan(String str, int index, boolean enable) {
        SpannableString span = new SpannableString(str);
        span.setSpan(new TextAppearanceSpan(this, enable ? R.style.pay_radio_txt_title : R.style.pay_radio_txt_title_unenable), 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new TextAppearanceSpan(this, R.style.pay_radio_txt_content), index, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * 重置可用
     *
     * @param enable
     */
    private void resetPayMethodEnable(boolean enable) {
        radioAliPay.setEnabled(enable);
        radioWeChat.setEnabled(enable);
        radioAliPay.setChecked(false);
        radioWeChat.setChecked(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private int getColorRes(int id) {
        return getResources().getColor(id);
    }
}
