package com.uyou.copenaccount.base;


import static com.uyou.copenaccount.base.UCConstants.EVENT_CLOSE_SDK;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR_401;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR_403;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR_404;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR_500;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR_PARSE;
import static com.uyou.copenaccount.base.UCConstants.NET_ERROR_S;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uyou.copenaccount.R;
import com.uyou.copenaccount.dialog.LoadingDialog;
import com.uyou.copenaccount.utils.PhoneUtils;
import com.uyou.copenaccount.utils.StatusBarUtil;
import com.uyou.copenaccount.utils.ULogger;
import com.uyou.copenaccount.utils.net.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zdd on 2020/1/2.
 * <p>
 * Description: UBaseActivity
 */
public abstract class UBaseActivity extends AppCompatActivity implements HttpRequest {

    private LoadingDialog mLoading;

    private CloseBroadcastReceiver receiverClose;

    protected View viewBack;

    protected boolean canBack = true;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout(savedInstanceState));
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置亮色模式
            StatusBarUtil.setStatusColor(this, true);
        }

        // 设置back键事件
        viewBack = findViewById(R.id.ucommon_common_title_back);
        if (viewBack != null) {
            viewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }


        initBeforeData();
        initData(savedInstanceState);

        // 若存在title 且 titleStr不为空, 设置标题
        TextView txtTitle = findViewById(R.id.ucommon_common_title_content);
        if (txtTitle != null) {
            String title = getPageTitle();
            if (!TextUtils.isEmpty(title))
                txtTitle.setText(title);
        }

        // 注册广播关闭页面/开卡成功关闭页面
        receiverClose = new CloseBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(EVENT_CLOSE_SDK);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiverClose, filter);
    }

    protected void initBeforeData() {

    }

    /**
     * 本地广播关闭页面/开卡成功关闭页面
     */
    private class CloseBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isActivityClose()) {
                return;
            }
            String action = intent.getAction();
            if (EVENT_CLOSE_SDK.equals(action)) {
                finish();
            }
        }
    }

    /**
     * 隐藏返回按钮 并不允许后退
     */
    protected void hideBackAndCantBack() {
        canBack = false;
        if (viewBack != null) {
            viewBack.setVisibility(View.GONE);
        }
    }

    /**
     * 获取标题名
     *
     * @return 标题名
     */
    protected String getPageTitle() {
        return "";
    }

    /**
     * 初始化layout
     */
    protected abstract int initLayout(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public void onBackPressed() {
        if (canBack) {
            super.onBackPressed();
        }
    }

    protected void onHttpResponseSuccess(int threadId,JSONObject object, String response) {

    }
    protected void onHttpResponseSuccess(int threadId,JSONObject object, String response,String path) {

    }

    protected void onHttpResponse201(int threadId, JSONObject json) {

    }

    @Override
    public void onHttpStart(int threadId) {
        showLoading();
    }

    @Override
    public void onHttpResponse(int threadId, String response,String path) {
        if ("".equals(response) || "error".equals(response)) {
            onHttpCodeError(threadId, -2, NET_ERROR_500);
            showToast(NET_ERROR_500);
            return;
        }
        try {
            if (!TextUtils.isEmpty(response)) {
                JSONObject json = new JSONObject(response);
                if (json.has("code")) {
                    int code = json.getInt("code");
                    String msg = "";
                    if (json.has("message")) {
                        msg = json.getString("message");
                    }
                    if (code == 200) {
                        // 请求成功
                        onHttpResponseSuccess(threadId, json,response,path);
                    } else if (code == 201) {
                        onHttpResponse201(threadId, json);
                    } else if (code == 500) {
                        // 弹窗并退出sdk
                        showMessage(msg, true);
                        onHttpCodeError(threadId, code, msg);
                    } else if (code == 800) {
                        // 弹窗
                        showMessage(msg);
                        onHttpCodeError(threadId, code, msg);
                    } else {
                        // 其他的弹toast
                        showToast(msg);
                        onHttpCodeError(threadId, code, msg);
                    }
                } else {
                    // 服务器返回的错误可能是这些
                    if (json.has("status")) {
                        int status = json.getInt("status");
                        String msg = json.has("error") ? json.getString("error") : NET_ERROR_S;
                        if (status == 500) {
                            msg = NET_ERROR_500;
                        } else if (status == 404) {
                            msg = NET_ERROR_404;
                        } else if (status == 403) {
                            msg = NET_ERROR_403;
                        } else if (status == 401) {
                            msg = NET_ERROR_401;
                        }
                        onHttpCodeError(threadId, status, msg);
                        showToast(msg);
                    } else {
                        onHttpCodeError(threadId, -1, NET_ERROR_PARSE);
                        showToast(NET_ERROR_PARSE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            onHttpCodeError(threadId, -2, NET_ERROR_PARSE);
            showToast(NET_ERROR_PARSE);
        }
    }

    @Override
    public void onHttpResponse(int threadId, String response) {
        if ("".equals(response) || "error".equals(response)) {
            onHttpCodeError(threadId, -2, NET_ERROR_500);
            showToast(NET_ERROR_500);
            return;
        }
        try {
            if (!TextUtils.isEmpty(response)) {
                JSONObject json = new JSONObject(response);
                if (json.has("code")) {
                    int code = json.getInt("code");
                    String msg = "";
                    if (json.has("message")) {
                        msg = json.getString("message");
                    }
                    if (code == 200) {
                        // 请求成功
                        onHttpResponseSuccess(threadId, json,response);
                    } else if (code == 201) {
                        onHttpResponse201(threadId, json);
                    } else if (code == 500) {
                        // 弹窗并退出sdk
                        showMessage(msg, true);
                        onHttpCodeError(threadId, code, msg);
                    } else if (code == 800) {
                        // 弹窗
                        showMessage(msg);
                        onHttpCodeError(threadId, code, msg);
                    } else {
                        // 其他的弹toast
                        showToast(msg);
                        onHttpCodeError(threadId, code, msg);
                    }
                } else {
                    // 服务器返回的错误可能是这些
                    if (json.has("status")) {
                        int status = json.getInt("status");
                        String msg = json.has("error") ? json.getString("error") : NET_ERROR_S;
                        if (status == 500) {
                            msg = NET_ERROR_500;
                        } else if (status == 404) {
                            msg = NET_ERROR_404;
                        } else if (status == 403) {
                            msg = NET_ERROR_403;
                        } else if (status == 401) {
                            msg = NET_ERROR_401;
                        }
                        onHttpCodeError(threadId, status, msg);
                        showToast(msg);
                    } else {
                        onHttpCodeError(threadId, -1, NET_ERROR_PARSE);
                        showToast(NET_ERROR_PARSE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            onHttpCodeError(threadId, -2, NET_ERROR_PARSE);
            showToast(NET_ERROR_PARSE);
        }
    }

    @Override
    public void onHttpError(int threadId, String error) {
        showToast(error);
    }

    @Override
    public void onHttpFinished(int threadId) {
        hideLoading();
    }

    protected void onHttpCodeError(int threadId, int code, String message) {

    }

    /**
     * 展示loading
     */
    protected void showLoading() {
        showLoading(null);
    }

    /**
     * 展示loading
     */
    protected void showLoading(String msg) {
        if (mLoading == null) {
            mLoading = new LoadingDialog(getContext());
        }
        if (TextUtils.isEmpty(msg)) {
            mLoading.text();
        } else {
            mLoading.text(msg);
        }
        lazyCloseLoading();
        if (mLoading.isShowing()) {
            return;
        }
        if (isActivityClose()) {
            return;
        }
        mLoading.show();
    }

    /**
     * 展示带进度的loading
     *
     * @param progress 进度
     */
    protected void showProgressLoading(float progress) {
        if (mLoading == null) {
            mLoading = new LoadingDialog(getContext());
        }
        mLoading.progress(progress);
        lazyCloseLoading();
        if (mLoading.isShowing()) {
            return;
        }
        mLoading.show();
    }

    /**
     * 延时关闭
     */
    private void lazyCloseLoading() {
        if (mHandler != null) {
            // 防止未关闭 120s后主动关闭
            mLoadingTime = System.currentTimeMillis();
            mHandler.sendEmptyMessageDelayed(0, 120000);
        }
    }

    /**
     * 隐藏loading
     */
    protected void hideLoading() {
        if (mLoading != null) {
            if (mLoading.isShowing()) {
                mLoading.dismiss();
            }
        }
    }

    protected Context getContext() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    protected String getStringRes(int res) {
        return getResources().getString(res);
    }

    protected String getStringRes(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 当前activity是否正在关闭/已关闭
     *
     * @return
     */
    protected boolean isActivityClose() {
        if (isFinishing()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    private InputMethodManager mInputManager;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (mInputManager == null) {
                    mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                }
                mInputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mHandler.removeMessages(0);
        if (receiverClose != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiverClose);
        }
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        super.onDestroy();
    }

    protected void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private long mLoadingTime;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (System.currentTimeMillis() - mLoadingTime >= 119500) {
                if (!isActivityClose()) {
                    hideLoading();
                }
            }
        }
    };

    protected void showMessage(String msg) {
        showMessage(msg, false);
    }

    protected void showMessage(String msg, final boolean isClose) {
        if (msg != null) {
            String btn = isClose ? "退出" : "确定";
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage(msg)
                    .setTitle("提示")
                    .setPositiveButton(btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (isClose) {
                                Intent intent = new Intent();
                                intent.setAction(EVENT_CLOSE_SDK);
                                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                            }
                        }
                    })
                    .setNegativeButton("", null)
                    .create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            if (isActivityClose()) {
                return;
            }
            dialog.show();
        }
    }

    protected void logger(String msg) {
        if (msg != null) {
            ULogger.e(getClass().getSimpleName(), msg);
        }
    }

    protected void threadSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {

        }
    }

    protected String getEditTxt(TextView editText) {
        if (editText == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }

    protected void setEditTxt(TextView editText, String txt) {
        if (editText != null && txt != null) {
            editText.setText(txt);
        }
    }

    protected String getJsonString(JSONObject json, String key) throws JSONException {
        if (json == null || TextUtils.isEmpty(key)) {
            return null;
        }
        if (json.has(key) && !json.isNull(key)) {
            return json.getString(key);
        }
        return null;
    }

    protected int getJsonInt(JSONObject json, String key) throws JSONException {
        if (json == null || TextUtils.isEmpty(key)) {
            return 0;
        }
        if (json.has(key) && !json.isNull(key)) {
            return json.getInt(key);
        }
        return 0;
    }

    protected void inputError(EditText view, int message) {
        Context context = null;
        if (view != null) {
            context = view.getContext();
        } else {
            return;
        }
        showToast(context.getResources().getString(message));
        view.requestFocus();
        PhoneUtils.showSoftKeyboard(context, view);
    }
}
