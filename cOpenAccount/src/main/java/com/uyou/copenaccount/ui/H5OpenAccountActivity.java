package com.uyou.copenaccount.ui;


import static com.uyou.copenaccount.base.UCConstants.ACTION_DATA;
import static com.uyou.copenaccount.base.UCConstants.ACTION_PATH;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.google.gson.JsonSyntaxException;
import com.uyou.copenaccount.BuildConfig;
import com.uyou.copenaccount.R;
import com.uyou.copenaccount.R2;
import com.uyou.copenaccount.base.UCConstants;
import com.uyou.copenaccount.base.UOpenBaseActivity;
import com.uyou.copenaccount.dialog.BaseConfirmDialog;
import com.uyou.copenaccount.model.NavPayModel;
import com.uyou.copenaccount.utils.AccountUtils;
import com.uyou.copenaccount.utils.AppConfigs;
import com.uyou.copenaccount.utils.XORUtils;
import com.uyou.copenaccount.xpopup.XPopup;
import com.uyou.copenaccount.xpopup.core.BasePopupView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by zdd on 2019/9/18.
 * <p>
 * Description:
 */
public class H5OpenAccountActivity extends UOpenBaseActivity {

    private static final int CODE_SCAN_ICCID = 99;
    private static final int CAMERA_RESULT_CODE = 98;

    @BindView(R2.id.open_webview)
    WebView mWebView;
    @BindView(R2.id.progress_bar)
    ProgressBar mProgressBar;

    //    Uri photoUri;
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessageArray;
    private int mPayPriceValue;
    private String mPhone;
    private String openId;
    private String payAmount;

    private String reloadUrl;

