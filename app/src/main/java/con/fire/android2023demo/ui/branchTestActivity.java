package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fingerprintjs.android.fingerprint.Fingerprinter;
import com.fingerprintjs.android.fingerprint.FingerprinterFactory;

import java.text.SimpleDateFormat;

import con.fire.android2023demo.R;
import con.fire.android2023demo.utils.UniqueId;
import io.branch.referral.util.BranchEvent;

public class branchTestActivity extends AppCompatActivity {
    private Button button2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branchtest);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("s---d", UniqueId.getId());
                Log.d("s---d-", UniqueId.getAgent());
//                onBranchLog();
            }
        });


        onstFinger();
    }

    private void onstFinger() {

        Log.d("onstFinger", "onstFinger========1");

// Initialization
        Fingerprinter fingerprinter = FingerprinterFactory.create(this);

// Usage
        fingerprinter.getFingerprint(Fingerprinter.Version.V_5, fingerprint -> {
            Log.d("onstFinger", "onstFinger========2");

            // use fingerprint
            return null;
        });

        fingerprinter.getDeviceId(Fingerprinter.Version.V_5, deviceIdResult -> {
            String deviceId = deviceIdResult.getDeviceId();
            // use deviceId
            Log.d("onstFinger", "onstFinger========3" + deviceId);

            return null;
        });

    }


    private void onBranchLog() {
        long time7 = System.currentTimeMillis();//获取系统long格式时间的第二种方法
        String p2 = "yyyy_MM_dd_HH_mm_ss";
        SimpleDateFormat sFormat2 = new SimpleDateFormat(p2);
        String time6 = sFormat2.format(time7);
        String eventName = "Te9" + time6 + "HS";
        Log.d("eventName", eventName);
        BranchEvent be = new BranchEvent(eventName);
        be.logEvent(this);
    }
}
