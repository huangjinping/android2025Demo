package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import con.fire.android2023demo.databinding.ActivityRemoteconfigBinding;

/* loaded from: classes12.dex */
public class RemoteConfigActivity extends AppCompatActivity {
    private static final String TAG = "okhttps";
    ActivityRemoteconfigBinding binding;
    long startTime = System.currentTimeMillis();
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityRemoteconfigBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        initFirebaseRemoteConfig();
        this.binding.button.setOnClickListener(new View.OnClickListener() { // from class: con.fire.android2023demo.ui.RemoteConfigActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    private void activateConfig() {
        this.mFirebaseRemoteConfig.activate().addOnCompleteListener(this, new OnCompleteListener() { // from class: con.fire.android2023demo.ui.RemoteConfigActivity$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                RemoteConfigActivity.this.m825x3dfad22e(task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$activateConfig$0$con-fire-android2023demo-ui-RemoteConfigActivity  reason: not valid java name */
    public /* synthetic */ void m825x3dfad22e(Task task) {
        if (task.isSuccessful()) {
            boolean updated = ((Boolean) task.getResult()).booleanValue();
            Log.d(TAG, "配置已激活: " + updated);
            applyConfig();
            return;
        }
        Log.d(TAG, "配置激活失败: " + task.getException().getMessage());
    }

    private void onlyFetchRemoteConfig() {
        this.mFirebaseRemoteConfig.fetch().addOnCompleteListener(this, new OnCompleteListener() { // from class: con.fire.android2023demo.ui.RemoteConfigActivity$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                RemoteConfigActivity.this.m827x83a879e6(task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onlyFetchRemoteConfig$1$con-fire-android2023demo-ui-RemoteConfigActivity  reason: not valid java name */
    public /* synthetic */ void m827x83a879e6(Task task) {
        if (task.isSuccessful()) {
            Log.d(TAG, "配置获取成功，等待激活");
            this.binding.button.setEnabled(true);
            return;
        }
        Log.d(TAG, "配置获取失败: " + task.getException().getMessage());
    }

    private void initFirebaseRemoteConfig() {
        this.mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(60L).build();
        this.mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("welcome_message", "Welcome to the app!");
        defaults.put("show_feature", false);
        this.mFirebaseRemoteConfig.setDefaultsAsync(defaults);
        fetchRemoteConfig();
        applyConfig();
        this.mFirebaseRemoteConfig.addOnConfigUpdateListener(new AnonymousClass2());
    }

    private void fetchRemoteConfig() {
        this.mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener() { // from class: con.fire.android2023demo.ui.RemoteConfigActivity$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                RemoteConfigActivity.this.m826x302448b9(task);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$fetchRemoteConfig$2$con-fire-android2023demo-ui-RemoteConfigActivity  reason: not valid java name */
    public /* synthetic */ void m826x302448b9(Task task) {
        if (task.isSuccessful()) {
            boolean updated = ((Boolean) task.getResult()).booleanValue();
            Log.d(TAG, "Config params updated: " + updated);
            applyConfig();
            return;
        }
        Log.d(TAG, "Fetch failed");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyConfig() {
        String welcomeMessage = this.mFirebaseRemoteConfig.getString("welcome_message");
        boolean showFeature = this.mFirebaseRemoteConfig.getBoolean("show_feature");
        long endTime = System.currentTimeMillis();
        long duration = endTime - this.startTime;
        Log.d(TAG, "方法耗时: " + duration + " 毫秒");
        Log.d(TAG, "Welcome Message: " + welcomeMessage);
        Log.d(TAG, "Show Feature: " + showFeature);
        this.binding.txtUpdateresult.setText("方法耗时: " + duration + " 毫秒\n\nWelcome Message: " + welcomeMessage + "\n\nShow Feature: " + showFeature);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: con.fire.android2023demo.ui.RemoteConfigActivity$2  reason: invalid class name */
    /* loaded from: classes12.dex */
    public class AnonymousClass2 implements ConfigUpdateListener {
        AnonymousClass2() {
        }

        @Override // com.google.firebase.remoteconfig.ConfigUpdateListener
        public void onUpdate(ConfigUpdate configUpdate) {
            Log.d(RemoteConfigActivity.TAG, "Updated keys: " + configUpdate.getUpdatedKeys());
            RemoteConfigActivity.this.mFirebaseRemoteConfig.activate().addOnCompleteListener(new OnCompleteListener() { // from class: con.fire.android2023demo.ui.RemoteConfigActivity$2$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    RemoteConfigActivity.AnonymousClass2.this.m828x47312de9(task);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$onUpdate$0$con-fire-android2023demo-ui-RemoteConfigActivity$2  reason: not valid java name */
        public /* synthetic */ void m828x47312de9(Task task) {
            if (task.isSuccessful()) {
                RemoteConfigActivity.this.applyConfig();
            }
        }

        @Override // com.google.firebase.remoteconfig.ConfigUpdateListener
        public void onError(FirebaseRemoteConfigException error) {
            Log.e(RemoteConfigActivity.TAG, "Real-time update failed: " + error.getMessage());
        }
    }
}