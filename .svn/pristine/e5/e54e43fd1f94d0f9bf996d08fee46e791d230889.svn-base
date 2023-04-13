package com.uyou.copenaccount.ui.pic.view;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uyou.copenaccount.R;


/**
 * Created by zdd on 2019/077/12.
 */

public class CameraProgressDialog extends Dialog {

    private View mRootView;
    private TextView txtProgress;

    public CameraProgressDialog(@NonNull Context context) {
        super(context, R.style.com_dialog_camera_progress);
        mRootView = LayoutInflater.from(context).inflate(R.layout.com_dialog_cameraprogress, null);

        setContentView(mRootView);

        txtProgress = (TextView) mRootView.findViewById(R.id.txt_progress);

    }

    public void setProgress(float progress) {
        if (txtProgress != null) {
            txtProgress.setText(String.format("%.2f", progress) + "%");
        }
    }

    public void noText() {
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
