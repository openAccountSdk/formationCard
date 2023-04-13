package com.uyou.copenaccount.dialog;


import static com.uyou.copenaccount.utils.StringUtils.setViewText;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.xpopup.core.BasePopupView;
import com.uyou.copenaccount.xpopup.core.CenterPopupView;


/**
 * Created by zdd on 2019/3/18.
 * <p>
 * Description: confirm 通用类
 */
public class BaseConfirmDialog extends CenterPopupView {

    protected Context mContext;

    protected TextView txtTitle;
    protected TextView txtContent;
    protected TextView txtCancel;
    protected TextView txtConfirm;

    protected OnClickListener mListener;

    public BaseConfirmDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        txtTitle = (TextView) centerPopupContainer.findViewById(R.id.tv_title);
        txtContent = (TextView) centerPopupContainer.findViewById(R.id.tv_content);
        txtCancel = (TextView) centerPopupContainer.findViewById(R.id.tv_cancel);
        txtConfirm = (TextView) centerPopupContainer.findViewById(R.id.tv_confirm);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_dialog_confirm;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

    public BaseConfirmDialog title(String title) {
        setViewText(txtTitle, title);
        return this;
    }

    public BaseConfirmDialog content(String content) {
        setViewText(txtContent, content);
        return this;
    }

    public BaseConfirmDialog cancel(String cancel) {
        setViewText(txtCancel, cancel);
        return this;
    }

    public BaseConfirmDialog hideCancelBtn() {
        txtCancel.setVisibility(GONE);
        return this;
    }

    public BaseConfirmDialog confirm(String confirm) {
        setViewText(txtConfirm, confirm);
        return this;
    }

    public BaseConfirmDialog listener(OnClickListener listener) {
        mListener = listener;
        return this;
    }

    public BaseConfirmDialog confirmClick() {
        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onConfirmClick(BaseConfirmDialog.this, v);
                }
            }
        });
        return this;
    }

    public BaseConfirmDialog cancelClick() {
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCancelClick(BaseConfirmDialog.this, v);
                }
            }
        });
        return this;
    }

    public BaseConfirmDialog hideCancel() {
        txtCancel.setVisibility(GONE);
        return this;
    }

    public BaseConfirmDialog hideConfirm() {
        txtConfirm.setVisibility(GONE);
        return this;
    }

    protected String getStringRes(int res) {
        if (mContext != null) {
            return mContext.getResources().getString(res);
        }
        return "";
    }

    @Override
    public int getAnimationDuration() {
        return 200;
    }

    public interface OnClickListener {
        void onCancelClick(BasePopupView popupView, View view);

        void onConfirmClick(BasePopupView popupView, View view);
    }
}
