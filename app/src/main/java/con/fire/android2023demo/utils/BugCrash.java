package con.fire.android2023demo.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.tencent.bugly.crashreport.CrashReport;

public class BugCrash {
    public static void initStatus(Application application) {
        try {
            try {
                CrashReport.initCrashReport(application, "eabb720eb0", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUserId(Context context, String userid) {

        try {
            CrashReport.setUserId(context, userid);
            CrashReport.setDeviceModel(context, Build.MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
