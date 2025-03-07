package con.fire.android2023demo.ui.login;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;

import con.fire.android2023demo.databinding.ActivityEmailloginBinding;


/**
 * 电话号码提示
 * https://developers.google.cn/identity/phone-number-hint/android?authuser=3&%3Bhl=zh-cn&hl=zh-cn
 * <p>
 * https://developers.google.com/identity/sms-retriever/user-consent/request?hl=en
 */
public class EmailLoginActivity extends AppCompatActivity {

    final int REQUEST_CODE_EMAIL_PICKER = 100;
    private ActivityEmailloginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btbEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmail();
            }
        });
    }

    private void getEmail() {
        try {
            HintRequest hintRequest = new HintRequest.Builder().setEmailAddressIdentifierSupported(true).build();
            CredentialsOptions options = new CredentialsOptions.Builder().forceEnableSaveDialog().build();
            // 下面这行代码在API34上运行会异常
            PendingIntent intent = Credentials.getClient(this, options).getHintPickerIntent(hintRequest);
            startIntentSenderForResult(intent.getIntentSender(), REQUEST_CODE_EMAIL_PICKER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // onActivityResult中
        if (requestCode == REQUEST_CODE_EMAIL_PICKER) {
            String email = "";
            if (resultCode == RESULT_OK && data != null) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null && credential.getId() != null) {
                    email = credential.getId();
                }
            }
            if (!email.isEmpty()) {
                // 填充邮箱
                binding.editTextEmail.setText(email);
            }
        }
    }
}
