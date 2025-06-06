package con.fire.android2023demo.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import con.fire.android2023demo.databinding.ActivityPackageListBinding;

public class PackageListActivity extends AppCompatActivity {
    static String[] packageNames = new String[]{"com.android.backupconfirm", "com.android.bluetooth", "com.android.calllogbackup", "com.android.certinstaller", "com.android.chrome", "com.android.externalstorage", "com.android.inputdevices", "com.android.keychain", "com.android.location.fused", "com.android.managedprovisioning", "com.android.mms.service", "com.android.pacprocessor", "com.android.phone", "com.android.providers.calendar", "com.android.providers.downloads", "com.android.providers.settings", "com.android.providers.telephony", "com.android.providers.userdictionary", "com.android.proxyhandler", "com.android.server.telecom", "com.android.settings", "com.android.sharedstoragebackup", "com.android.shell", "com.android.systemui", "com.android.vending", "com.android.vpndialogs", "com.google.android.gms", "com.google.android.gsf", "com.google.android.packageinstaller", "com.google.android.setupwizard", "com.google.android.tts", "com.google.android.youtube", "com.android.mtp", "com.android.providers.blockednumber", "com.google.android.apps.maps", "com.google.android.ext.services", "com.google.android.gm", "com.android.stk", "com.android.wallpaperbackup", "com.android.companiondevicemanager", "com.android.egg", "com.android.carrierconfig", "com.google.android.apps.restore", "com.android.providers.downloads.ui", "com.android.ons", "com.google.android.permissioncontroller", "com.android.localtransport", "com.android.dynsystem", "com.google.android.overlay.modules.permissioncontroller.forframework", "com.google.android.overlay.modules.ext.services", "com.whatsapp", "com.google.android.apps.tachyon", "com.google.android.overlay.modules.modulemetadata.forframework", "com.android.wallpapercropper", "com.zhiliaoapp.musically", "com.google.android.webview", "com.facebook.katana", "com.google.android.apps.docs", "com.android.se", "com.google.android.cellbroadcastservice", "com.google.android.networkstack.tethering", "com.google.android.cellbroadcastreceiver", "com.google.android.providers.media.module", "com.google.android.apps.messaging", "com.google.android.googlequicksearchbox", "com.google.android.modulemetadata", "com.google.android.projection.gearhead", "com.google.android.apps.photos", "com.android.emergency", "com.android.providers.media", "com.google.android.documentsui", "com.android.cameraextensions", "com.google.android.videos", "com.android.carrierdefaultapp", "com.android.traceur", "com.google.android.apps.youtube.music", "com.google.android.networkstack", "com.android.providers.contacts", "com.facebook.orca", "com.google.android.marvin.talkback", "com.google.android.apps.wellbeing", "com.google.mainline.telemetry", "com.yellowpepper.pichincha", "com.google.android.gms.supervision", "com.google.android.ondevicepersonalization.services", "com.google.android.sdksandbox", "com.android.nfc", "com.google.android.photopicker", "com.google.android.federatedcompute", "com.google.android.inputmethod.latin", "com.instagram.android", "com.android.storagemanager", "com.google.android.adservices.api", "com.google.android.overlay.modules.captiveportallogin.forframework", "com.google.mainline.adservices", "com.android.bips", "com.android.soundpicker", "com.android.settings.intelligence", "com.einnovation.temu", "com.android.cellbroadcastreceiver", "com.google.android.calendar", "com.android.wallpaper.livepicker", "org.telegram.messenger", "com.google.android.networkstack.tethering.overlay", "com.cash.dusty.sink", "com.google.android.apps.nbu.files", "com.android.wifi.resources.overlay", "com.android.dreams.phototable", "com.android.networkstack.overlay", "com.facebook.lite", "com.mediatek.batterywarning", "com.snaptube.premium", "com.android.networkstack.tethering.overlay", "com.govern.sad.clothing", "com.bancodeguayaquil", "com.mediatek.location.lppe.main", "com.mediatek.ims", "com.mediatek.telephony", "com.google.android.apps.safetyhub", "com.google.android.apps.googleassistant", "com.android.statementservice", "com.mediatek.frameworkresoverlay", "com.android.credentialmanager", "com.android.intentresolver", "com.google.android.healthconnect.controller", "com.netflix.mediaclient", "com.mediatek", "com.android.networkstack.tethering.inprocess.overlay", "com.android.launcher3", "com.lemon.lvoverseas", "com.mediatek.SettingsProviderResOverlay", "com.appdeuna.wallet", "com.debug.loggerui", "com.mediatek.FrameworkResOverlayExt", "com.transsion.carlcare", "com.hoffnung", "com.scorpio.securitycom", "com.sh.smart.caller", "com.transsion.deskclock", "com.transsion.fmradio", "com.transsion.overlaysuw", "com.transsion.repaircard", "com.transsion.statisticalsales", "com.transsion.systemupdate", "com.transsnet.store", "com.gallery20", "com.idea.questionnaire", "com.mediatek.schpwronoff", "com.transsion.plat.appupdate", "com.reallytek.wg", "com.transsion.faceid", "com.transsion.camera", "com.transsion.calculator", "com.transsion.phonemanager", "com.rlk.weathers", "com.transsion.batterylab", "com.transsion.trancare", "com.transsion.soundrecorder", "com.talpa.hibrowser", "com.transsion.notebook", "com.mediatek.capctrl.service", "com.transsion.phonemaster", "com.mediatek.engineermode", "com.transsion.ossettingsext", "com.transsion.nephilim", "com.transsion.overlaysuw.resoverlay", "com.trans Сумоботник", "com.transsion.magicshow", "com.transsion.tabe", "com.mediatek.ygps", "com.mediatek.mdmlsample", "com.transsion.filemanagerx", "com.transsion.teop", "tech.palm.id", "com.android.settings.resoverlay", "com.transsion.tranengine", "com.transsion.hamal", "com.transsion.dtsaudio", "com.mediatek.MtkSettingsResOverlay", "com.funbase.xradio", "com.transsion", "com.google.android.networkstack.overlay", "net.bat.store", "com.transsion.os.typeface", "com.transsion.agingfunction", "com.transsion.letswitch", "com.android.networkstack.inprocess.overlay", "com.mediatek.mdmconfig", "com.dywx.larkplayer", "com.mediatek.cellbroadcastuiresoverlay", "com.spotify.music", "com.android.wallpaperpicker", "cn.wps.moffice_eng", "com.transsion.uxdetector", "com.android.imsserviceentitlement", "com.whatsapp.w4b", "com.goodix.gftest"};

