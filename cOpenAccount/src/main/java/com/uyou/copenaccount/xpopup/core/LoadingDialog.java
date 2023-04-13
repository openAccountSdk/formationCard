package com.uyou.copenaccount.xpopup.core;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.TextView;

import com.uyou.copenaccount.R;


/**
 * Created by zdd on 2019/7/18.
 * <p>
 * Description:
 */
public class LoadingDialog extends CenterPopupView {

    private TextView tv_title;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_loading_dialog;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tv_title = findViewById(R.id.tv_title);
        if (title != null && tv_title != null) {
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(title);
        }
    }

    public void resetTitle(String title) {
        if (title != null && tv_title != null) {
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(title);
        }
    }

    private String title;

    public LoadingDialog setTitle(String title) {
        this.title = title;
        return this;
    }
}
