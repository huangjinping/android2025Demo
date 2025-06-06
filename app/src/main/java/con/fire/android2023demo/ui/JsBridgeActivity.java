package con.fire.android2023demo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.OnBridgeCallback;
import com.google.gson.Gson;

import con.fire.android2023demo.databinding.ActivityJsbridgeBinding;
import con.fire.android2023demo.utils.MainJavascriptInterface;

public class JsBridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    BridgeWebView webView;
    Button button;
    int RESULT_CODE = 0;
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessageArray;
    private ActivityJsbridgeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJsbridgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webView = binding.webView;
        button = binding.button;
        button.setOnClickListener(this);
        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessageArray = filePathCallback;
                pickFile();
                return true;
            }
        });

        webView.addJavascriptInterface(new MainJavascriptInterface(webView.getCallbacks(), webView), "WebViewJavascriptBridge");
        webView.setGson(new Gson());
        webView.loadUrl("file:///android_asset/demo.html");
        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";

        webView.callHandler("functionInJs", new Gson().toJson(user), new OnBridgeCallback() {
            @Override
            public void onCallBack(String data) {
                Log.d(TAG, "onCallBack: " + data);
            }
        });

        webView.sendToWeb("hello");

    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadMessageArray) {
                return;
            }
            if (null != mUploadMessage && null == mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

            if (null == mUploadMessage && null != mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                if (result != null) {
                    mUploadMessageArray.onReceiveValue(new Uri[]{result});
                }
                mUploadMessageArray = null;
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (button.equals(view)) {
            webView.callHandler("functionInJs", "data from Java", new OnBridgeCallback() {

                @Override
                public void onCallBack(String data) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, "reponse data from js " + data);
                }

            });
        }
    }

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

}
