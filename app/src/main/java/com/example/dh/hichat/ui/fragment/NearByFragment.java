package com.example.dh.hichat.ui.fragment;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dh.hichat.Html5WebView;
import com.example.dh.hichat.R;
import com.example.dh.hichat.base.BaseFragment;


/**
 * Created by DH on 2017/8/10.
 */

public class NearByFragment extends BaseFragment {

    private String mUrl;

    private FrameLayout mLayout;
    public Html5WebView mWebView;

    @Override
    public void setDefaultTitle(TextView tvTitle) {
        tvTitle.setText("彩神争霸");
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
        }, 2000);
        // 创建 WebView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new Html5WebView(getContext().getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        mWebView.setWebChromeClient(new Html5WebChromeClient());
//        mWebView.loadUrl("https://m.78500.cn/site.html");
        mWebView.loadUrl("http://m.zgzcw.com/news/");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                hideHtmlContent(webView);
                return super.shouldInterceptRequest(webView, s);
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
    }

    private void hideHtmlContent(final WebView webView) {
        //编写 javaScript方法
        final String javascript = "javascript:function hideOther() {" +
                "document.getElementsByTagName('header')[0].style.display='none';" +
//                "document.getElementById('top').style.display='none';" +
//                "document.getElementsByClassName('home')[0].style.display='none';" +
                "document.getElementsByClassName('footer')[0].style.display='none';" +
//                "document.getElementsByClassName('warpper')[0].style.display='none';" +
//                "document.frames[0].style.display='none';"+
//                "document.frames[1].style.display='none';"+
//                "document.frames[2].style.display='none';"+
////                "document.getElementsByClassName('ui-img')[0].src='http://www.baidu.com/img/bd_logo1.png';" +
//                "document.getElementsByClassName('single-city')[0].style.display='none';" +
//                "var node = document.createTextNode('  \t\t  ');" +
//                "node.textColor;" +
//                "document.getElementsByClassName('hd-logo')[0].appendChild(node);" +
//                "var rootlist = document.getElementById('rootlist');"+
//                "var catelist = document.getElementsByClassName('cate-content-ads')[0];"+
//                "rootlist.style.display='none';"+
//                "rootlist.remove('cate-root-tab');" +
//                "rootlist.style.width = 0 + 'px'"+

//                "catelist[0].style.display='none';" +
//                "var divs = catelist.getElementsByTagName('a')"+
//                "document.getElementsByClassName('cate-content-wrap')[0].removeChild(divs[0]);" +
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
    static class Html5WebChromeClient extends Html5WebView.BaseWebChromeClient {

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
