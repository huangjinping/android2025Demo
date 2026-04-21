package con.fire.android2023demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.luck.picture.lib.permissions.PermissionUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MediaPipeCacheWebViewActivity extends AppCompatActivity {

    private static final int REQ_CAMERA = 1001;
    String[] permissionArr = {Manifest.permission.CAMERA};
    private WebView webView;

    private static String headerValue(Map<String, String> headers, String key) {
        if (headers == null) return null;
        for (Map.Entry<String, String> e : headers.entrySet()) {
            if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) {
                return e.getValue();
            }
        }
        return null;
    }

    private static String mimeFor(String ext) {
        if (".wasm".equals(ext)) return "application/wasm";
        if (".task".equals(ext)) return "application/octet-stream";
        return "application/octet-stream";
    }

    private static File downloadToFile(String urlStr, File target) throws Exception {
        File tmp = new File(target.getParentFile(), target.getName() + ".tmp");
        if (tmp.exists()) {
            //noinspection ResultOfMethodCallIgnored
            tmp.delete();
        }

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "*/*");

            int code = conn.getResponseCode();
            if (code < 200 || code >= 300) return null;

            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(tmp);
            try {
                byte[] buf = new byte[64 * 1024];
                int n;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                out.flush();
            } finally {
                try {
                    in.close();
                } catch (Throwable ignored) {
                }
                try {
                    out.close();
                } catch (Throwable ignored) {
                }
            }

            if (tmp.length() <= 0) return null;

            if (target.exists()) {
                //noinspection ResultOfMethodCallIgnored
                target.delete();
            }

            boolean renamed = tmp.renameTo(target);
            if (!renamed) {
                copyFile(tmp, target);
                //noinspection ResultOfMethodCallIgnored
                tmp.delete();
            }
            return target;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private static void copyFile(File src, File dst) throws Exception {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        try {
            byte[] buf = new byte[64 * 1024];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            out.flush();
        } finally {
            try {
                in.close();
            } catch (Throwable ignored) {
            }
            try {
                out.close();
            } catch (Throwable ignored) {
            }
        }
    }

    private static String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(text.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) sb.append(String.format(Locale.US, "%02x", b));
            return sb.toString();
        } catch (Throwable e) {
            return String.valueOf(text.hashCode());
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        requestCameraPermissionIfNeeded();

        WebView.setWebContentsDebuggingEnabled(true);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setSupportZoom(false);

        s.setAllowFileAccess(false);
        s.setAllowContentAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                runOnUiThread(() -> request.grant(request.getResources()));
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String method = request.getMethod();
                if (method == null || !"GET".equalsIgnoreCase(method)) return null;

                Uri uri = request.getUrl();
                String url = uri.toString();
                String lower = url.toLowerCase(Locale.US);

                boolean isTarget = lower.endsWith(".wasm") || lower.endsWith(".task");
                if (!isTarget) return null;

                // 避免 Range 分段导致缓存文件不完整
                Map<String, String> headers = request.getRequestHeaders();
                String range = headerValue(headers, "Range");
                if (range != null && !range.isEmpty()) return null;

                File cacheDir = new File(getExternalCacheDir(), "mp_asset_cache");
                //noinspection ResultOfMethodCallIgnored
                cacheDir.mkdirs();

                String ext;
                if (lower.endsWith(".wasm")) ext = ".wasm";
                else if (lower.endsWith(".task")) ext = ".task";
                else ext = "";

                File cacheFile = new File(cacheDir, sha256(url) + ext);

                if (cacheFile.exists() && cacheFile.length() > 0) {
                    return fileResponse(cacheFile, mimeFor(ext));
                }

                try {
                    File downloaded = downloadToFile(url, cacheFile);
                    if (downloaded != null) {
                        return fileResponse(downloaded, mimeFor(ext));
                    }
                } catch (Throwable ignored) {
                }

                if (cacheFile.exists() && cacheFile.length() > 0) {
                    return fileResponse(cacheFile, mimeFor(ext));
                }

                return null;
            }
        });

        loadWebsite();

//        String pageUrl = getIntent().getStringExtra("url");
//        if (pageUrl == null || pageUrl.trim().isEmpty()) {
////            pageUrl = "https://your-domain.com/faceDemo.html?language=cn";
//            webView.loadUrl("https://bjst.ultracreditosmx.com/facemodel/faceDemo.html?token=a3e6ab4b-7da3-491c-b87c-3f1cb998dc4f&userId=853&language=en&appssid=06");
//
//        }
//        webView.loadUrl(pageUrl);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    private void requestCameraPermissionIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
        }
    }

    private WebResourceResponse fileResponse(File file, String mime) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            WebResourceResponse resp = new WebResourceResponse(mime, "utf-8", is);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resp.setStatusCodeAndReasonPhrase(200, "OK");
                Map<String, String> responseHeaders = new HashMap<>();
                responseHeaders.put("Cache-Control", "public, max-age=31536000, immutable");
                resp.setResponseHeaders(responseHeaders);
            }
            return resp;
        } catch (Throwable ignored) {
            return null;
        }
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

    private void loadWebsite() {
//        webView.loadUrl("https://doc.inxtech.cn/liveness.html");
        webView.loadUrl("https://bjst.ultracreditosmx.com/facemodel/faceDemo.html?token=a3e6ab4b-7da3-491c-b87c-3f1cb998dc4f&userId=853&language=en&appssid=06");
    }
}