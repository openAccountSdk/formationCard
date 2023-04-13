package com.uyou.copenaccount.ui;

import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Button;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UOpenBaseActivity;

import butterknife.BindView;

/**
 * Created by zdd on 2019/3/28.
 * <p>
 * Description: 入网协议 activity
 */
public class NetProtocolActivity extends UOpenBaseActivity {

    @BindView(R2.id.web_protocol)
    WebView webProtocol;
    @BindView(R2.id.bus_btn_confirm)
    Button btn_confirm;
    private String title;


    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.bus_activity_protocol;
    }

    protected String getPageTitle() {
        return title;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(ACTION_DATA);
        title = getIntent().getStringExtra("title");

        Button btn_confirm = findViewById(R.id.bus_btn_confirm);
        hideBackAndCantBack();
        if (!TextUtils.isEmpty(url)) {
            webProtocol.loadUrl(url);
        }

        /*
          CountDownTimer 实现倒计时
         */
        CountDownTimer countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int s = (int) (millisUntilFinished / 1000);
                btn_confirm.setText(String.format(getResources().getString(R.string.bus_open_form_agree), s));
            }

            @Override
            public void onFinish() {
                if (btn_confirm != null) {
                    btn_confirm.setEnabled(true);
                }
            }
        };
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        countDownTimer.start();

        btn_confirm.setOnClickListener(v -> finish());
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
