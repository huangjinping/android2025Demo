package con.fire.android2023demo.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;


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
                Uri targetUri = appLinkData.getTargetUri();
                Log.d("AttributionData", "---------------------------1");

                // 2. 获取拓展字段（extras 包含平台/位置信息）
                Bundle extras = appLinkData.getArgumentBundle();
                if (extras != null) {
                    // 平台相关字段
                    String publisherPlatform = extras.getString("publisher_platform"); // 对应 publisher_platform
                    String platformPosition = extras.getString("platform_position"); // 对应 platform_position
                    boolean isInstagram = "instagram".equals(publisherPlatform); // 对应 is_instagram
                    boolean isAn = extras.getBoolean("is_an", false); // 对应 is_an

                    // 3. 广告名称/目标字段（需通过 Meta Graph API 补充，SDK 不直接返回）
                    // ad_objective_name、campaign_name、adgroup_name 等需用 adId 调用 Graph API 获取
                }
                if (appLinkData != null) {
                    Log.d("AttributionData", "---------------------------2");
                    Bundle attributionData = appLinkData.getArgumentBundle();
                    if (attributionData != null) {
                        // 1. 获取广告归因参数
                        String adId = attributionData.getString("fb_ad_id");
                        String campaignId = attributionData.getString("fb_campaign_id");
                        String placement = attributionData.getString("fb_placement");

                        // 2. 获取UTM参数
                        String utmSource = attributionData.getString("utm_source");
                        String utmCampaign = attributionData.getString("utm_campaign");

                        // 3. 获取深度链接参数
                        String targetUrl = attributionData.getString("al_target_url");
                        Bundle appLinkDataNested = attributionData.getBundle("al_applink_data");

                        // 4. 打印所有参数（调试用）
                        for (String key : attributionData.keySet()) {
                            Object value = attributionData.get(key);
                            Log.d("AttributionData", "Key: " + key + ", Value: " + value);
                        }
                    }
                }
                if (appLinkData != null) {
                    // 获取归因参数（包含广告 ID、campaign ID 等）
                    String ref = appLinkData.getRef();
                    Bundle refererData = appLinkData.getRefererData();

                    String adId = targetUri.getQueryParameter("ad_id");
                    String campaignId = targetUri.getQueryParameter("campaign_id");
                    String adgroupId = targetUri.getQueryParameter("adgroup_id");
                    String site = targetUri.getQueryParameter("site");  // 如 "ig"
                    String placement = targetUri.getQueryParameter("placement");  // 如 "instream_video"

                    // 自定义映射（生成你的 JSON 类似结构）
                    String publisherPlatform = "ig".equals(site) ? "instagram" : ("an".equals(site) ? "audience_network" : "facebook");
                    String platformPosition = "instagram_" + placement.replace("_", "");  // 示例：生成 "instagram_reels_instream"
                    boolean isInstagram = "ig".equals(site);
                    boolean isAn = "an".equals(site);

//                    JSONObject attributionData = appLinkData.getArgumentBundle().getJSONObject("referrer_data");
//                    if (attributionData != null) {
//                        try {
//                            // 提取你需要的字段
//                            String adId = attributionData.getString("ad_id");
//                            String campaignId = attributionData.getString("campaign_id");
//                            String publisherPlatform = attributionData.getString("publisher_platform");
//                            boolean isInstagram = attributionData.getBoolean("is_instagram");
//
//                            // 业务处理：存储/上报数据
//                            handleAdAttribution(adId, campaignId, publisherPlatform, isInstagram);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }
        });
    }

    private static void handleAdAttribution(String adId, String campaignId, String platform, boolean isIg) {
        // 例如：上报到服务器、存入本地数据库
//        Log.d("AdAttribution", "ad_id: " + adId + ", campaign_id: " + campaignId + ", platform: " + platform);
    }
}
