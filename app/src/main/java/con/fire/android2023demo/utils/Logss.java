package con.fire.android2023demo.utils;

import android.util.Log;

import com.google.gson.Gson;

public class Logss {


    public static void log(String tag, Object o) {
        try {
            if (o instanceof String) {
                Log.d(tag, o.toString());

            } else {
                Gson gson = new Gson();
                Log.d(tag, gson.toJson(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
