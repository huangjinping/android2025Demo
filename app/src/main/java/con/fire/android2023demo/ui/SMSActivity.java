package con.fire.android2023demo.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.facebook.appevents.integrity.IntegrityManager;
import con.fire.android2023demo.databinding.ActivitySmsBinding;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* loaded from: classes12.dex */
public class SMSActivity extends AppCompatActivity {
    private static final int REQUEST_DEFAULT_SMS = 1;
    private static final String TAG = "SMSActivity";
    ActivitySmsBinding binding;
    String lantingxu = "永和九年，岁在癸丑，暮春之初，会于会稽山阴之兰亭，修禊事也。群贤毕至，少长咸集。此地有崇山峻岭，茂林修竹，又有清流激湍，映带左右，引以为流觞曲水，列坐其次。虽无丝竹管弦之盛，一觞一咏，亦足以畅叙幽情。\n是日也，天朗气清，惠风和畅。仰观宇宙之大，俯察品类之盛，所以游目骋怀，足以极视听之娱，信可乐也。\n夫人之相与，俯仰一世。或取诸怀抱，悟言一室之内；或因寄所托，放浪形骸之外。虽趣舍万殊，静躁不同，当其欣于所遇，暂得于己，快然自足，不知老之将至；及其所之既倦，情随事迁，感慨系之矣。向之所欣，俯仰之间，已为陈迹，犹不能不以之兴怀，况修短随化，终期于尽！古人云：“死生亦大矣。”岂不痛哉！\n每览昔人兴感之由，若合一契，未尝不临文嗟悼，不能喻之于怀。固知一死生为虚诞，齐彭殇为妄作。后之视今，亦犹今之视昔，悲夫！故列叙时人，录其所述，虽世殊事异，所以兴怀，其致一也。后之览者，亦将有感于斯文。";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySmsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        this.binding.btnSend.setOnClickListener(new View.OnClickListener() { // from class: con.fire.android2023demo.ui.SMSActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SMSActivity.this.onSend();
            }
        });
        this.binding.btnWrite.setOnClickListener(new View.OnClickListener() { // from class: con.fire.android2023demo.ui.SMSActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SMSActivity.this.onWrite();
            }
        });
        this.binding.editPhoneNumber.setText("1234567890");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWrite() {
        if (!isDefaultSmsApp()) {
            requestDefaultSmsApp();
        } else {
            writeSmsToInbox("1234567890", "这是一条测试短信", System.currentTimeMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (isDefaultSmsApp()) {
                Log.d(TAG, "已成为默认短信应用");
                writeSmsToInbox("1234567890", "这是一条测试短信", System.currentTimeMillis());
                return;
            }
            Log.d(TAG, "用户未设置为默认短信应用");
        }
    }

    private void writeSmsToInbox(String phoneNumber, String messageBody, long timestamp) {
        try {
            ContentValues values = new ContentValues();
            values.put(IntegrityManager.INTEGRITY_TYPE_ADDRESS, phoneNumber);
            values.put("body", messageBody);
            values.put("date", Long.valueOf(timestamp));
            values.put("read", (Integer) 0);
            values.put("type", (Integer) 1);
            Uri uri = getContentResolver().insert(Telephony.Sms.CONTENT_URI, values);
            if (uri != null) {
                Log.d(TAG, "短信写入成功: " + uri.toString());
            } else {
                Log.d(TAG, "短信写入失败");
            }
        } catch (Exception e) {
            Log.e(TAG, "写入短信出错: " + e.getMessage());
        }
    }

    private boolean isDefaultSmsApp() {
        String defaultApp = Telephony.Sms.getDefaultSmsPackage(this);
        String myPackage = getPackageName();
        return myPackage.equals(defaultApp);
    }

    private void requestDefaultSmsApp() {
        Intent intent = new Intent("android.provider.Telephony.ACTION_CHANGE_DEFAULT");
        intent.putExtra("package", getPackageName());
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v1, types: [con.fire.android2023demo.ui.SMSActivity$3] */
    public void onSend() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.SEND_SMS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.SEND_SMS"}, 1);
            return;
        }
        int count = 1;
        try {
            String phone = this.binding.editTextNumber.getText().toString();
            BigDecimal limitDecimal = new BigDecimal(phone);
            BigDecimal integerPart = limitDecimal.setScale(0, RoundingMode.DOWN);
            count = integerPart.intValue();
        } catch (Exception e) {
            e.getMessage();
        }
        final int finalCount = count;
        new Thread() { // from class: con.fire.android2023demo.ui.SMSActivity.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                for (int i = 0; i < finalCount; i++) {
                    String phone2 = SMSActivity.this.binding.editPhoneNumber.getText().toString().trim();
                    SMSActivity.this.sendSms(phone2, "这是一条测试短信这是一条测试短信这是一条测试短信这是一条测试短信这是一条测试短信这是一条测试短信这是一条测试短信" + System.currentTimeMillis());
                }
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.d(TAG, "短信发送成功，已记录到发件箱");
        } catch (Exception e) {
            Log.e(TAG, "发送短信失败: " + e.getMessage());
        }
    }
}