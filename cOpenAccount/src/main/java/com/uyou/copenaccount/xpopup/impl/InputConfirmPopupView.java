package com.uyou.copenaccount.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.xpopup.XPopup;
import com.uyou.copenaccount.xpopup.interfaces.OnCancelListener;
import com.uyou.copenaccount.xpopup.interfaces.OnInputConfirmListener;
import com.uyou.copenaccount.xpopup.util.XPopupUtils;


/**
 * Description: 带输入框，确定和取消的对话框
 * Create by dance, at 2018/12/16
 */
public class InputConfirmPopupView extends ConfirmPopupView implements View.OnClickListener {

    public InputConfirmPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_xpopup_center_impl_confirm;
    }

    AppCompatEditText tv_input;

    @Override
    protected void initPopupContent() {
        tv_input = findViewById(R.id.tv_input);
        tv_input.setVisibility(VISIBLE);
        super.initPopupContent();
        if (!TextUtils.isEmpty(hint)) {
            tv_input.setHint(hint);
        }
    }

    public void applyPrimaryColor() {
        super.applyPrimaryColor();
        XPopupUtils.setCursorDrawableColor(tv_input, XPopup.getPrimaryColor());
        tv_input.post(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable defaultDrawable = XPopupUtils.createBitmapDrawable(getResources(), tv_input.getMeasuredWidth(), Color.parseColor("#888888"));
                BitmapDrawable focusDrawable = XPopupUtils.createBitmapDrawable(getResources(), tv_input.getMeasuredWidth(), XPopup.getPrimaryColor());
                tv_input.setBackgroundDrawable(XPopupUtils.createSelector(defaultDrawable, focusDrawable));
            }
        });

    }

    OnCancelListener cancelListener;
    OnInputConfirmListener inputConfirmListener;

    public void setListener(OnInputConfirmListener inputConfirmListener, OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        this.inputConfirmListener = inputConfirmListener;
    }

    @Override
    public void onClick(View v) {
        if (v == tv_cancel) {
            if (cancelListener != null) cancelListener.onCancel();
            dismiss();
        } else if (v == tv_confirm) {
            if (inputConfirmListener != null)
                inputConfirmListener.onConfirm(tv_input.getText().toString().trim());
            if (popupInfo.autoDismiss) dismiss();
        }
    }
}
