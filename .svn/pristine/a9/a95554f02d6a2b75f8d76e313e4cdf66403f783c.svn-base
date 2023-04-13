package com.uyou.copenaccount.ui;


import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.QUERY_INCOME;
import static com.uyou.copenaccount.base.UCConstants.QUERY_PHONE_PAY_INFO;
import static com.uyou.copenaccount.base.UCConstants.QUERY_RECHARGE;
import static com.uyou.copenaccount.base.UCConstants.SDK_VERSION;
import static com.uyou.copenaccount.base.UCConstants.SHARE_CREDENTIAL;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_017;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_018;
import static com.uyou.copenaccount.base.UCConstants.THREAD_ID_019;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.adapter.RechargeAdapter;
import com.uyou.copenaccount.base.UBaseActivity;
import com.uyou.copenaccount.bean.RechargeBean;
import com.uyou.copenaccount.decoration.RechargeDecoration;
import com.uyou.copenaccount.model.NavPayModel;
import com.uyou.copenaccount.model.NavPhoneRechargeModel;
import com.uyou.copenaccount.response.QueryIncomeResponse;
import com.uyou.copenaccount.response.QueryPhonePayInfoResponse;
import com.uyou.copenaccount.response.QueryRechargeResponse;
import com.uyou.copenaccount.utils.AccountUtils;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.RegexUtils;
import com.uyou.copenaccount.utils.net.UYouHttpClient;
import com.uyou.copenaccount.view.rv.BaseQuickAdapter;
import com.uyou.copenaccount.xpopup.util.KeyboardUtils;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * Created by zdd on 2019/03/19
 * <p>
 * Description: 话费充值 activity
 */
