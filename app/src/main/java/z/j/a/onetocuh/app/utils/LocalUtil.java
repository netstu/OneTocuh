package z.j.a.onetocuh.app.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lumia on 2016-07-01.
 */
public class LocalUtil {
    private static ArrayList<AppInfo> appList;
    private static Map<String,AppInfo> appMaps;
    private static Map<Integer,String> appID;
    private static Map<Integer,AppInfo> btnLinkApp;

    /**
     * 保存视图的位置信息，key为视图的名称，value为视图XY位置值
     */
    private static Map<String, Integer[]> local;
    /***
     * 保存视图的位置信息，key为视图的名称，value为视图XY位置值
     */
    private static Map<String, Integer[]> viewSize;

    public static Map<String, Integer[]> getViewSize() {
        return viewSize;
    }

    public static void setViewSize(Map<String, Integer[]> viewSize) {
        if (LocalUtil.viewSize == null && viewSize != null) {
            LocalUtil.viewSize = viewSize;
        } else {
            LocalUtil.viewSize.putAll(viewSize);
        }
    }

    public static void setViewSize(String className, Integer[] wh) {
        Map<String, Integer[]> viewSize = new HashMap<>();
        viewSize.put(className, wh);
        setViewSize(viewSize);
    }

    public static Map<String, Integer[]> getLocal() {
        return local;
    }

    public static void setLocal(Map<String, Integer[]> local) {
        if (LocalUtil.local == null) {
            LocalUtil.local = local;
        } else {
            local.putAll(local);
        }

    }

    public static void setLocal(String key, Integer[] xy) {
        if (local == null) {
            LocalUtil.local = new HashMap<>();
        }
        local.put(key, xy);
    }

    private static Map<String, Activity> activityList;

    /**
     * 添加活动的Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new HashMap<>();
        }
        activityList.put(activity.getClass().getName(), activity);
    }

    /**
     * 结束活动的Activity
     *
     * @param activityName
     */
    public static void finishActivity(String activityName) {
        if (activityList != null && activityList.containsKey(activityName)) {
            activityList.get(activityName).finish();
            activityList.remove(activityName);
        }
    }

    public static ArrayList<AppInfo> getAppList() {
        return appList;
    }

    public static void setAppList(ArrayList<AppInfo> appList) {
        LocalUtil.appList = appList;
    }

    public static Map<Integer, String> getAppID() {
        return appID;
    }

    public static void setAppID(Map<Integer, String> appID) {
        LocalUtil.appID = appID;
    }

    public static void puttAppID(Integer appId, String appName) {
        if(LocalUtil.appID==null){
            LocalUtil.appID = new HashMap();
        }
        LocalUtil.appID.put(appId,appName);
    }

    public static Map<Integer, AppInfo> getBtnLinkApp() {
        return btnLinkApp;
    }

    public static void setBtnLinkApp(Map<Integer, AppInfo> btnLinkApp) {
        LocalUtil.btnLinkApp = btnLinkApp;
    }

    public static void setBtnLinkApp(Integer id, AppInfo appInfo) {
        if(LocalUtil.btnLinkApp == null){
            LocalUtil.btnLinkApp = new HashMap<>();
        }
        LocalUtil.btnLinkApp.put(id,appInfo);
    }

    public static Map<String, AppInfo> getAppMaps() {
        return appMaps;
    }

    public static AppInfo getAppMap(String packageName) {
        if(appMaps!=null){
            return appMaps.get(packageName);
        }
        return null;
    }

    public static void setAppMaps(Map<String, AppInfo> appMaps) {
        LocalUtil.appMaps = appMaps;
    }

    public static void setAppMap(String packageName, AppInfo appInfo) {
        if(LocalUtil.appMaps == null){
            LocalUtil.appMaps = new HashMap<>();
        }
        LocalUtil.appMaps.put(packageName,appInfo);
    }
}
