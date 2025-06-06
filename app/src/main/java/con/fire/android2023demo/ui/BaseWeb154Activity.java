package con.fire.android2023demo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.zip.GZIPOutputStream;

import con.fire.android2023demo.R;
import con.fire.android2023demo.databinding.ActivityWebbaseBinding;

public class BaseWeb154Activity extends AppCompatActivity {

    protected WebView wvContent;
    ActivityWebbaseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebbaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wvContent = new WebView(getApplicationContext());
        binding.webViewContainer.addView(wvContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        WebSettings webSettings = wvContent.getSettings();


//        AppsFlyerLib.getInstance().logEvent(wvContent, null, null, new AppsFlyerRequestListener() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onError(int i, @NonNull String s) {
//
//            }
//        });

        //存储
        webSettings.setDomStorageEnabled(true);//是否支持 Local Storage
        webSettings.setJavaScriptEnabled(true);//支持JavaScript
        webSettings.setUseWideViewPort(true);//使Webview具有普通的视口(例如普通的桌面浏览器)
        webSettings.setLoadWithOverviewMode(true);//加载完全缩小的WebView
        webSettings.setSupportZoom(true);// 设置可以支持缩放
        webSettings.setBuiltInZoomControls(true);//设置启用缩放功能

        webSettings.setDatabaseEnabled(true);
        webSettings.setDisplayZoomControls(false);//不显示缩放按钮
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);//开启支持多窗口
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        //缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setTextZoom(100);
        //不显示滚动条
//        wvContent.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//        wvContent.setVerticalScrollBarEnabled(false);
//        wvContent.setOverScrollMode(View.OVER_SCROLL_NEVER);

//        wvContent.setWebChromeClient(new MyWebChromeClient());

        wvContent.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //加载开始

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //加载失败

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (url.startsWith("http") || url.startsWith("https")) {
                    return false;
                } else {
                    return true;
                }
//                // 处理特殊协议
//                if (url.startsWith("tel:")) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//                    startActivity(intent);
//                    return true; // 表示已处理，不交给 WebView 加载
//                } else if (url.startsWith("mailto:")) {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                } else if (url.startsWith("sms:")) {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                } else if (url.startsWith("intent:")) {
//                    try {
//                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                        startActivity(intent);
//                        return true;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//                }

                // 默认处理 HTTP/HTTPS
//                return false; // 交给 WebView 加载
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 兼容旧版本（API < 24）
//                if (url.startsWith("tel:")) {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                } else if (url.startsWith("mailto:")) {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                } else if (url.startsWith("sms:")) {
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                } else if (url.startsWith("intent:")) {
//                    try {
//                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                        startActivity(intent);
//                        return true;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//                }
//                return false;
                if (url.startsWith("http") || url.startsWith("https")) {
                    return false;
                } else {
                    return true;
                }
            }

        });
//        loadUrl("https://www.jianshu.com/p/2499255c7e79");

//        loadUrl("https://web.infinancingci.com/pay/#/?token=6058fbd6c6697e4e5cce805e4091d6dc");

        loadUrl("https://ecuador.prestamogratis.com/evolvingloans/privacy.html");
//        loadUrl("https://temp.empruntfacilecod.com/Chat/index.html");
    }


    @Override
    protected void onDestroy() {
        //销毁webview资源
//        if (wvContent != null) {
//            ViewParent parent = wvContent.getParent();
//            if (parent != null) {
//                ((ViewGroup) parent).removeView(wvContent);
//            }
//            //退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
//            wvContent.getSettings().setJavaScriptEnabled(false);
//            wvContent.stopLoading();
//            wvContent.clearHistory();
//            wvContent.clearCache(true);
//            wvContent.loadUrl("about:blank");
//            wvContent.removeAllViews();
//            wvContent.destroy();
//            wvContent = null;
//        }
//        GZIPOutputStream gzip = new GZIPOutputStream(baos);
//        String base64String = Base64.encodeToString(compressed, Base64.DEFAULT);


        super.onDestroy();
    }

    /**
     * WebView加载Url
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (wvContent != null) {
//            try {
//                Class<?> clazz = wvContent.getSettings().getClass();
//                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
//                method.invoke(wvContent.getSettings(), true);
//            } catch (IllegalArgumentException | NoSuchMethodException | IllegalAccessException |
//                     InvocationTargetException e) {
//                e.printStackTrace();
//            }

            wvContent.post(new Runnable() {

                @Override
                public void run() {
                    if (URLUtil.isValidUrl(url)) wvContent.loadUrl(url);
                }

            });
        }

    }


    private class MyWebChromeClient extends WebChromeClient {


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (TextUtils.isEmpty(title)) {
                //赋值标题赋值操作
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

}
