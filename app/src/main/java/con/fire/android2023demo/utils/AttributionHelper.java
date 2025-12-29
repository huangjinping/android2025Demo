package con.fire.android2023demo.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AttributionHelper {

    // 初始化 Facebook SDK（建议在 Application 中调用）
    public static void initFacebookSDK(Context context) {
        FacebookSdk.sdkInitialize(context);
//        AppEventsLogger.activateApp(context);
    }

    // 获取 Facebook 广告归因数据
    public static void getFacebookAdAttribution(Context context) {
        Log.d("AttributionData", "---------------------------0");

        AppLinkData.fetchDeferredAppLinkData(context, new AppLinkData.CompletionHandler() {
            @Override
            public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {

                
                String rootRef = appLinkData.getRef();
                Uri targetUri = appLinkData.getTargetUri();
                JSONObject JSONObject = appLinkData.getAppLinkData();
                Bundle attributionData = appLinkData.getArgumentBundle();
                Map<String, Object> allData = new HashMap<>();
                if (rootRef != null) {
                    allData.put("rootRef", rootRef + "");
                }
                if (targetUri != null) {
                    allData.put("rootUri", targetUri.toString() + "");
                }
                if (JSONObject != null) {
                    allData.put("appLinkData", JSONObject.toString());
                }
                if (attributionData != null) {
                    Map<String, String> attributionDataMap = new HashMap<>();
                    for (String key : attributionData.keySet()) {
                        Object value = attributionData.get(key);
                        attributionDataMap.put(key, value + "");
                    }
                    allData.put("attributionDataMap", attributionDataMap);
                }


            }
        });
    }

    private static void handleAdAttribution(String adId, String campaignId, String platform, boolean isIg) {
        // 例如：上报到服务器、存入本地数据库
//        Log.d("AdAttribution", "ad_id: " + adId + ", campaign_id: " + campaignId + ", platform: " + platform);
    }
}
