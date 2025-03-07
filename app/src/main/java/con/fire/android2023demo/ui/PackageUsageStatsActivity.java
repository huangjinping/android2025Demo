package con.fire.android2023demo.ui;

import android.Manifest;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import con.fire.android2023demo.databinding.ActivityPackageusagestatsBinding;
import con.fire.android2023demo.utils.LogUtils;
import con.fire.android2023demo.utils.usageStats.PackageInfo;
import con.fire.android2023demo.utils.usageStats.UseTimeDataManager;


//https://blog.csdn.net/github_37271067/article/details/85292543
//https://mp.weixin.qq.com/s/SfOAr0Bpnwrh_YUDb6SA0g
//
public class PackageUsageStatsActivity extends AppCompatActivity {

    ActivityPackageusagestatsBinding binding;
    String TAG = "okhtt22p";


    ActivityResultLauncher activityResultSingle = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            LogUtils.logS(TAG, result);

        }
    });
    String permissionsSingle = Manifest.permission.PACKAGE_USAGE_STATS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackageusagestatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(view -> {
//            activityResultSingle.launch(permissionsSingle);
            checkOpenPermissions();
        });
    }


    private void checkOpenPermissions() {

        String packageName = "com.chrome.beta"; // 要查询的应用程序的包名
        PackageManager packageManager = getPackageManager();
        try {


            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);


            StorageStatsManager statsManager = (StorageStatsManager) this.getSystemService(Context.STORAGE_STATS_SERVICE);
            UserHandle handler = UserHandle.getUserHandleForUid(-2);


            StorageStats stats = statsManager.queryStatsForPackage(StorageManager.UUID_DEFAULT, info.packageName, handler);
            long codeBytes = stats.getAppBytes();
            long dataBytes = stats.getDataBytes();
            long cacheBytes = stats.getCacheBytes();
            Log.d(TAG, codeBytes + "==========" + dataBytes + "=============" + cacheBytes);

            UseTimeDataManager instance = UseTimeDataManager.getInstance(this);
            instance.refreshData(1);
            String jsonObjectStr = getJsonObjectStr();
            Log.d(TAG, jsonObjectStr);
            binding.txtResult.setText(jsonObjectStr);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            //这里说明没有权限，没有权限只能查询自身应用大小
            openSetting();
            e.printStackTrace();
        }


    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        PackageUsageStatsActivity.this.startActivity(intent);
    }


    public String getJsonObjectStr() {
        String jsonAppdeTails = "";

        UseTimeDataManager mUseTimeDataManager = UseTimeDataManager.getInstance(this);
        try {
            ArrayList<PackageInfo> packageInfos = mUseTimeDataManager.getmPackageInfoListOrderByTime();
            JSONObject jsonObject2 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < packageInfos.size(); i++) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonArray.put(i, jsonObject.accumulate("count", packageInfos.get(i).getmUsedCount()));
                    jsonArray.put(i, jsonObject.accumulate("name", packageInfos.get(i).getmPackageName()));
                    jsonArray.put(i, jsonObject.accumulate("time", packageInfos.get(i).getmUsedTime()));
                    jsonArray.put(i, jsonObject.accumulate("appname", packageInfos.get(i).getmAppName()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "";
                }

            }
            jsonObject2.put("details", jsonArray);
            jsonAppdeTails = jsonObject2.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        return jsonAppdeTails;
    }

}
