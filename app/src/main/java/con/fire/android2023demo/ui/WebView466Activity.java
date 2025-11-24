package con.fire.android2023demo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityWebview466Binding;

public class WebView466Activity extends AppCompatActivity {

    private ActivityWebview466Binding binding;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebview466Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webview.setWebChromeClient(new MyWebChromeClient());
        WebSettings settings = binding.webview.getSettings();
        settings.setDomStorageEnabled(true);//是否支持 Local Storage
        settings.setJavaScriptEnabled(true);//支持JavaScript
        settings.setUseWideViewPort(true);//使Webview具有普通的视口(例如普通的桌面浏览器)
        settings.setLoadWithOverviewMode(true);//加载完全缩小的WebView
        settings.setSupportZoom(true);// 设置可以支持缩放
        settings.setBuiltInZoomControls(true);// 设置启用缩放功能
        settings.setDisplayZoomControls(true);//显示缩放控制按钮
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);

//        settings.setAllowFileAccess(true);
//        settings.setAllowFileAccessFromFileURLs(true);
//        settings.setAllowUniversalAccessFromFileURLs(true);

        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        settings.setDatabaseEnabled(true);
        settings.setTextZoom(100);
        binding.webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webview.setVerticalScrollBarEnabled(false);
        binding.webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        binding.webview.loadUrl("http://172.16.3.28:8081/?v=" + System.currentTimeMillis());
//        binding.webview.loadUrl("");
//        binding.webview.loadUrl("file:///android_asset/h5/index.html");

        binding.webview.loadUrl("https://doc.inxtech.cn/camera.html");


        binding.webview.addJavascriptInterface(this, "nativeWkObc");

    }


    @JavascriptInterface
    public String getAppVersion() {
        // 获取App版本号
        String version = "1002";

        // 将App版本号返回给H5
        return version;
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
