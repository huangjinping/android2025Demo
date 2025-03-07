package con.fire.android2023demo.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import con.fire.android2023demo.utils.LogUtils;

public class InstallTrackingReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //处理referrer参数
        String referrer;

        if (intent != null) {
            referrer = intent.getStringExtra("referrer");
            Map<String, String> map = new HashMap<>();
            map.put("install_referrer", referrer);
            // 得到相应的广告信息 是url 参数格式，需要解析
            LogUtils.logSLocation(context,"install_referrer",map);

        }
    }
}