package con.fire.android2023demo.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Locale;

public class CommonUtil {

    public static String formatMoney(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }
        double v = Double.parseDouble(s);
        if (v == 0) {
            return "0.00";
        }
        Locale.setDefault(Locale.US);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return replace(formatter.format(v));
    }

    public static String formatMoney2(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }
        double v = Double.parseDouble(s);
        if (v == 0) {
            return "0.00";
        }
        Locale.setDefault(Locale.US);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String result = replace(formatter.format(v));
        return result.substring(0, result.length() - 3);
    }

    private static String replace(String result) {
        char c = result.charAt(result.length() - 3);
        if (c != ',') {
            return result;
        }
        String substring = result.substring(0, result.length() - 3);
        String substring1 = result.substring(result.length() - 3, result.length());

        return substring.replace(".", ",") + substring1.replace(",", ".");
    }


}
