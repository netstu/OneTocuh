package z.j.a.onetocuh.app;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lumia on 2016-07-01.
 */
public class LocalUtil {
    /**
     * 保存视图的位置信息，key为视图的名称，value为视图XY位置值
     */
    private static Map<String,Integer[]> local;
    /***
     * 保存视图的位置信息，key为视图的名称，value为视图XY位置值
     */
    private static Map<String,Integer[]> viewSize;

    public static Map<String, Integer[]> getViewSize() {
        return viewSize;
    }

    public static void setViewSize(Map<String, Integer[]> viewSize) {
        if(LocalUtil.viewSize==null && viewSize!=null){
            LocalUtil.viewSize = viewSize;
        }else{
            LocalUtil.viewSize.putAll(viewSize);
        }
    }
    public static void setViewSize(String className, Integer[] wh) {
        Map<String, Integer[]> viewSize = new HashMap<>();
        viewSize.put(className,wh);
        setViewSize(viewSize);
    }

    public static Map<String, Integer[]> getLocal() {
        return local;
    }

    public static void setLocal(Map<String, Integer[]> local) {
        if(LocalUtil.local==null){
            LocalUtil.local = local;
        }else{
            local.putAll(local);
        }

    }
    public static void setLocal(String key,Integer[] xy) {
        if(local==null){
            LocalUtil.local = new HashMap<>();
        }
        local.put(key,xy);
    }

    private static Map<String,Activity> activityList;

    /**
     * 添加活动的Activity
     * @param activity
     */
    public static void addActivity(Activity activity){
        if(activityList==null){
            activityList = new HashMap<>();
        }
        activityList.put(activity.getClass().getName(),activity);
    }

    /**
     * 结束活动的Activity
     * @param activityName
     */
    public static void finishActivity(String activityName){
        if(activityList!=null && activityList.containsKey(activityName)){
            activityList.get(activityName).finish();
            activityList.remove(activityName);
        }
    }


}
