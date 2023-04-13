package com.uyou.copenaccount.ui;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.model.OpenAccountAction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

public class ReceiptActivity extends UOpenBaseActivity {
    @BindView(R2.id.tv_clientSName)
    TextView tv_clientSName;
    @BindView(R2.id.tv_customer_type)
    TextView tv_customer_type;
    @BindView(R2.id.tv_paperwork_type)
    TextView tv_paperwork_type;
    @BindView(R2.id.tv_id_number)
    TextView tv_id_number;
    @BindView(R2.id.tv_address)
    TextView tv_address;
    @BindView(R2.id.tv_user_number)
    TextView tv_user_number;
    @BindView(R2.id.tv_reception_time)
    TextView tv_reception_time;
    @BindView(R2.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R2.id.tv_product)
    TextView tv_product;
    @BindView(R2.id.content_tariff_explanation)
    TextView content_tariff_explanation;
    @BindView(R2.id.bus_btn_confirm)
    Button bus_btn_confirm;


    @Override
    public String getPageTitle() {
        return getStringRes(R.string.bus_title_open_receipt);
    }

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.bus_activity_receipt;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        hideBackAndCantBack();
        Object object = getIntent().getSerializableExtra(ACTION_DATA);

        if (object != null) {
            if (object instanceof OpenAccountAction) {
                OpenAccountAction mAction = (OpenAccountAction) object;
                if (mAction.openAccountModel != null) {
                    tv_clientSName.setText(String.format(getResources().getString(R.string.bus_clientSName), mAction.openAccountModel.getName()));
                    if ("1".equals(mAction.userType)) {
                        tv_customer_type.setText(String.format(getResources().getString(R.string.bus_customer_type), "企业客户"));
                    } else {
                        tv_customer_type.setText(String.format(getResources().getString(R.string.bus_customer_type), "个人客户"));
                    }
                    String certNum = mAction.openAccountModel.getCert_num();
                    if (certNum.startsWith("810000") || certNum.equals("820000") || certNum.equals("830000")) {
                        tv_paperwork_type.setText(String.format(getResources().getString(R.string.bus_type_of_certificate), "居住证"));
                    } else {
                        tv_paperwork_type.setText(String.format(getResources().getString(R.string.bus_type_of_certificate), "身份证"));
                    }
                    tv_id_number.setText(String.format(getResources().getString(R.string.bus_id_number), mAction.openAccountModel.getCert_num()));

                    tv_address.setText(String.format(getResources().getString(R.string.bus_address), mAction.openAccountModel.getAddress()));

                    tv_user_number.setText(String.format(getResources().getString(R.string.bus_user_number), mAction.openAccountModel.getPhone_num()));

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); //设置时间格式
                    Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
                    String createDate = formatter.format(curDate);   //格式转换
                    tv_reception_time.setText(String.format(getResources().getString(R.string.bus_reception_time), createDate));

                    tv_order_num.setText(String.format(getResources().getString(R.string.bus_order_num), mAction.openAccountModel.getOpen_id()));

                    tv_product.setText(String.format(getResources().getString(R.string.bus_product), mAction.openAccountModel.getProductName()));

                    content_tariff_explanation.setText(mAction.remark);


                }
            }
        }
        /*
          CountDownTimer 实现倒计时
         */
        CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int s = (int) (millisUntilFinished / 1000);
                if (bus_btn_confirm != null) {
                    bus_btn_confirm.setText(String.format(getResources().getString(R.string.bus_open_form_agree), s));
                }
            }

            @Override
            public void onFinish() {
                if (bus_btn_confirm != null) {
                    bus_btn_confirm.setEnabled(true);
                }
            }
        };
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        countDownTimer.start();
        bus_btn_confirm.setOnClickListener(v -> finish());
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