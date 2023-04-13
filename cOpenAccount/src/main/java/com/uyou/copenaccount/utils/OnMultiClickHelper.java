package com.uyou.copenaccount.utils;

import android.view.View;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description:
 */
public class OnMultiClickHelper implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 300;
    private long lastClickTime = 0;

    private OnMultiClickListener listener;

    public OnMultiClickHelper(OnMultiClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            if (listener != null) {
                listener.onMultiClick(v);
            }
        }
    }

    public interface OnMultiClickListener {
        void onMultiClick(View v);
    }
}