    @Override
    protected int initLayout(Bundle savedInstanceState) {
        return R.layout.openh5_activity_h5_open_account;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        if (BuildConfig.DEBUG) {
            // 测试环境默认开启调试
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        Object obj = getIntent().getSerializableExtra(ACTION_DATA);
        if (obj instanceof NavPayModel) {
            NavPayModel model = (NavPayModel) obj;
            mPayPriceValue = model.getPriceVal();
            mPhone = model.getPhone();
            openId = model.getOpenId();
            payAmount = model.getPayAmount();
        }
        mProgressBar.setMax(100);
        try {
            mWebView.clearCache(true);
            mWebView.clearHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setTextZoom(100); // 让网页内容不受安卓文字大小设置

        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(true);
        mWebView.setWebChromeClient(new H5OpenAccountWebChromeClient());
        mWebView.setWebViewClient(new H5OpenAccountViewClient());
        mWebView.addJavascriptInterface(new H5CallBackAndroid(), "uyou");
        String username = AccountUtils.getUserName(getContext());
        String token = XORUtils.stringXOREncode(username, "UYOU");
        mWebView.loadUrl(UCConstants.APP_NFC_DOMAIN + "?token=" + token + "#/pages/uyou/appPay/appPayMethod");


    }

    MyHandler mHandler = new MyHandler(H5OpenAccountActivity.this);

    class MyHandler extends Handler {
        private final WeakReference<H5OpenAccountActivity> mActivityRef;

        MyHandler(H5OpenAccountActivity activity) {
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final H5OpenAccountActivity activity = mActivityRef.get();
            if (activity == null || activity.isFinishing()) {
                removeCallbacksAndMessages(null);
                return;
            }
            switch (msg.what) {
                case 111:
                    goHome();
                    break;
                case 222:
                    onBackPressed();
                    break;
            }
        }
    }

    private class H5OpenAccountWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressBar != null) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            if (!TextUtils.isEmpty(message)) {
                Uri uri = Uri.parse(message);
                String scheme = uri.getScheme();
                String authority = uri.getAuthority();
                // prompt("uyou-js://closeAll?aa=22&bb=22");
            }
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
            this.openFileChooser(uploadMsg);
        }

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
            this.openFileChooser(uploadMsg);
            showToast(AcceptType);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadMessageArray = filePathCallback;
            return true;
        }
    }


    // 设置微信 H5 支付调用 loadDataWithBaseURL 的标记位，避免循环调用，
    // 再次进入微信 H5 支付流程时记得重置此标记位状态
    boolean firstVisitWXH5PayUrl = true;

    private class H5OpenAccountViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            try {
                isAliPayIntercepted(view, url);
            } catch (Exception e) {
                // 防止手机没有安装处理某个 scheme 开头的 url 的 APP 导致 crash
                // 启动支付宝 App 失败，会自行跳转支付宝网页支付
                new AlertDialog.Builder(H5OpenAccountActivity.this)
                        .setMessage("未检测到支付宝客户端，请安装后重试。")
                        .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                            }
                        }).setNegativeButton("取消", null).show();
                return true;
            }


            if (url.startsWith("weixin://")) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } catch (Exception e) {
                    // 防止手机没有安装处理某个 scheme 开头的 url 的 APP 导致 crash
                    showToast("该手机没有安装微信");
                    return true;
                }
            } else if (url.contains("wx.tenpay.com")) {

                // 申请微信 H5 支付时填写的域名
                // 比如经常用来测试网络连通性的 http://www.baidu.com
                String referer = UCConstants.WX_H5_PAY_HOST;

                // 兼容 Android 4.4.3 和 4.4.4 两个系统版本设置 referer 无效的问题
                if (("4.4.3".equals(android.os.Build.VERSION.RELEASE))
                        || ("4.4.4".equals(android.os.Build.VERSION.RELEASE))) {
                    if (firstVisitWXH5PayUrl) {
                        view.loadDataWithBaseURL(referer, "<script>window.location.href=\"" + url + "\";</script>",
                                "text/html", "utf-8", null);
                        // 修改标记位状态，避免循环调用
                        // 再次进入微信H5支付流程时记得重置状态 firstVisitWXH5PayUrl = true
                        firstVisitWXH5PayUrl = false;
                    }
                    // 返回 false 由系统 WebView 自己处理该 url
                    return false;
                } else {
                    // HashMap 指定容量初始化，避免不必要的内存消耗
                    HashMap<String, String> map = new HashMap<>(1);
                    map.put("Referer", referer);
                    view.loadUrl(url, map);
                    return true;
                }
            }
            // 处理普通 http 请求跳转
            return !(url.startsWith("http") || url.startsWith("https"));
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            execJs(String.format("javascript:getAppParams('%s', '%s', '%s', '%s');", mPhone, mPayPriceValue, payAmount, openId));
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }
            if (mWebView != null) {
                mWebView.loadUrl("file:///android_asset/error_page.html");
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            try {
                handler.proceed();
            } catch (Exception e) {
                super.onReceivedSslError(view, handler, error);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 支付宝拦截模块
     *
     * @param url
     * @return
     */
    private boolean isAliPayIntercepted(final WebView webView, String url) {
        /*********支付宝支付模块**********/
        // 1.通过拦截方式
        // https://docs.open.alipay.com/203/107091/

        // 2.通过h5转native方式
        //https://docs.open.alipay.com/203/106493/
        final PayTask task = new PayTask(H5OpenAccountActivity.this);
        //1.如果h5PayUrl是有效的支付宝H5支付URL，则说明拦截转化成功，返回true，商户容器无需再加载该URL；
        //2.如果是无效的，则返回false，商户容器需要继续加载该URL。
        boolean isIntercepted = task.payInterceptorWithUrl(
                url,
                true,
                result -> {
                    // 支付结果返回
                    final String code = result.getResultCode() == null ? "" : result.getResultCode();
                    //支付结束后应当跳转的url地址
                    final String url1 = result.getReturnUrl();
                    H5OpenAccountActivity.this.runOnUiThread(() -> {
                        /*
                         * result.getResultCode()
                         * 返回码，标识支付状态，含义如下：
                         * 9000——订单支付成功
                         * 8000——正在处理中
                         * 4000——订单支付失败
                         * 5000——重复请求
                         * 6001——用户中途取消
                         * 6002——网络连接出错
                         */
                        switch (code) {
                            case "9000":
                                showToast("订单支付成功");
                                break;
                            case "8000":
                                showToast("正在处理中");
                                break;
                            case "4000":
                                showToast("订单支付失败");
                                break;
                            case "5000":
                                showToast("重复请求");
                                break;
                            case "6001":
                                showToast("支付已取消");
                                break;
                            case "6002":
                                showToast("网络连接出错");
                                break;
                            default:
                                break;
                        }
                        if (!TextUtils.isEmpty(url1)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadUrl(url1);
                                }
                            });
                        }
                    });
                });
        return isIntercepted;
    }

    /**
     * native回调js
     */
    private void execJs(String action) {
        if (mWebView != null && action != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.evaluateJavascript(action, null);
            } else {
                mWebView.loadUrl(action);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        XPopup.get(H5OpenAccountActivity.this)
                .asCustom(
                        new BaseConfirmDialog(H5OpenAccountActivity.this)
                                .title(getStringRes(R.string.bus_upload_open_suc_dialog_title))
                                .content("确定要退出支付")
                                .hideCancel()
                                .confirm(getStringRes(R.string.common_confirm))
                                .listener(new BaseConfirmDialog.OnClickListener() {
                                    @Override
                                    public void onCancelClick(BasePopupView popupView, View view) {
                                        popupView.dismiss();
                                    }

                                    @Override
                                    public void onConfirmClick(BasePopupView popupView, View view) {
                                        popupView.dismiss();
                                        finish();

                                    }
                                })
                                .confirmClick()
                )
                .autoDismiss(true)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            try {
                ViewParent parent = mWebView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(mWebView);
                }
                mWebView.stopLoading();
                mWebView.getSettings().setJavaScriptEnabled(false);
                mWebView.setWebChromeClient(null);
                mWebView.setWebViewClient(null);
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    public void goHome() {
        String path = AppConfigs.getStringSF(H5OpenAccountActivity.this, ACTION_PATH);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClassName(getPackageName(), path);
        startActivity(intent);
        finish();
    }

    public class H5CallBackAndroid {
        @JavascriptInterface
        public void goBackHome() {
            try {
                Message message = new Message();
                message.what = 111;
                mHandler.sendMessage(message);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void goHome() {
            try {
                Message message = new Message();
                message.what = 222;
                mHandler.sendMessage(message);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
