package con.fire.android2023demo.utils.lin;


import android.text.TextWatcher;
import android.util.Base64;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public abstract class pzetpkg {

    /* renamed from: mwowwskti  reason: collision with root package name */
    public static final SecretKeySpec f12435mwowwskti = new SecretKeySpec(Base64.decode("RllPVGo3eGJ5VUR1Y1FPRg==", 2), "AES");

    /* renamed from: emazevgh  reason: collision with root package name */
    public static final IvParameterSpec f12434emazevgh = new IvParameterSpec(Base64.decode("d0E4Z2J4Q3FtbXJIVDl2Sg==", 2));

    /* renamed from: zwfgajm  reason: collision with root package name */
    public static String f12436zwfgajm = etol("JBa/OjExVggvDE2BnlnqfQ==");

    public static String emazevgh(BigDecimal bigDecimal) {
        return f12436zwfgajm + bigDecimal.stripTrailingZeros().toPlainString();
    }

    public static String etol(String str) {
        if (str.isEmpty()) {
            return str;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, f12435mwowwskti, f12434emazevgh);
            byte[] doFinal = cipher.doFinal(Base64.decode(str, 2));
            if (doFinal != null && doFinal.length != 0) {
                return new String(doFinal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String mwowwskti(long j) {
        if (j > 9) {
            return j + HttpUrl.FRAGMENT_ENCODE_SET;
        } else if (j > 0) {
            return "0" + j;
        } else {
            return "00";
        }
    }

    public static String nmqbnjf(String str) {
        long convert = 0;
        if (str != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                convert = TimeUnit.DAYS.convert(simpleDateFormat.parse(str).getTime() - simpleDateFormat.parse(simpleDateFormat.format(new Date())).getTime(), TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return String.valueOf(Math.max(convert, 0L));
        }
        convert = 0;
        return String.valueOf(Math.max(convert, 0L));
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0071 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String slyvsmo(android.content.Context r3, java.lang.String r4) {
        /*
            r0 = 0
            android.content.res.Resources r3 = r3.getResources()     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L50
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L50
            java.io.InputStream r3 = r3.open(r4)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L50
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L50
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L4b java.io.IOException -> L50
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L46
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L46
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L6e
            r0.<init>()     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L6e
        L1c:
            java.lang.String r1 = r3.readLine()     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L6e
            if (r1 == 0) goto L26
            r0.append(r1)     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L6e
            goto L1c
        L26:
            java.lang.String r0 = r0.toString()     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L6e
            java.lang.String r0 = etol(r0)     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L6e
            r4.close()     // Catch: java.io.IOException -> L32
            goto L36
        L32:
            r4 = move-exception
            r4.printStackTrace()
        L36:
            r3.close()     // Catch: java.io.IOException -> L3a
            goto L3e
        L3a:
            r3 = move-exception
            r3.printStackTrace()
        L3e:
            return r0
        L3f:
            r0 = move-exception
            goto L54
        L41:
            r3 = move-exception
            r2 = r0
            r0 = r3
            r3 = r2
            goto L6f
        L46:
            r3 = move-exception
            r2 = r0
            r0 = r3
            r3 = r2
            goto L54
        L4b:
            r3 = move-exception
            r4 = r0
            r0 = r3
            r3 = r4
            goto L6f
        L50:
            r3 = move-exception
            r4 = r0
            r0 = r3
            r3 = r4
        L54:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L6e
            if (r4 == 0) goto L61
            r4.close()     // Catch: java.io.IOException -> L5d
            goto L61
        L5d:
            r4 = move-exception
            r4.printStackTrace()
        L61:
            if (r3 == 0) goto L6b
            r3.close()     // Catch: java.io.IOException -> L67
            goto L6b
        L67:
            r3 = move-exception
            r3.printStackTrace()
        L6b:
            java.lang.String r3 = ""
            return r3
        L6e:
            r0 = move-exception
        L6f:
            if (r4 == 0) goto L79
            r4.close()     // Catch: java.io.IOException -> L75
            goto L79
        L75:
            r4 = move-exception
            r4.printStackTrace()
        L79:
            if (r3 == 0) goto L83
            r3.close()     // Catch: java.io.IOException -> L7f
            goto L83
        L7f:
            r3 = move-exception
            r3.printStackTrace()
        L83:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: frtdzyup.pzetpkg.slyvsmo(android.content.Context, java.lang.String):java.lang.String");
    }

    public static String zppskyagv(String str) {
        if (str.isEmpty()) {
            return str;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, f12435mwowwskti, f12434emazevgh);
            byte[] encode = Base64.encode(cipher.doFinal(str.getBytes()), 2);
            if (encode != null && encode.length != 0) {
                return new String(encode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String zwfgajm(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return f12436zwfgajm + bigDecimal.stripTrailingZeros().toPlainString() + etol("R9etR4sI9vxhz6RNG9a/HA==") + bigDecimal2.stripTrailingZeros().toPlainString();
    }

    public static byte[] zybqm(byte[] bArr) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(2, f12435mwowwskti, f12434emazevgh);
                    byte[] doFinal = cipher.doFinal(bArr);
                    if (doFinal != null) {
                        if (doFinal.length != 0) {
                            return doFinal;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return bArr;
    }

    /* loaded from: classes.dex */
    public static abstract class mwowwskti implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }
}