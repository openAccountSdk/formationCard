package com.uyou.copenaccount.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.view.View;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.utils.AppConfigs;


/**
 * Created by zdd on 2019/4/1.
 * <p>
 * Description: 权限设置弹窗
 */
public class PermissionDialog extends BaseConfirmDialog {

    private String mContent;

    public PermissionDialog(@NonNull Context activity, String content) {
        super(activity);
        this.mContent = content;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();

        title(getStringRes(R.string.common_permission_apply));
        cancel(getStringRes(R.string.common_to_setting));
        confirm(getStringRes(R.string.common_cancel));
        content(mContent);
        // 这里确定和取消是反的
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    AppConfigs.showToast(mContext, getStringRes(R.string.common_open_setting_fail));
                }
                dismiss();
                if (mListener != null) {
                    mListener.onConfirmClick(PermissionDialog.this, view);
                }
            }
        });

        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mListener != null) {
                    mListener.onCancelClick(PermissionDialog.this, view);
                }
            }
        });
    }

}
