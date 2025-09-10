package con.fire.android2023demo.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import con.fire.android2023demo.FileUtils;
import con.fire.android2023demo.databinding.ActivityUploadwebBinding;
import con.fire.android2023demo.utils.AndroidImageResizer;
import con.fire.android2023demo.utils.ExifInfoCopier;

/**
 * https://www.jianshu.com/p/444932cf5d41
 * https://www.cnblogs.com/cw828/p/10110973.html
 */
public class UploadWebPDFActivity extends AppCompatActivity {

    public static final int SELECT_PHOTO = 2;//启动相册标识
    final String TAG = "Upl22oadWe";
    WebView webView;
    ValueCallback mUploadCallBack;
    ValueCallback<Uri[]> mUploadCallBackAboveL;
    FileUtils fileUtils;
    String isCanBack = "1";
    private ActivityUploadwebBinding binding;
    private AndroidImageResizer imageResizer;
    private ActivityResultLauncher<Intent> filePickerLauncher;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadwebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webView = binding.webView;
//        webView = new WebView(this);
//
//        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
//        decorView.addView(webView, 1, 1);
        requestPermissions(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1200);


        webView.setWebViewClient(new MyWebViewClient());
        this.fileUtils = new FileUtils();
        this.imageResizer = new AndroidImageResizer(this, new ExifInfoCopier());

