package con.fire.android2023demo.utils;

import android.content.Context;

public class Constants {
    public static String CLIENT_ID_VALUE = "151"; //


    public static void resettingSsid() {
        CLIENT_ID_VALUE = "151";
    }

    public static void sSsid(Context ccc, String ssid) {
        CLIENT_ID_VALUE = ssid;

    }
}
