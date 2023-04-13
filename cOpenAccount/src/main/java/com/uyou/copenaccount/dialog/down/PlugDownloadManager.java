package com.uyou.copenaccount.dialog.down;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import androidx.core.content.FileProvider;
import android.text.TextUtils;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.plug.IPlugDownloadManager;
import com.uyou.copenaccount.progress.ProgressInfo;
import com.uyou.copenaccount.progress.ProgressListener;
import com.uyou.copenaccount.progress.ProgressManager;
import com.uyou.copenaccount.utils.AppConfigs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zdd on 2019/6/13.
 * <p>
 * Description:
 */
public class PlugDownloadManager implements IPlugDownloadManager {

    private static final int MESSAGE_PROGRESS = 2;
    private static final int MESSAGE_ERROR = 3;
    private static final int MESSAGE_START = 0;
    private static final int MESSAGE_FINISH = 1;
    private static final int REQUEST_CODE_APP_INSTALL = 99;

    private Activity mActivity;
    private Context mContext;

    // apk下载路径
    private String apk_url = "";
    // apk保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String apk_dir = "";
    // apk保存文件名
    private String apk_Name = "";

    private MyHandler mHandler;

    private DownloadDialog mDialog;

    public PlugDownloadManager(Activity activity, String url, String apkName) {
        this.mActivity = activity;
        this.apk_url = url;
        this.apk_Name = apkName;
        if (mActivity == null) {
            return;
        }
        this.mContext = activity.getApplicationContext();

        if (!hasInstallPermission()) {
            startToSettingPermission();
            return;
        }

        preInstall();
    }

    /**
     * 安装前初始化
     */
    private void preInstall() {
        mHandler = new MyHandler();
        mDialog = new DownloadDialog(mActivity);
        mDialog.show();
        initAPKDir();
        downloadApk();
    }

    /**
     * 检测是否拥有安装未知来源应用的权限
     *
     * @return
     */
    private boolean hasInstallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mContext.getPackageManager().canRequestPackageInstalls();
        } else {
            return true;
        }
    }

    private void startToSettingPermission() {
        Uri packageURI = Uri.parse("package:" + mContext.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        mActivity.startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }

    /**
     *
     */
    private void initAPKDir() {
        if (mContext == null) {
            return;
        }
        File apkDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (apkDir != null) {
            apk_dir = apkDir.getAbsolutePath();
        }
        if (TextUtils.isEmpty(apk_dir)) {
            apk_dir = mContext.getCacheDir().getAbsolutePath() + File.separator + "download";
        }
        File destDir = new File(apk_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
    }

    private void downloadApk() {
        apk_url = ProgressManager.getInstance().addDiffResponseListenerOnSameUrl(apk_url, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                if (mHandler != null) {
                    Message message = new Message();
                    message.what = MESSAGE_PROGRESS;
                    message.arg1 = progressInfo.getPercent();
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onError(long id, Exception e) {
            }
        });
        if (mContext == null) {
            return;
        }
        mHandler.sendEmptyMessage(MESSAGE_START);


        //启动分线程下载数据，显示下载进度
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1 得到链接对象
                    URL url = new URL(apk_url);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(10000);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream is = connection.getInputStream();
                        writeFile(is, new File(apk_dir, apk_Name));
                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(MESSAGE_ERROR);
                }

            }
        }).start();
    }

    /**
     * 将输入流写入文件
     */
    private void writeFile(InputStream inputString, File file) {
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();
            mHandler.sendEmptyMessage(MESSAGE_FINISH);
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MESSAGE_ERROR);
        }
    }

    public class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MESSAGE_START:// 开始下载
                    if (mDialog != null) {
                        mDialog.setProgress(0);
                    }
                    break;
                case MESSAGE_PROGRESS:// 正在下载
                    int x = msg.arg1;
                    if (mDialog != null) {
                        mDialog.setProgress(x);
                    }
                    break;
                case MESSAGE_FINISH:// 下载完成
                    dismissDialog();


                    File apkFile = new File(apk_dir, apk_Name);
                    if (mContext == null) {
                        return;
                    }
                    if (!apkFile.exists()) {
                        AppConfigs.showToast(mContext, getStringRes(R.string.com_download_error_no_file));
                        return;
                    }

                    installApk(apkFile);
                    break;
                case MESSAGE_ERROR:// 下载出错
                    dismissDialog();
                    if (mContext == null) {
                        return;
                    }
                    AppConfigs.showToast(mContext, getStringRes(R.string.com_download_error));
                    break;
                default:
                    break;
            }

        }
    }

    private void installApk(File apkFile) {
        if (mContext == null) {
            return;
        }
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(apkFile);
        //7.0以上版本兼容的问题。
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(mContext.getApplicationContext(), mContext.getPackageName() + ".provider", apkFile);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }

        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(installIntent);
    }

    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void clear() {
        dismissDialog();
        mDialog = null;
        mActivity = null;
        mHandler = null;
        mContext = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_APP_INSTALL) {
            if (resultCode == Activity.RESULT_OK) {
                preInstall();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (mContext != null) {
                    AppConfigs.showToast(mContext, getStringRes(R.string.common_install_need_permission));
                }
            }
        }
    }

    private String getStringRes(int id) {
        if (mContext == null) {
            return "";
        }
        return mContext.getResources().getString(id);
    }
}
