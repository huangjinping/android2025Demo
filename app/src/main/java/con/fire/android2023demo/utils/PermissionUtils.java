package con.fire.android2023demo.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    public static boolean checkPermission(Context context, String[] perm) {
        for (String item : perm) {
            if (ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {

                return false;
            }
        }
        return true;
    }
}
