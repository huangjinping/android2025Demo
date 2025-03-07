package con.fire.android2023demo.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getList();
        getReferrerApi();
        getReferrerReceiver();
    }


    private void getReferrerApi() {
        final SharedPreferences prefs = getSharedPreferences("InstallReferrerHelper", Context.MODE_PRIVATE);
        String old = prefs.getString("InstallReferrerHelper", "");
        binding.txtResultApi.setText(old);
    }

    private void getReferrerReceiver() {
        final SharedPreferences prefs = getSharedPreferences("install_referrer", Context.MODE_PRIVATE);
        String old = prefs.getString("install_referrer", "");
        binding.txtResultReceiver.setText(old);
    }

    private void getList() {
        String packageName = "com.chrome.beta"; // 要查询的应用程序的包名
        packageName = "bin.mt.plus";
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            String apkFilePath = applicationInfo.sourceDir;
            String appName = (String) packageManager.getApplicationLabel(applicationInfo);
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            // 打印相关信息
            Log.d("Package Info", "APK 文件路径: " + apkFilePath);
            Log.d("Package Info", "应用程序名称: " + appName);
            Log.d("Package Info", "版本号: " + versionCode);
            Log.d("Package Info", "版本名称: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
