package con.fire.android2023demo.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import con.fire.android2023demo.R;

public class CameraWebActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraweb);
        webView = findViewById(R.id.webview);
        setupWebView();

        // Check and request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            loadWebsite();
        }
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
                if (ContextCompat.checkSelfPermission(CameraWebActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    runOnUiThread(() -> request.grant(request.getResources()));
                } else {
                    request.deny();
                }
            }
        });
    }

    private void loadWebsite() {
        webView.loadUrl("https://doc.inxtech.cn/camera.html");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadWebsite();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
