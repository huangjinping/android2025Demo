package con.fire.android2023demo.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.util.concurrent.Executor;

import con.fire.android2023demo.databinding.ActivitySystemloginBinding;

//https://developer.android.google.cn/security/fraud-prevention/authentication?hl=zh-cn#java
//
public class SystemLoginActivity extends AppCompatActivity {


    final String TAG = "sysLogin";
    ActivitySystemloginBinding binding;
    // 创建BiometricPrompt实例
    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySystemloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBiometricPrompt();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBiometricPrompt(v);
            }
        });

    }

    // 初始化BiometricPrompt（在Activity或Fragment中）
    private void initBiometricPrompt() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            Executor executor = ContextCompat.getMainExecutor(this);

            biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    handleAuthenticationError();
                    Log.d("sysLogin", errorCode + "onAuthenticationError" + errString);

                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Gson gson = new Gson();
                    Log.d("sysLogin", result.getAuthenticationType() + "onAuthenticationSucceeded==" + gson.toJson(result.getCryptoObject()));
                    login();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Log.d("sysLogin", "onAuthenticationFailed");
                }
            });

        }


    }

    private void showBiometricPrompt(View view) {
//        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("面容登录").setDescription("请使用面容进行登录").setNegativeButtonText("取消").build();
//        biometricPrompt.authenticate(promptInfo);

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for my app").setSubtitle("Log in using your biometric credential").setNegativeButtonText("Use account password").build();
        biometricPrompt.authenticate(promptInfo);


    }

    private void login() {
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
        // 执行登录相关的操作，如验证用户名和密码（虽然在这个场景下可能不需要） // 然后跳转到主界面或其他页面
    }

    private void handleAuthenticationError() {
        // 显示错误信息或执行其他错误处理逻辑
        Toast.makeText(this, "handleAuthenticationError", Toast.LENGTH_SHORT).show();

    }

}