public class PhoneRechargeActivity extends UBaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.pay_edit_phone)
    EditText editPhone;

    @BindView(R2.id.pay_layout_container)
    LinearLayout layoutRechargeItem;
    @BindView(R2.id.pay_txt_select_price)
    TextView txtSelectPrice;
    @BindView(R2.id.pay_txt_recharge_tip)
    TextView txtRechargeTip;
    @BindView(R2.id.pay_recycler_recharge)
    RecyclerView recyclerRecharge;

    @BindView(R2.id.pay_layout_income)
    LinearLayout layoutIncome;
    @BindView(R2.id.pay_txt_income_all)
    TextView txtIncomeAll;
    @BindView(R2.id.pay_txt_income_today)
    TextView txtIncomeToday;
    @BindView(R2.id.pay_txt_income_yesterday)
    TextView txtIncomeYesterday;

    @BindView(R2.id.pay_btn_recharge)
    Button btnRecharge;

    RechargeAdapter adapterRecharge;
    RechargeDecoration decorationRecharge;


    private String mOpenAccountRequestId;
    private String mICCID;
    private String mPayType;
    private String isPay;
    private boolean isOnLine;
    // true为未预录入
    private boolean isNotPreRecordCard;

    private String mGiftFromMoney;
    private String mGiftMoney;

    private RechargeBean mSelectRechargeBean;
    private String openId;
    private String phoneNum;
    //支付金额
    private String payAmount;

    @Override
    public String getPageTitle() {
        return getStringRes(R.string.pay_title_phone_recharge);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.pay_activity_phone_recharge;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String credential = AppConfigs.getStringSF(getContext(), SHARE_CREDENTIAL);
        // 登录状态不显示充值收益, 包含加盟入网用户和商城用户
        // 未登录状态 查询显示充值收益
        if (TextUtils.isEmpty(credential)) {
            try {
                // 创建客户资料并校验证卡数
                JSONObject js = new JSONObject();
                UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_017, QUERY_INCOME, js, SDK_VERSION, PhoneRechargeActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Object obj = getIntent().getSerializableExtra(ACTION_DATA);

        if (obj != null && obj instanceof NavPhoneRechargeModel) {
            NavPhoneRechargeModel model = (NavPhoneRechargeModel) obj;
            mICCID = model.getICCID();
            isOnLine = model.getIsOnLine();
            isPay = model.getIsPay();
            mPayType = model.getPayType();
            phoneNum = model.getPhoneNum();
            mOpenAccountRequestId = model.getRequestId();
            isNotPreRecordCard = model.isNotPreRecordCard();
            openId = model.getOpenId();

        }
        decorationRecharge = new RechargeDecoration(PhoneRechargeActivity.this);
        adapterRecharge = new RechargeAdapter();

        GridLayoutManager managerRecharge = new GridLayoutManager(PhoneRechargeActivity.this, 2);
        recyclerRecharge.setLayoutManager(managerRecharge);
        recyclerRecharge.addItemDecoration(decorationRecharge);
        recyclerRecharge.setAdapter(adapterRecharge);
        adapterRecharge.setOnItemClickListener(this);

        if (!TextUtils.isEmpty(phoneNum)) {
            // 存在号码 就不能再修改, 直接查询
            editPhone.setEnabled(false);
            editPhone.setText(phoneNum);
            //queryRechargeItem();
        } else {
            // 添加IME_ACTION事件, 使点击可进行搜索
            editPhone.setOnEditorActionListener((view, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String phone = editPhone.getText().toString().trim();
                    if (phone.length() == 11) {
                        queryRechargeItem();
                    }
                    KeyboardUtils.hideSoftInput(editPhone);
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * 查询充值信息
     */
    private void queryRechargeItem() {
        String form = "";
        if ("1".equals(mPayType)) { // 成卡1
            form = "1";
        }
        if (isOnLine) { // 白卡2
            form = "2";
        }
        if (isNotPreRecordCard) { // 未预录入成卡3
            form = "3";
        }

        try {
            JSONObject params = new JSONObject();
            params.put("is_open", isPay);
            params.put("ICCID", mICCID);
            params.put("form", form);
            params.put("mobile_no", editPhone.getText().toString().trim());
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_018, QUERY_RECHARGE, params, SDK_VERSION, PhoneRechargeActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * edit change 时间
     *
     * @param charSequence
     */
    @OnTextChanged(R2.id.pay_edit_phone)
    public void onPhoneChange(CharSequence charSequence) {
        mSelectRechargeBean = null;
        if (charSequence.toString().trim().length() != 11) {
            layoutRechargeItem.setVisibility(View.GONE);
        } else {
            // 隐藏键盘 查询充值信息
            KeyboardUtils.hideSoftInput(editPhone);
            queryRechargeItem();
        }
    }

    /**
     * 立即充值, 页面跳转
     */
    @OnClick(R2.id.pay_btn_recharge)
    void toRecharge() {
        String phone = editPhone.getText().toString().trim();
        if (!RegexUtils.checkMobile(phone)) {
            inputError(editPhone, R.string.common_err_phone_error);
            return;
        }
        if (mSelectRechargeBean == null) {
            showToast(getStringRes(R.string.pay_toast_select_recharge));
            return;
        }

        btnRecharge.setEnabled(false);
        NavPayModel model = new NavPayModel();
        model.setICCID(mICCID);

        model.setPayType(mPayType);
        model.setPriceCode(mSelectRechargeBean.getCode());
        model.setOpenAccountRequestId(mOpenAccountRequestId);
        model.setIsPayed(isPay);
        model.setNotPreRecordCard(isNotPreRecordCard);
        model.setPhone(phone);

        model.setPriceVal(mSelectRechargeBean.getValue());//充值金额
        model.setOpenId(openId);
        model.setPayAmount(payAmount);
        String username = AccountUtils.getUserName(getContext());
        // TODO: 2022/12/28 与H5交互 使用h5支付
        Intent intent = new Intent(this, H5OpenAccountActivity.class);
        intent.putExtra(ACTION_DATA, model);
        startActivity(intent);
    }

    @Override
    protected void onHttpResponseSuccess(int threadId, JSONObject json, String response) {

        Gson gson = new Gson();
        switch (threadId) {
            case THREAD_ID_017:
                QueryIncomeResponse queryIncomeResponse = gson.fromJson(response, QueryIncomeResponse.class);
                QueryIncomeResponse.Data model = queryIncomeResponse.getData();
                layoutIncome.setVisibility(View.VISIBLE);
                txtIncomeAll.setText(model.getAllDayMoney());
                txtIncomeToday.setText(model.getTodayMoney());
                txtIncomeYesterday.setText(model.getYesterdayMoney());
                break;
            case THREAD_ID_018:
                QueryRechargeResponse queryRechargeResponse = gson.fromJson(response, QueryRechargeResponse.class);
                layoutRechargeItem.setVisibility(View.VISIBLE);

                txtRechargeTip.setText(queryRechargeResponse.getData().getGiftRule());
                mGiftFromMoney = queryRechargeResponse.getData().getGiftFromMoney();
                mGiftMoney = queryRechargeResponse.getData().getGiftMoney();

                List<RechargeBean> list = queryRechargeResponse.getData().getRechargeAmountMoney();
                Collections.sort(list, new PriceCompare());
                adapterRecharge.setNewData(list);
                break;
            case THREAD_ID_019:
                //查询话费充值金额返回结果
                QueryPhonePayInfoResponse queryPhonePayInfoResponse = gson.fromJson(response, QueryPhonePayInfoResponse.class);

                if (queryPhonePayInfoResponse.getData().getPay_value() != null) {
                    payAmount = queryPhonePayInfoResponse.getData().getPay_value();// 获取应付金额
                }

                break;
        }
    }

    @Override
    protected void onHttpCodeError(int threadId, int code, String message) {
        super.onHttpCodeError(threadId, code, message);
        if (threadId == THREAD_ID_018) {
            layoutRechargeItem.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position >= 0 && position < adapterRecharge.getData().size()) {
            List<RechargeBean> list = adapterRecharge.getData();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChecked(position == i);
            }
            adapterRecharge.notifyDataSetChanged();

            mSelectRechargeBean = list.get(position);

            if (!TextUtils.isEmpty(mGiftFromMoney)) {
                try {
                    if (Double.parseDouble(mSelectRechargeBean.getName().substring(0, mSelectRechargeBean.getName().length() - 1))
                            < Double.parseDouble(mGiftFromMoney)) {
                        showToast("充" + mGiftFromMoney + "元赠" + mGiftMoney + "元");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            queryRechargeData();
        }
    }

    private void queryRechargeData() {
        // 请求手机充值缴费信息
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
            json.put("mobile_no", phoneNum);
            json.put("pay_code", mSelectRechargeBean.getCode());
            json.put("pay_type", mPayType);
            json.put("is_pay", isPay);
            json.put("ICCID", mICCID);
            UYouHttpClient.getInstance().requestPost(getContext(), this, THREAD_ID_019, QUERY_PHONE_PAY_INFO, json, SDK_VERSION, PhoneRechargeActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 价格对比
     */
    private class PriceCompare implements Comparator<RechargeBean> {

        @Override
        public int compare(RechargeBean lhs, RechargeBean rhs) {
            if (lhs.getMoney() == rhs.getMoney()) {
                return 0;
            }
            if (lhs.getValue() > rhs.getValue()) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnRecharge.setEnabled(true);
    }


}
