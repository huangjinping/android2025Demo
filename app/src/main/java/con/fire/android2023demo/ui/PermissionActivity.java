package con.fire.android2023demo.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import con.fire.android2023demo.databinding.ActivityPermissionBinding;

public class PermissionActivity extends AppCompatActivity {
    final int requestPermissionCode = 100;
    final int requestPermissionCodeV2 = 101;
    ActivityPermissionBinding binding;
    String[] permissionArr = {Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CALENDAR, Manifest.permission.ACCESS_COARSE_LOCATION};
    Map<String, String> hashMap;
    int requestCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hashMap = new HashMap<>();
        hashMap.put(Manifest.permission.READ_CALL_LOG, "需要获取通话记录权限");
        hashMap.put(Manifest.permission.READ_CALENDAR, "需要获取读取日历权限");
        hashMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, "需要获取读取您的定位权限");
        AlertDialog.Builder builder=null;

        binding.buttonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, Manifest.permission.CALL_PHONE)) {
//                }
                requestPermissions();

            }
        });
        binding.buttonTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionsV2();
            }
        });
    }

    private void requestPermissionsV2() {
        boolean result = requestPermission(PermissionActivity.this, permissionArr, requestPermissionCodeV2);
        if (result) {
            Toast.makeText(PermissionActivity.this, "有权限所有权限 读取json", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PermissionActivity.this, "直接获取权限", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestPermissions() {
        boolean result = requestPermission(PermissionActivity.this, permissionArr, requestPermissionCode);
        if (result) {
            Toast.makeText(PermissionActivity.this, "有权限所有权限 读取json", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PermissionActivity.this, "开始申请权限", Toast.LENGTH_SHORT).show();
//                    showSetting();
        }
    }

    private void showSetting() {

        StringBuilder build = new StringBuilder();
        for (String name : permissionArr) {
            if (ActivityCompat.checkSelfPermission(this, name) != PackageManager.PERMISSION_GRANTED) {
                build.append(hashMap.get(name) + "\n");
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage(build.toString()).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (checkOpenSetting(permissionArr)) {
                    requestPermissions();
                } else {
                    toSetting();
                }


            }
        }).show();
    }


    private void showSettingV2() {

        StringBuilder build = new StringBuilder();
        for (String name : permissionArr) {
            if (ActivityCompat.checkSelfPermission(this, name) != PackageManager.PERMISSION_GRANTED) {
                build.append(hashMap.get(name) + "\n");
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage(build.toString()).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toSetting();


            }
        }).show();
    }

    private boolean checkOpenSetting(String... args) {

        boolean result = false;
        for (String item : args) {
            if (ActivityCompat.checkSelfPermission(this, item) == PackageManager.PERMISSION_GRANTED) {
                continue;
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, item)) {
                result = true;
            }
        }
        return result;
    }

    public void toSetting() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean requestPermission(Context context, String[] permissions, int requestCode) {
        boolean result = true;
        List<String> permissionList = new ArrayList<>();
        for (String item : permissions) {
            if (ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(item);
                result = false;
            }
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(PermissionActivity.this, permissionList.toArray(new String[permissionList.size()]), requestCode);
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestPermissionCode == requestCode) {
            if (checkPermission(PermissionActivity.this, permissionArr)) {
                Toast.makeText(PermissionActivity.this, "判断有权限--开始读取json", Toast.LENGTH_SHORT).show();
            } else {
                showSetting();
            }
        } else if (requestPermissionCodeV2 == requestCode) {

            requestCount++;
            Log.d("PermissionsV2", "==================000=" + requestCount);
            if (checkPermission(PermissionActivity.this, permissionArr)) {
                Toast.makeText(PermissionActivity.this, "判断有权限--开始读取json", Toast.LENGTH_SHORT).show();
            } else {
                if (requestCount > 2) {
                    requestCount = 0;
                    showSettingV2();
                } else {
                    requestPermission(this, permissionArr, requestCode);
                }
            }
        }

    }

    public boolean checkPermission(Context context, String[] perm) {
        for (String item : perm) {
            if (ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