        webView.setWebChromeClient(new MyWebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//是否支持 Local Storage
        settings.setJavaScriptEnabled(true);//支持JavaScript
        settings.setUseWideViewPort(true);//使Webview具有普通的视口(例如普通的桌面浏览器)
        settings.setLoadWithOverviewMode(true);//加载完全缩小的WebView
        settings.setSupportZoom(true);// 设置可以支持缩放
        settings.setBuiltInZoomControls(true);// 设置启用缩放功能
        settings.setDisplayZoomControls(true);//显示缩放控制按钮
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        -----------------------------___>>>>>>>>>>>>>>>>>1>>>>>>>
        settings.setDatabaseEnabled(true);
        settings.setTextZoom(100);
        //不显示滚动条
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setVerticalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

//        webView.loadUrl("http://10.1.2.91:8092/inxupload.html?v=" + System.currentTimeMillis());

//        webView.loadUrl("http://111.203.220.52:8091/inxupload.html?appSsid=252&frontSource=2&userId=2183&v=" + System.currentTimeMillis());

//        webView.loadUrl("http://172.17.1.103:8092/inxupload.html?appSsid=252&frontSource=2&userId=2183&v=" + System.currentTimeMillis());

//        webView.loadUrl("https://peru.prestamopluspe.com/customers/index.html?frontSource=1&appSsid=200&mobile=1820000&v=" + System.currentTimeMillis());
//        webView.loadUrl("https://chile.ultracreditosmx.com/customer/index.html?frontSource=10&appSsid=9050&userId=5201&v=" + System.currentTimeMillis());

//        webView.postUrl("https://www.sricredito.com/sricreditos/privacy.html", null);
//        webView.loadUrl("file:///android_asset/your_html_file.html"); // 加载本地 HTML 文件

//        webView.loadUrl("http://10.1.2.8:8092/pdf.html?v=" + System.currentTimeMillis());

//        webView.loadUrl("https://www.soleadomx.com/customer/submitResult.html?aiType=0&workOrderId=2447&missNum=4&appSsid=313&type=2&mobile=1832111111");
//        webView.loadUrl("https://www.soleadomx.com/customer/index.html?aiType=0&workOrderId=2447&missNum=4&appSsid=313&type=2&mobile=1832111111");
        http:
//111.203.220.52:8091/inxuploadBack.html
//        webView.loadUrl("http://111.203.220.52:8091/inxupload.html?v=" + System.currentTimeMillis());
//        webView.loadUrl("https://www.baidu.com");
        webView.addJavascriptInterface(this, "nativeWkObc");
//        webView.loadUrl("file:///android_asset/upload.html");
//        webView.loadUrl("file:///android_asset/upload.html");
//        webView.loadUrl("file:///android_asset/h5/index.html");

        binding.btnSubmit.setOnClickListener(view -> {
//            String result = System.currentTimeMillis() + "";
//            webView.loadUrl("javascript:onVSLogEvent('" + result + "')");
            showFileChooser();
        });
//        requestAllFilesAccess();

        // 初始化文件选择器
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
//                            uploadPdfFile(uri);
                }
            }
        });
    }

    private void requestAllFilesAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 100);
            } catch (Exception e) {
                // 部分设备可能不支持，降级到通用设置页面
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 100);
            }
        } else {
            Toast.makeText(this, "此功能需要 Android 11 或更高版本", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileChooser() {

        Toast.makeText(this, "打开相册" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        boolean usePhotoPicker = false;

//        Intent pickImageIntent;
//        pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
////        pickImageIntent.setType("image/*");
//        pickImageIntent.setType("application/pdf");
////        pickImageIntent.setType("*/*");
////        pickImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(pickImageIntent, SELECT_PHOTO);

//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*");
//        String[] mimetypes = {"image/*", "application/pdf"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
//        startActivityForResult(intent, SELECT_PHOTO);

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
//
//        startActivityForResult(intent, SELECT_PHOTO);


//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "application/pdf"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择PDF文件"), SELECT_PHOTO);
//            filePickerLauncher.launch(Intent.createChooser(intent, "选择PDF文件"));

    }


    // 将Uri转换为File
    private File uriToFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) return null;

            String fileName = "temp_file";
            String mimeType = getContentResolver().getType(uri);
            if (mimeType != null) {
                if (mimeType.startsWith("image/")) {
                    fileName = "temp_image" + (mimeType.endsWith("png") ? ".png" : ".jpg");
                } else if (mimeType.equals("application/pdf")) {
                    fileName = "temp_pdf.pdf";
                }
            }

            File file = new File(getCacheDir(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void backToHtml(String path) {

        System.out.println("=======================");
        System.out.println(path);
        Log.d("backToHtml", "" + path);
        Toast.makeText(this, "save:" + path, Toast.LENGTH_SHORT).show();
        webView.evaluateJavascript("javascript:dataFromNative('" + path + "')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {

            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case SELECT_PHOTO:
//                Log.d(TAG, "===================1");
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d(TAG, "===================4");
//                        setData(requestCode, resultCode, data);
//                        clearUploadMessage();
//                    }
//                }, 4000);
//                break;
//        }
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECT_PHOTO && mUploadCallBackAboveL != null) {
//            Uri[] results = null;
//            if (resultCode == RESULT_OK && data != null) {
//                results = new Uri[]{data.getData()};
//
//                Uri uri = data.getData();
//                if (uri != null) {
//                    uploadFile(uri);
//                }
//            }
//
//
//            mUploadCallBackAboveL.onReceiveValue(results);
//            mUploadCallBackAboveL = null;
//        }
        Uri[] results = null;
        if (resultCode == RESULT_OK && data != null) {
            results = new Uri[]{data.getData()};

            Uri uri = data.getData();
            if (uri != null) {
                uploadFile(uri);
            }
        }
    }


    private void uploadFile(Uri uri) {
        File file = uriToFile(uri);
        if (file == null) {
            Toast.makeText(this, "无法读取文件", Toast.LENGTH_SHORT).show();

        }
        Toast.makeText(this, "读取1文件成功！", Toast.LENGTH_SHORT).show();

        Log.d("fil22e", "=====" + file.getAbsolutePath());
    }

    private void setData(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            String compressPath = fileUtils.getPathFromUri(this, data.getData());
            compressPath = imageResizer.resizeImageIfNeeded(compressPath, 1080d, 1920d, 90);

            if (!fileUtils.isPicture(new File(compressPath))) {
                return;
            }
            Uri newUri = null;
            newUri = Uri.fromFile(new File(compressPath));

            backToHtml(compressPath);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mUploadCallBackAboveL != null) {
                    if (newUri != null) {
                        Toast.makeText(this, "开始返回html", Toast.LENGTH_SHORT).show();
                        mUploadCallBackAboveL.onReceiveValue(new Uri[]{newUri});
                        mUploadCallBackAboveL = null;
                        return;
                    }
                }
            } else if (mUploadCallBack != null) {
                if (newUri != null) {
                    mUploadCallBack.onReceiveValue(newUri);
                    mUploadCallBack = null;
                    return;
                }
            }
        } else {
            clearUploadMessage();
        }
    }

    private void clearUploadMessage() {
//        mUploadCallBackAboveL.onReceiveValue(null);
//        mUploadCallBackAboveL = null;
//        mUploadCallBack.onReceiveValue(null);
//        mUploadCallBack = null;
        if (mUploadCallBackAboveL != null) {
            mUploadCallBackAboveL.onReceiveValue(null);
            mUploadCallBackAboveL = null;
        }
        if (mUploadCallBack != null) {
            mUploadCallBack.onReceiveValue(null);
            mUploadCallBack = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "-------onDestroy--------");
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        webView.evaluateJavascript("javascript:onBackPressed()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                if ("0".equals(s)) {

                    Toast.makeText(UploadWebPDFActivity.this, "不返回", Toast.LENGTH_SHORT).show();

                } else {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
//                        UploadWebActivity.super.onBackPressed();
                        finish();
                    }
                }
            }
        });
    }

    @JavascriptInterface
    public void setNativeBacKFlag(String flag) {

        if ("1".equals(flag)) {
            showFileChooser();
        }
        Toast.makeText(this, "showToast===" + flag, Toast.LENGTH_SHORT).show();
    }

    private void openFileChooser1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择图片或 PDF"), SELECT_PHOTO);
    }

    private class MyWebViewClient extends WebViewClient {

//        在api 24（7.0）以下的版本的时候，只会回调shouldOverrideUrlLoading(WebView view, String url)方法
//        在api 24及以上版本的时候，只会回调shouldOverrideUrlLoading（WebView view, WebResourceRequest request)方法
//        注：方法中return true 进行url拦截自己处理，return false由webview系统自己处理。


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("shouloading", "==========" + url);

            try {
                Log.d(TAG, "====");
                if (!url.startsWith("http") && !url.startsWith("https")) {

                    if (url.startsWith("whatsapp://")) {
                        /**
                         * 判断是不是安装了whatApp  包名为com.whatsapp
                         * 若没有安装系统浏览器请打开https://api.whatsapp.com 即可
                         */
                    }
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

            try {
                if (url.contains("/MPsuccess.html")) {
                    //跳转页面
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //        onReceivedSslError  此方法最好不要使用。
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
//
//            handler.proceed();
//        }
    }

    private class MyWebChromeClient extends WebChromeClient {


        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//            MainActivity.this.filePathCallback = filePathCallback;
            // 检查权限
            mUploadCallBackAboveL = filePathCallback;
            openFileChooser1();

            return true;
        }
//
//        // For Android < 3.0
//        public void openFileChooser(ValueCallback<Uri> valueCallback) {
//            mUploadCallBack = valueCallback;
//            showFileChooser();
//        }
//
//        // For Android  >= 3.0
//        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
//            mUploadCallBack = valueCallback;
//            showFileChooser();
//        }
//
//        //For Android  >= 4.1
//        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
//            mUploadCallBack = valueCallback;
//            showFileChooser();
//        }

        // For Android >= 5.0
//        @Override
//        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//            mUploadCallBackAboveL = filePathCallback;
//            fileChooserParams.getMode();
//            showFileChooser();
//            return true;
//        }

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
            if (newProgress >= 100) {
                binding.progressBar.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.progressBar.setProgress(newProgress);
            }
        }
    }

}
