package com.uyou.copenaccount.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uyou.copenaccount.R;


/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description: loading加载框
 */
public class LoadingDialog extends AppCompatDialog {

    private View mRootView;
    private TextView txtProgress;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.ucommon_style_common_dialog);
        mRootView = LayoutInflater.from(context).inflate(R.layout.ucommon_dialog_loading, null);

        setContentView(mRootView);

        txtProgress = (TextView) mRootView.findViewById(R.id.uyou_txt_loading);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void progress(float progress) {
        if (txtProgress != null) {
            txtProgress.setText(String.format("%.2f", progress) + "%");
        }
    }

    public void text() {
        if (txtProgress != null) {
            txtProgress.setText("请稍候...");
        }
    }

    public void text(String s) {
        if (txtProgress != null && s != null) {
            txtProgress.setText(s);
        }
    }

}
