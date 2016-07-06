package z.j.a.onetocuh.app.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by Lumia on 2016-07-04.
 */
public class AppInfo {
    private Integer versionCode=0;
    private String appname="";
    private String packagename="";
    private String versionName="";
    private Drawable appicon=null;
    private Boolean sysapp=false;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public Boolean getSysapp() {
        return sysapp;
    }

    public void setSysapp(Boolean sysapp) {
        this.sysapp = sysapp;
    }
}
