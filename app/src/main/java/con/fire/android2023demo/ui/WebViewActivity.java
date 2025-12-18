package con.fire.android2023demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fingerprintjs.android.fingerprint.Fingerprinter;
import com.fingerprintjs.android.fingerprint.FingerprinterFactory;

import java.util.UUID;

import con.fire.android2023demo.R;

public class WebViewActivity extends AppCompatActivity {

    final String tag = "WebV1iewActi1vity";

    WebView webview;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = findViewById(R.id.webview);
//        WebView.setWebContentsDebuggingEnabled(true);
        webview.setWebViewClient(new MyWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());
        WebSettings settings = webview.getSettings();
        settings.setDomStorageEnabled(true);//是否支持 Local Storage
        settings.setJavaScriptEnabled(true);//支持JavaScript
        settings.setUseWideViewPort(true);//使Webview具有普通的视口(例如普通的桌面浏览器)
        settings.setLoadWithOverviewMode(true);//加载完全缩小的WebView
        settings.setSupportZoom(true);// 设置可以支持缩放
        settings.setBuiltInZoomControls(true);// 设置启用缩放功能
        settings.setDisplayZoomControls(true);//显示缩放控制按钮
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);


//        -----------------------------___>>>>>>>>>>>>>>>>>1>>>>>>>
        settings.setDatabaseEnabled(true);
        settings.setTextZoom(100);
        //不显示滚动条
        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setVerticalScrollBarEnabled(false);
        webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //        -----------------------------___>>>>>>>>>>>>>>>>>1>>>>>>>


        String url = "https://qr.pay.wave.com/c/cos-1ctbn5v7g21zm?a=1025&c=XOF&m=S-FINTECH%20TECHNOLOGIE";
//        webview.loadUrl("http://www.baidu.com");
//        url = "https://www.jianshu.com/p/4860097148c0";
//        url="https://www.flow.cl/app/web/pay.php?token=A277E78C1BD89310C253F51BAE398A5B725DF76T";

        url = "https://docs.google.com/viewer?url=https%3A%2F%2Fapi-uat.kushkipagos.com%2Fcash%2Fv1%2Fcharges%2F2380860672042019%2Freceipt";
//        url = "https://app.payku.cl/gateway/cobro?id=trx83ab21586b9643d01&valid=1cc20559db";

//        url = "http://10.1.2.40:8080/fingerprint1.html?v=" + System.currentTimeMillis();
        url = "http://10.1.2.40:8080/fingerprint1.html?v=" + System.currentTimeMillis();
        url = "https://sandbox-short.payv.co/nM5RJ8x1zFx6";
        url = "https://secure-short.payv.co/RxbJMwSJh1";
        url = "http://111.203.220.52:93/t4est.html";
//        url = "https://sandbox.combopay.co/payment-link/peso-efectivo-sas/401872_1690962067396";
//        url = "https://sandbox.combopay.co/payment-link/peso-efectivo-sas/401966_1691563606699";
        url = "https://sandbox.combopay.co/payment-link/peso-efectivo-sas/401966_1691563983838";
//        url = "https://sandbox.combopay.co/payment-link/peso-efectivo-sas/401947_1691492760839";
//        url = "https://www.baidu.com";

        url = "https://checkout.toppaylatam.com/form/#/?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb2RlIjoyNjMzMDcyfQ.2Dxxx29MsveldyS_ryALt5mQmWIqc-C6Q7EZdUyfzmM";

        url = "https://web.itshcash.com/pay/#/?token=249b8e30e44c2c84c5e51cf6104ab2fb";
        url = " https://web.creditlandsa.com/clause?userId=9752&detailId=18&chooseAmountIds=,7167793935007899649";

        url = "https://pay-met.ec:6080/#/metodo-pago/VE9LRU4gR0VORVJBIFJFQ0FVRE8xNjEzMTE=";
//        url = "https://doc.inxtech.cn/webview.html?v="+ UUID.randomUUID().toString();

        url = "http://10.1.2.8:8080/test/webview.html?v=" + UUID.randomUUID().toString();

        url = "https://malaysia.prestamoup.com/customer/index.html?appSsid=521";

        webview.loadUrl(url);
//        webview.loadUrl("https://www.inx-fintech.com/#/home/index");
//jianshu://notes/4860097148c0
        onstFinger();

//        installapklink(this, url);


    }

    public void installapklink(Activity context, String apkLink) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(apkLink);
        intent.setData(content_url);
        startActivity(intent);
    }


    private void onstFinger() {

        Log.d("onstFinger", "onstFinger========1");

// Initialization
        Fingerprinter fingerprinter = FingerprinterFactory.create(this);

// Usage
        fingerprinter.getFingerprint(Fingerprinter.Version.V_5, fingerprint -> {
            Log.d("onstFinger", "onstFinger=====2===" + fingerprint);
            // use fingerprint
            return null;
        });

        fingerprinter.getDeviceId(Fingerprinter.Version.V_5, deviceIdResult -> {
            String deviceId = deviceIdResult.getDeviceId();
            // use deviceId
            Log.d("onstFinger", "onstFinger=====3===" + deviceId);
            return null;
        });

    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
            return;
        }
        super.onBackPressed();


    }

    private class MyWebViewClient extends WebViewClient {

//        在api 24（7.0）以下的版本的时候，只会回调shouldOverrideUrlLoading(WebView view, String url)方法
//        在api 24及以上版本的时候，只会回调shouldOverrideUrlLoading（WebView view, WebResourceRequest request)方法
//        注：方法中return true 进行url拦截自己处理，return false由webview系统自己处理。


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            try {
                Log.d(tag, "==UrlLoading====" + url);

                if (!url.startsWith("http") && !url.startsWith("http")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("onReceivedError", "" + error.getErrorCode() + "======" + error.getDescription());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d(tag, error.getErrorCode() + "----onReceivedError" + error.getDescription());
            }
        }


        //        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            //            //这里需要为true，否则网页中出现非http，https协议的方式会打不开，例如，https://www.jianshu.com/p/4860097148c0
//            return super.shouldOverrideUrlLoading(view, request);
//        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(tag, "onPageStarted " + url);
            try {
                if (url.contains("static/Mpsuccess.html")) {
                    //跳转页面
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //        onReceivedSslError  此方法最好不要使用。
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            Log.d("onReceivedSslError", "" + error.getPrimaryError());
            view.getUrl();
            handler.proceed();
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