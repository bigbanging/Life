package com.gwl.life.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gwl.life.R;

public class ShowWechatArticleActivity extends BaseActivity {

    private ProgressBar pb_loading_article;
    private WebView wv_article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wechat_article);
        initView();
    }

    private void initView() {
        pb_loading_article = (ProgressBar) findViewById(R.id.pb_loading_article);
        wv_article = (WebView) findViewById(R.id.wv_article);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");

        //设置标题
        getSupportActionBar().setTitle(title);
        //进行加载网页的逻辑
        //支持JS
        wv_article.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        wv_article.getSettings().setSupportZoom(true);
        wv_article.getSettings().setBuiltInZoomControls(true);
        //接口回掉
        wv_article.setWebChromeClient(new WebViewClient());
        //加载网页
        wv_article.loadUrl(url);
        //本地显示
        wv_article.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public class WebViewClient extends WebChromeClient{
        //进度条变化监听

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                pb_loading_article.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
