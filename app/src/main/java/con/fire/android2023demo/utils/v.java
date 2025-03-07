package con.fire.android2023demo.utils;


import android.util.Log;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class v {
    public static String a(String str) {
        if (i(str)) {
            return "";
        }
        if (!n(str)) {
            return str;
        }
        return str.substring(0, 3) + "****" + str.substring(str.length() - 3, str.length());
    }

    public static String b(String str) {
        String str2 = "";
        if (i(str)) {
            return "";
        }
        int i9 = 0;
        while (i9 < str.length() / 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            int i10 = i9 * 4;
            i9++;
            sb.append(str.substring(i10, i9 * 4));
            sb.append(" ");
            str2 = sb.toString();
        }
        if (str.length() % 4 != 0) {
            return str2 + str.substring((str.length() / 4) * 4, str.length());
        }
        return str2;
    }

    public static String c(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i9 = 0; i9 < length; i9++) {
            char charAt = str.charAt(i9);
            if (charAt != ' ' && ((charAt < 128 || charAt > 255) && ((charAt < '0' || charAt > '9') && ((charAt < 'A' || charAt > 'Z') && (charAt < 'a' || charAt > 'z'))))) {
                sb.append("*");
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

//    public static String d() {
//        String f9 = r.f("cs_email");
//        if (i(f9)) {
//            return "cashmexservicio@gmail.com";
//        }
//        return f9;
//    }

//    public static String e() {
//        String f9 = r.f("cs_phone");
//        if (i(f9)) {
//            return "5536486135";
//        }
//        return f9;
//    }

//    public static String f() {
//        String f9 = r.f("cs_whatsapp");
//        if (i(f9)) {
//            return "5630291161";
//        }
//        return f9;
//    }

    public static String g(String str) {
        Matcher matcher = Pattern.compile("\\d+").matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            stringBuffer.append(matcher.group());
        }
        return stringBuffer.toString();
    }

//    public static String h(String str) {
//        String a9 = App.b().a();
//        String str2 = "http://api-test.cashimax.com/h5/";
//        if (a9.contains("https://api.cashimax.com") || (!a9.contains("http://api-test.cashimax.com") && !App.b().f())) {
//            str2 = "https://api.cashimax.com/h5/";
//        }
//        return str2 + str;
//    }

    public static boolean i(String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }

    public static boolean j(TextView textView) {
        return textView == null || textView.getText() == null || textView.getText().length() == 0;
    }

    public static boolean k(String str) {
        if (i(str)) {
            return false;
        }
        return Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$").matcher(str).matches();
    }

    public static boolean l(String str) {
        return str.contains("<") && str.contains(">");
    }

    public static boolean m(String str) {
        Log.e("123", str);
        if (str.length() < 18 || !Pattern.compile("[a-zA-Z]{4}[0-9]{6}[a-zA-Z]{6}[a-zA-Z0-9]{2}").matcher(str).matches()) {
            return false;
        }
        int parseInt = Integer.parseInt(str.substring(6, 8));
        int parseInt2 = Integer.parseInt(str.substring(8, 10));
        if (parseInt <= 0 || parseInt > 12 || parseInt2 <= 0 || parseInt2 > 31) {
            return false;
        }
        char charAt = str.charAt(10);
        if (charAt != 'M' && charAt != 'H') {
            return false;
        }
        return true;
    }

    public static boolean n(String str) {
        if (i(str)) {
            return false;
        }
        return str.matches("[2-9][0-9]{9}");
    }

    public static boolean o(String str) {
        if (i(str)) {
            return false;
        }
        if (str.startsWith("52")) {
            if (str.length() == 10) {
                return str.matches("[2-9][0-9]{9}");
            }
            return str.matches("(52)[2-9][0-9]{9}");
        } else if (str.startsWith("0052")) {
            return str.matches("(0052)[2-9][0-9]{9}");
        } else {
            if (str.startsWith("052")) {
                return str.matches("(052)[2-9][0-9]{9}");
            }
            return str.matches("[2-9][0-9]{9}");
        }
    }

    public static boolean p(String str, String str2) {
        if (str.length() > 10) {
            str = str.substring(str.length() - 10);
        }
        if (str2.length() > 10) {
            str2 = str2.substring(str2.length() - 10);
        }
        if (str.equals(str2)) {
            return true;
        }
        return false;
    }

    public static String q(String str) {
        return i(str) ? "" : str;
    }

    public static String r(String str) {
        return i(str) ? "0" : str;
    }
}