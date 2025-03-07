package con.fire.android2023demo.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Map;

import con.fire.android2023demo.databinding.ActivityPermissionnewBinding;
import con.fire.android2023demo.utils.LogUtils;

//https://segmentfault.com/a/1190000040910293
public class PermissionNewActivity extends AppCompatActivity {
    String TAG = "okhttp";
    ActivityPermissionnewBinding binding;
    ActivityResultLauncher activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            LogUtils.logS(TAG, result);
            /**
             * {"android.permission.ACCESS_COARSE_LOCATION":false,"android.permission.READ_SMS":true,"android.permission.READ_CALL_LOG":true}
             */
        }
    });
    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG};

    String permissionsSingle = Manifest.permission.READ_PHONE_STATE;

    ActivityResultLauncher activityResultSingle = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            LogUtils.logS(TAG, result);

        }
    });
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            LogUtils.logS(TAG, result.getResultCode() + "");

        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionnewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSingle();
            }
        });
        binding.buttonMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestModel();

            }
        });
        binding.buttonResult.setOnClickListener(v -> regForActivity());

    }

    private void regForActivity() {
        Intent intent = new Intent(this, SelectContractActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    private void requestSingle() {
        /**
         * 单个权限
         */
        activityResultSingle.launch(permissionsSingle);
    }

    private void requestModel() {
        /**
         * 权限列表
         */
        activityResultLauncher.launch(permissions);
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
