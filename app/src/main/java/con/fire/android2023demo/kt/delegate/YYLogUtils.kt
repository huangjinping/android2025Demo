package con.fire.android2023demo.kt.delegate

import android.util.Log

class YYLogUtils {
    companion object {
        fun w(s: String) {

            Log.d("YYLogUtils", "-$s");
        }
    }
}