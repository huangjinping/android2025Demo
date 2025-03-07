package con.fire.android2023demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("okhttpss2", "============SMSReceiver=========");
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                // 解析短信内容，提取验证码
            }
        }
    }
}