    PackageManager packageManager;
    int count = 0;
    private ActivityPackageListBinding binding;
    private ExecutorService executor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackageListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        packageManager = getPackageManager();
        executor = Executors.newFixedThreadPool(10);
        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//
//                    }
//                }.start();

                long startTime = System.nanoTime();
                doLog("Method took  =========== ms");
                onV2CLick("厄瓜app.txt", new OnPackageCallBack() {
                    @Override
                    public void onFinish() {
                        Log.d("okhttp", "================>>>>>=====onFinish+");
                        long endTime = System.nanoTime();
                        double durationInMillis = (endTime - startTime) / 1_000_000.0;
                        double durationInSeconds = durationInMillis / 1000.0; // 转换为秒
//                        doLog("Method took " + array.length());
                        doLog("Method took " + durationInSeconds + " ms");
                    }
                });


//                executeTasks();

            }
        });
    }

    public void executeTasks() {
        // 创建固定大小的线程池（例如 4 个线程）
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Log.d("ThreadPool", "Task ==============000");

        // 提交任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                // 模拟耗时任务
                try {
                    Thread.sleep(1000);
                    Log.d("ThreadPool", "Task " + taskId + " executed on thread: " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Log.d("ThreadPool", "Task ==============0001");

        // 关闭线程池（不再接受新任务，执行完现有任务后销毁）
        executor.shutdown();
    }

    private void onV2CLick(String fileName, OnPackageCallBack callBack) {

        JSONArray array = new JSONArray();


//        for (String packageN : packageNames) {
//            executor.execute(() -> {
//                try {
//                    Log.d("okhttp", "================>>>>>=====111+" + packageN);
//                    parsePackage(packageN, array);
//                    count++;
//                    if (count == packageNames.length) {
//                        callBack.onFinish();
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//
//
//        }
        count = 0;
        List<String> packageList = new ArrayList<>();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            AssetManager assetManager = getAssets();
            // 打开 assets 中的文件
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // 逐行读取文件内容
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                line = filterString(line);
                packageList.add(line);
            }
//            executor.shutdown();
            // 关闭流
            bufferedReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String name : packageList) {
            try {
                String packageN = name;
                executor.execute(() -> {
                    try {

                        Log.d("okhttp", "================>>>>>=====111+" + Thread.currentThread().getName());
                        parsePackage(packageN, array);
                        count++;
                        if (count == packageList.size()) {
                            callBack.onFinish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        long startTime = System.nanoTime();
//        StringBuilder stringBuilder = new StringBuilder();
//        AssetManager assetManager = getAssets();
//
//        JSONArray array = new JSONArray();
//
//        int corePoolSize = 2; // 核心线程数
//        int maxPoolSize = 4;  // 最大线程数
//        long keepAliveTime = 60; // 非核心线程空闲存活时间（秒）
//        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); // 任务队列
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

//        try {
//            // 打开 assets 中的文件
//            InputStream inputStream = assetManager.open(fileName);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            int count=0;
//            // 逐行读取文件内容
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line).append("\n");
//                line = filterString(line);
//                try {
//
//                    String packageN = line;
////                    executor.execute(() -> {
////                        parsePackage(packageN, array);
////                    });
//                    int ca=count++;
//                    executor.submit(() -> {
//                        try {
//                            Log.d("okhttp",packageN+""+(ca));
//                            parsePackage(packageN, array);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
////            executor.shutdown();
//            // 关闭流
//            bufferedReader.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int count = 0;


//        for (String packageN : packageNames) {
//            executor.execute(() -> {
//                try {
//                    Log.d("okhttp", "================>>>>>=====111+" + packageN);
//                    parsePackage(packageN, array);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });

////            int name = count++;
////            executor.submit(() -> {
////                try {
////                    Log.d("okhttp", "================>>>>>" + name);
////                    parsePackage(packageN, array);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            });
//        }


//        executor.shutdown();
//
//        long endTime = System.nanoTime();
//        double durationInMillis = (endTime - startTime) / 1_000_000.0;
//        double durationInSeconds = durationInMillis / 1000.0; // 转换为秒
//        doLog("Method took " + array.length());
//
//        doLog("Method took " + durationInSeconds + " ms");
    }

    private void parsePackage(String packageN, JSONArray array) {
        try {
            PackageInfo packageInfo = null;
            try {
                packageInfo = packageManager.getPackageInfo(packageN, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (packageInfo != null) {

                String name = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("app_name", name);
                jsonObject.put("package", packageInfo.packageName);
                jsonObject.put("version_name", packageInfo.versionName);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    jsonObject.put("version_code", packageInfo.getLongVersionCode());
                } else {
                    jsonObject.put("version_code", packageInfo.versionCode);
                }
                jsonObject.put("in_time", packageInfo.firstInstallTime);
                jsonObject.put("up_time", packageInfo.lastUpdateTime);
                jsonObject.put("flags", packageInfo.applicationInfo.flags);
                jsonObject.put("app_type", (packageInfo.applicationInfo.flags & 1) == 0 ? "0" : "1");
                array.put(jsonObject);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doLog(String str) {
        Log.d("okhttpdoLog", str);
    }

    public String filterString(String input) {
        if (input == null) {
            return "";
        }
        // 使用正则表达式替换非数字、非英文字符、非星号的内容
        return input.replaceAll("[^a-zA-Z0-9*.]", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭线程池，防止内存泄漏
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }

    interface OnPackageCallBack {
        void onFinish();

    }
}
