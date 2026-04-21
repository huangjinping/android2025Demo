package con.fire.android2023demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashs);

        startActivity(new Intent(SplashActivity.this, Hao123ActivityC.class));


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//
//            }
//        }, 2000);

    }

    private void 初始化af() {
        AppsFlyerLib.getInstance().init("Af 的key", new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                if (conversionData != null && !conversionData.isEmpty()) {
                    String mediaSource = getValue(conversionData, "media_source");
                    String campaign = getValue(conversionData, "campaign");
                    String install_time = getValue(conversionData, "install_time");

                    String adset = getValue(conversionData, "adset");
                    String adgroup = getValue(conversionData, "adgroup");
                    String adgroup_name = getValue(conversionData, "adgroup_name");
                    String af_adset = getValue(conversionData, "af_adset");
                    String af_ad = getValue(conversionData, "af_ad");


                    String appsflyerId = AppsFlyerLib.getInstance().getAppsFlyerUID(SplashActivity.this);
                    if (appsflyerId == null) {
                        appsflyerId = "";
                    }

                    String finalAppsflyerId = appsflyerId;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            try {
//                                //没有gaid 获取gaid
//                                String gaid =  CommonUtil.getNonNullText(DeviceUtils.getGAID());
//                                if (!TextUtils.isEmpty(gaid)) {
//
//                                } else {
//                                    gaid = "00000000-0000-0000-0000-000000000000";
//                                }
//
//                                String finalGaid = gaid;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Map<String, String> map = new HashMap<>();
//                                        map.put("mediaSource", mediaSource);
//                                        map.put("campaign", campaign);
//                                        map.put("installTime", install_time);
//
//                                        map.put("adset", adset);
//                                        map.put("adgroup", adgroup);
//                                        map.put("adgroup_name", adgroup_name);
//                                        map.put("af_adset", af_adset);
//                                        map.put("af_ad", af_ad);
//
//                                        map.put("appsflyerId", finalAppsflyerId);
//                                        map.put("gaid", finalGaid);
//
//                                        Gson gson = new Gson();
//                                        String json = gson.toJson(map);
//                                        // 在这里打点 把这个json传到。 /anon/uploadOperation 中data字段里面
//                                    }
//                                });
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                    }).start();

                }
            }

            @Override
            public void onConversionDataFail(String s) {
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
            }

            @Override
            public void onAttributionFailure(String s) {
            }
        }, this);
        AppsFlyerLib.getInstance().start(this);
        AppEventsLogger.activateApp(getApplication());
    }

    private String getValue(Map<String, Object> map, String key) {
        if (map == null || !map.containsKey(key)) return "";
        Object val = map.get(key);
        return val == null ? "" : val.toString();
    }
}
