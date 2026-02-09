package con.fire.android2023demo.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.luck.picture.lib.permissions.PermissionUtil;

import con.fire.android2023demo.R;

public class CameraWebActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    String[] permissionArr = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraweb);
        webView = findViewById(R.id.webview);
        setupWebView();

        // Check and request camera permission
//        if (!PermissionUtil.hasPermissions(this, permissionArr)) {
//            ActivityCompat.requestPermissions(this, permissionArr, REQUEST_CAMERA_PERMISSION);
//        } else {
//            loadWebsite();
//        }
        loadWebsite();

    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                Gson gson = new Gson();
                Log.d("okhttpss", gson.toJson(request.getResources()));

                // Grant camera permission for getUserMedia
//                if (ContextCompat.checkSelfPermission(CameraWebActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    runOnUiThread(() -> request.grant(request.getResources()));
//                } else {
//                    request.deny();
//                }

                if (PermissionUtil.hasPermissions(CameraWebActivity.this, permissionArr)) {
                    Log.d("okhttpss", "---------------------------");

                    runOnUiThread(() -> request.grant(request.getResources()));

                } else {
                    ActivityCompat.requestPermissions(CameraWebActivity.this, permissionArr, REQUEST_CAMERA_PERMISSION);
                    request.deny();
                }
            }
        });
    }

    private void loadWebsite() {
//        webView.loadUrl("https://doc.inxtech.cn/camera.html?v=" + Math.random());

//        webView.loadUrl("https://doc.inxtech.cn/kkocr.html?v=121");
//        webView.loadUrl("https://doc.inxtech.cn/faceapi.html");
//        webView.loadUrl("https://docs.google.com/viewer?url=https://www.ordacredit.com/documentFiles/files/%D0%9B%D0%B8%D1%86%D0%B5%D0%BD%D0%B7%D0%B8%D1%8F.pdf");

        webView.loadUrl("https://www.ordacredit.com/documentFiles/index.html");
//        webView.loadUrl("https://remote.biometric.kz/flow/1e8f000e-dcae-4bbf-bb6e-7d2a31236b9a?web_view=true");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtil.hasPermissions(this, permissionArr)) {
            loadWebsite();
        } else {
            Toast.makeText(this, "弹出dialog，打开设置页面", Toast.LENGTH_SHORT).show();
        }
//        if (requestCode == REQUEST_CAMERA_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                loadWebsite();
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
