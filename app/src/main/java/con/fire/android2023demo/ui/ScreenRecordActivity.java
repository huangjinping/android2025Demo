package con.fire.android2023demo.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.concurrent.Executor;

import con.fire.android2023demo.databinding.ActivityScreenRecordBinding;
import con.fire.android2023demo.service.ScreenRecordService;


/// /https://blog.csdn.net/weixin_42602900/article/details/128340037
//https://github.com/tangfuOK/AndroidTangFu/tree/master/%E4%B8%A4%E4%B8%AA%E7%B1%BB%E5%AE%9E%E7%8E%B0%E5%BD%95%E5%B1%8FDemo/ScreenRecorderDemo
public class ScreenRecordActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 1;
    ActivityScreenRecordBinding binding;
    String path = "";
    private MediaProjectionManager mMediaProjectionManager;
    private ContentObserver screenshotObserver;
    private Activity.ScreenCaptureCallback screenCaptureCallback;

    public static void checkPermission(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 123);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScreenRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermission(this); //检查权限

        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createScreenCapture();
            }
        });
        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service = new Intent(ScreenRecordActivity.this, ScreenRecordService.class);
                stopService(service);
            }
        });


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {  // Android 14 (API 34)
//            screenCaptureCallback = new Activity.ScreenCaptureCallback() {
//                @Override
//                public void onScreenCaptured() {
//                    // 截屏触发后显示警告弹框
//                    showScreenshotWarningDialog();
//                }
//            };
//        } else {
//
//        }
        registerScreenshotObserver();


    }

    // 在 onCreate 中注册
    private void registerScreenshotObserver() {
        screenshotObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                if (TextUtils.equals(path, uri.getPath())) {
                    return;
                }
                path = uri.getPath();
                Log.d("okhttps", uri.getPath() + "=========1===========" + System.currentTimeMillis());
                showScreenshotWarningDialog();
            }
        };

        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, screenshotObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterScreenshotObserver();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && screenCaptureCallback != null) {
            // 注册截屏监听（使用主线程 Executor）
            Executor mainExecutor = getMainExecutor();
            registerScreenCaptureCallback(mainExecutor, screenCaptureCallback);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && screenCaptureCallback != null) {
            // 取消注册，防止内存泄漏
            unregisterScreenCaptureCallback(screenCaptureCallback);
        }
    }

    // 在 onDestroy 中取消
    private void unregisterScreenshotObserver() {
        if (screenshotObserver != null) {
            getContentResolver().unregisterContentObserver(screenshotObserver);
            screenshotObserver = null;
        }
    }

    /**
     * 显示截屏警告弹框
     */
    private void showScreenshotWarningDialog() {
        new AlertDialog.Builder(this).setTitle("截屏警告").setMessage("您已触发截屏操作。\n\n请合理使用截屏功能，避免用于非法用途或泄露他人隐私。\n感谢您的配合！").setPositiveButton("我知道了", (dialog, which) -> dialog.dismiss()).setCancelable(false)        // 用户必须点击才能关闭（推荐）
                .show();
    }

    private void createScreenCapture() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                Toast.makeText(this, "允许录屏", Toast.LENGTH_SHORT).show();

                Intent service = new Intent(this, ScreenRecordService.class);
                service.putExtra("resultCode", resultCode);
                service.putExtra("data", data);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(service);
                } else {
                    startService(service);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "拒绝录屏", Toast.LENGTH_SHORT).show();
        }
    }

}
