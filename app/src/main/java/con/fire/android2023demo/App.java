package con.fire.android2023demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import java.util.Map;

import con.fire.android2023demo.utils.CrashHandler;
import con.fire.android2023demo.utils.LogUtils;
import me.jessyan.autosize.AutoSizeConfig;

//https://blog.csdn.net/qq_48656522/article/details/126011280
public class App extends Application implements Thread.UncaughtExceptionHandler {


    public static App application;

    String EVENT_REGISTRATION = "EVENT_REGISTRATION";
    String EVENT_SUBSCRIBE = "EVENT_REGISTRATION";
    String EVENT_ADDED_TO_CART = "EVENT_SUBSCRIBE";
    String EVENT_CHECKOUT = "EVENT_SUBSCRIBE";

    public static App getAppContext() {

        return application;
    }


    private void upCatchMsg(Throwable e) {
        Log.d("upCatchMsg", "" + e.toString());

    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//            upCatchMsg(e); // 读取异常堆栈并上传
//            if (t == Looper.getMainLooper().getThread()) {
//                // 如果发生异常的线程是主线程，则保护主线程不闪退
//                while (true) {
//                    try {
//                        Looper.loop();
//                    } catch (Throwable e1) {
//                    }
//                }
//            }
//        });

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
//        OkGo.getInstance().init(this);

//        Branch.enableTestMode();
//        Branch.getAutoInstance(this);

//        Thread.setDefaultUncaughtExceptionHandler(this);
//        BugCrash.initStatus(this);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        startReferrer(this);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        AppEventsLogger.activateApp(this);
        AppsFlyerLib.getInstance().init("ZUwwP6a2jFqoXzxtrszHRn", new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                Gson gson = new Gson();
                Log.d("okhttp9", "=========onConversionDataSuccess=======>>>" + gson.toJson(map));

            }

            @Override
            public void onConversionDataFail(String s) {
                Log.d("okhttp9", "=========onConversionDataFail=======>>>" + s);

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d("okhttp9", "====onAttributionFailure============>>>" + s);

            }
        }, this);
        AppsFlyerLib.getInstance().start(this);

        application = this;

    }

    public void showToastApp() {
        Toast.makeText(application, "showToastApp", Toast.LENGTH_SHORT).show();
    }


    public void startReferrer(Context context) {

        try {
            final InstallReferrerClient installReferrerClient = InstallReferrerClient.newBuilder(context).build();
            installReferrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK:
                            if (installReferrerClient != null) {
                                try {
                                    ReferrerDetails response = installReferrerClient.getInstallReferrer();
                                    String referrer = response.getInstallReferrer();// 你要得referrer值
                                    long referrerClickTime = response.getReferrerClickTimestampSeconds();
                                    long appInstallTime = response.getInstallBeginTimestampSeconds();
                                    String version = response.getInstallVersion();

                                    StringBuilder builder = new StringBuilder();
                                    builder.append("referrer:" + referrer);
                                    builder.append("\n");
                                    builder.append("referrerClickTime:" + referrerClickTime);
                                    builder.append("\n");
                                    builder.append("appInstallTime:" + appInstallTime);
                                    builder.append("\n");
                                    builder.append("version:" + version);

                                    Log.d("InstallReferrerHelper", builder.toString());

                                    LogUtils.logSLocation(context, "InstallReferrerHelper", builder.toString());

                                    installReferrerClient.endConnection();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    Log.e("InstallReferrerHelper", ex.toString());
                                    LogUtils.logSLocation(context, "InstallReferrerHelper", ex.toString());

                                }
                            }

                            break;
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                            // API not available on the current Play Store app
                            Log.d("InstallReferrerHelper", "FEATURE_NOT_SUPPORTED");
                            LogUtils.logSLocation(context, "InstallReferrerHelper", "FEATURE_NOT_SUPPORTED");

                            break;
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                            // Connection could not be established
                            Log.d("InstallReferrerHelper", "SERVICE_UNAVAILABLE");
                            LogUtils.logSLocation(context, "InstallReferrerHelper", "SERVICE_UNAVAILABLE");

                            break;
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            });
        } catch (Exception ex) {
            LogUtils.logSLocation(context, "InstallReferrerHelper", ex.toString());
            Log.e("InstallReferrerHelper", ex.toString());
        }
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {


        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder reason = new StringBuilder(e.toString());
        if (elements != null && elements.length > 0) {
            for (StackTraceElement element : elements) {
                reason.append("\n");
                reason.append(element.toString());
            }
        }

        Log.d("okhttp", reason.toString());
//        Intent intent = new Intent(this, PostErrorService.class);
//        intent.putExtra("error", reason.toString());
//        startService(intent);
//
        Log.d("okhttp", "=====uncaughtException=======");


//        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, e);

    }

//
//    private void  asd(){
//        val packageManager: PackageManager = getPackageManager()
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 12L and above
//            // Use QUERY_ALL_PACKAGES if necessary
//            val apps: List<PackageInfo> =
//            packageManager.getInstalledPackages(PackageManager.MATCH_ALL)
//
//            Toast.makeText(this,apps.size.toString(),Toast.LENGTH_LONG ).show()
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11 and above
//            // Consider using specific intents or declaring QUERY_ALL_PACKAGES in manifest
//            val launcherIntent: Intent = Intent(Intent.ACTION_MAIN, null)
//            launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER)
//            val resolveInfos: List<ResolveInfo> =
//            packageManager.queryIntentActivities(launcherIntent, 0)
//
//
//            Toast.makeText(this,resolveInfos.size.toString(),Toast.LENGTH_LONG ).show()
//        } else {
//            // For older versions, you can directly query all packages
//            val apps: List<PackageInfo> = packageManager.getInstalledPackages(0)
//
//            Toast.makeText(this,apps.size.toString(),Toast.LENGTH_LONG ).show()
//        }
//    }
}
