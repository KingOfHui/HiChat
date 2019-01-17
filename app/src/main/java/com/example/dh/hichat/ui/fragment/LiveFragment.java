package com.example.dh.hichat.ui.fragment;

import android.net.http.SslError;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseFragment;
import com.example.dh.hichat.weight.Html5WebView;

/**
 * Created by DH on 2017/8/10.
 */

public class LiveFragment extends BaseFragment {
    private FrameLayout mLayout;
    public Html5WebView mWebView;

    @Override
    public void setDefaultTitle(TextView tvTitle) {
        tvTitle.setText("彩神开奖直播");
    }

    @Override
    public void showBodyContent() {
        super.showBodyContent();

        View view = View.inflate(getContext(), R.layout.activity_web, null);
        mLayout = (FrameLayout) view.findViewById(R.id.web_layout);
        content.addView(view);

        content.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                content.setVisibility(View.VISIBLE);
            }
        }, 1500);
        // 创建 WebView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new Html5WebView(getContext().getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        mWebView.setWebChromeClient(new NearByFragment.Html5WebChromeClient());

//        mWebView.loadUrl("https://m.78500.cn/site.html");

//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url("https://m.78500.cn/kaijiang/")
//                .get()//默认就是GET请求，可以不写
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("dhdhdh", "onFailure: "+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                Log.d("dhdhdh", "onResponse: " + string);
//                mWebView.loadUrl(string);
//            }
//        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                hideHtmlContent(webView);
                return super.shouldInterceptRequest(webView, s);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);
                hideHtmlContent(webView);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideHtmlContent(view);
            }
        });
        mWebView.loadUrl("https://m.78500.cn/kaijiang/");
    }

    private void hideHtmlContent(final WebView webView) {
        //编写 javaScript方法
        final String javascript = "javascript:function hideOther() {" +
                "document.getElementsByTagName('header')[0].style.display='none';" +
                "var cars = document.getElementsByTagName('iframe');" +
                "for (var i=0;i<cars.length;i++)" +
                "{" +
                "cars[i].style.display='none'" +
                "}"+
                "document.getElementsByClassName('footer')[0].style.display='none';" +
                "}";
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建方法
                webView.loadUrl(javascript);

                //加载方法
                webView.loadUrl("javascript:hideOther();");

            }
        });
    }

    // 继承 WebView 里面实现的基类
    class Html5WebChromeClient extends Html5WebView.BaseWebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 顶部显示网页加载进度
//            mSeekBar.setProgress(newProgress);
        }

    }

    @Override
    public void onDestroy() {
        // 销毁 WebView
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyBackPressed() {
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return false;//用来自定义事件未消费
    }
}
