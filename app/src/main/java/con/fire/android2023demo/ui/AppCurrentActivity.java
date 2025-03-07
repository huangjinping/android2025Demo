package con.fire.android2023demo.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import con.fire.android2023demo.databinding.ActivityAppcurrentBinding;
import con.fire.android2023demo.utils.PhoneUtils;

public class AppCurrentActivity extends AppCompatActivity {

    ActivityAppcurrentBinding binding;

    String[] permissions = {Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE};
    ActivityResultLauncher activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            String phoneNumber = PhoneUtils.getPhoneNumber(AppCurrentActivity.this);
            binding.button12.setText("" + phoneNumber);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppcurrentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAction();
            }
        });

    }

    private void startAction() {
        activityResultLauncher.launch(permissions);

    }

}
