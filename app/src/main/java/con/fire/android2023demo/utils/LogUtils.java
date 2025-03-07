package con.fire.android2023demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class LogUtils {


    public static void logSLocation(Context context, String TAG, Object object) {

        String result = "";
        if (object instanceof String) {
            result = (String) object;
        } else {
            Gson gson = new Gson();
            result = gson.toJson(object);
        }
        final SharedPreferences prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String old = prefs.getString(TAG, "");
        result = old + "\n" + result;
        prefs.edit().putString(TAG, result).apply();
        logS(TAG,result);
    }

    public static void logS(String TAG, Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof String) {
            Log.d(TAG, "" + object.toString());
        } else {
            Gson gson = new Gson();
            Log.d(TAG, "" + gson.toJson(object));
        }

    }
}
