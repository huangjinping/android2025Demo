package con.fire.android2023demo.bean;



/* loaded from: classes.dex */
public class AppInfo {
    private String appName;
    private long installTime;
    private long lastUpdateTime;
    private String packageName;
    private String selfAppVersion;
    private int userId;
    private int versionCode;
    private String versionName;

    public AppInfo() {
    }

    public String getAppName() {
        return this.appName;
    }

    public long getInstallTime() {
        return this.installTime;
    }

    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSelfAppVersion() {
        return this.selfAppVersion;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public void setInstallTime(long j9) {
        this.installTime = j9;
    }

    public void setLastUpdateTime(long j9) {
        this.lastUpdateTime = j9;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setSelfAppVersion(String str) {
        this.selfAppVersion = str;
    }

    public void setUserId(int i9) {
        this.userId = i9;
    }

    public void setVersionCode(int i9) {
        this.versionCode = i9;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public AppInfo(String str, String str2, int i9, String str3, int i10) {
        this.appName = str3;
        this.packageName = str;
        this.versionCode = i9;
        this.versionName = str2;
        this.userId = i10;
    }
}