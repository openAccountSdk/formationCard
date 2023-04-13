package com.cio.project;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.uyou.copenaccount.utils.COpenAccountSdk;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        COpenAccountSdk.getInstance().start(this," com.cio.project.SuccessfulActivity");
        WebView mWebView = findViewById(R.id.my_web_view);
//         开启javascript 渲染
   /*     mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings settings = mWebView.getSettings();
        //支持javascript
        settings.setJavaScriptEnabled(true);
        //扩大比例的缩放
        //settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>欢迎您</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h2></h2>");
        sb.append("</body>");
        sb.append("</html>");
        System.out.println(sb.toString());
        mWebView.loadData(sb.toString(),"text/html","utf-8");*/
    }
}