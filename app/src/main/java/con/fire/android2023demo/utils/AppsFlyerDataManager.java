package con.fire.android2023demo.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppsFlyerDataManager {
    private static final String TAG = "AppsFlyerData";
    private static volatile AppsFlyerDataManager INSTANCE;
    private final Context mContext;

    // 私有构造方法
    private AppsFlyerDataManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    // 单例获取
    public static AppsFlyerDataManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppsFlyerDataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppsFlyerDataManager(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化 AppsFlyer SDK
     *
     * @param devKey AppsFlyer 后台获取的 DevKey
     * @param
     */
    public void initAppsFlyer(String devKey) {
        AppsFlyerLib appsFlyerLib = AppsFlyerLib.getInstance();
        // 测试阶段开启调试日志，上线时关闭
        appsFlyerLib.setDebugLog(true);
        // 初始化 SDK 并设置归因监听器
        appsFlyerLib.init(devKey, getConversionListener(), mContext);
        // 启动 SDK
//        appsFlyerLib.start(mContext, appId);
        appsFlyerLib.start(mContext);

    }

    /**
     * 获取归因数据的监听器
     */
    private AppsFlyerConversionListener getConversionListener() {
        return new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                Gson gson = new Gson();
                Log.d("okhttp9", "=========onConversionDataSuccess=======>>>" + gson.toJson(conversionData));

                if (conversionData != null && !conversionData.isEmpty()) {
                    // 1. 获取 media_source（媒体来源）
                    String mediaSource = conversionData.containsKey("media_source") ? (String) conversionData.get("media_source") : "unknown";

                    // 2. 获取 campaign（推广活动）
                    String campaign = conversionData.containsKey("campaign") ? (String) conversionData.get("campaign") : "unknown";

                    // 3. 获取 install_time（安装时间，毫秒级时间戳）
                    String installTime = conversionData.containsKey("install_time") ? (String) conversionData.get("install_time") : "unknown";

                    // 4. 获取 appsflyer_id（同步获取）
                    String appsflyerId = AppsFlyerLib.getInstance().getAppsFlyerUID(mContext);
                    if (appsflyerId == null) appsflyerId = "unknown";


                    // 5. 异步获取 GAID
                    String finalAppsflyerId = appsflyerId;
                    getGAID(gaid -> {
                        // 所有数据获取完成，可在此处处理（如上传服务器、本地存储）
                        Log.d(TAG, "===== AppsFlyer 归因数据 =====");
                        Log.d(TAG, "appsflyer_id: " + finalAppsflyerId);
                        Log.d(TAG, "gaid: " + gaid);
                        Log.d(TAG, "install_time: " + installTime);
                        Log.d(TAG, "media_source: " + mediaSource);
                        Log.d(TAG, "campaign: " + campaign);
                    });
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.e(TAG, "获取归因数据失败: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                // App 打开归因（可选处理，非核心需求）
                Gson gson = new Gson();
                Log.d("okhttp9", "=========onAppOpenAttribution=======>>>" + gson.toJson(attributionData));

            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.e(TAG, "归因失败: " + errorMessage);
            }
        };
    }

    /**
     * 异步获取 GAID（Google Advertising ID）
     *
     * @param callback 回调返回 GAID
     */
    private void getGAID(GAIDCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String gaid = "unknown";
            try {
                // 兼容低版本系统
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mContext);
                    // 仅当用户未开启广告跟踪限制时，获取 GAID
                    if (!adInfo.isLimitAdTrackingEnabled()) {
                        gaid = adInfo.getId() != null ? adInfo.getId() : "unknown";
                    }
                }
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.e(TAG, "Google Play 服务不可用: " + e.getMessage());
            } catch (GooglePlayServicesRepairableException e) {
                Log.e(TAG, "Google Play 服务需要修复: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IO 异常: " + e.getMessage());
            } finally {
                // 回到主线程返回结果（可选，根据需求调整）
                callback.onGAIDResult(gaid);
                executor.shutdown();
            }
        });
    }

    /**
     * GAID 获取回调接口
     */
    public interface GAIDCallback {
        void onGAIDResult(String gaid);
    }
}