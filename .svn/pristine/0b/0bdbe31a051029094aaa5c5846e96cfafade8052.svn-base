package com.uyou.copenaccount.dialog.down;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;

import com.uyou.copenaccount.R;


/**
 * Created by zdd on 2019/6/13.
 * <p>
 * Description:
 */
public class DownloadDialog extends AlertDialog {

    public DownloadDialog(@NonNull Context context) {
        super(context);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setTitle("下载插件");

        setProgress(0);
    }

    public void setProgress(int progress) {
        setMessage(getContext().getResources().getString(R.string.com_dialog_downloading, progress));
    }

}
