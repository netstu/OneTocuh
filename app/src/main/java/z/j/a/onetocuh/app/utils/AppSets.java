package z.j.a.onetocuh.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lumia on 2016-07-05.
 */
public class AppSets {
    private static AppSets appSets;
    private static SharedPreferences spf;
    private static SharedPreferences.Editor spfe;

    public static AppSets getInstance(Context context){
        if(appSets==null){
            synchronized (AppSets.class) {
                if (appSets == null) {
                    appSets = new AppSets(context);
                }
            }
        }
        return appSets;
    }

    private AppSets(Context context) {
        spf = context.getSharedPreferences("myConfig", Context.MODE_PRIVATE);
        spfe = spf.edit();
    }

    public static void setAppToFloatWindow(String key) {
        Map<String,String> appList = new HashMap<>();
        appList.put("left","com.tencent.mobileqq");
        appList.put("right","com.UCMobile");
        JSONObject jsb = new JSONObject(appList);
        spfe.putString(key, jsb.toString());
        spfe.commit();
    }

    public static void setAppToFloatWindow(String btnKey,String packageName) {
        spfe.putString(btnKey, packageName);
        spfe.commit();
    }

    public static String getAppToFloatWindow(String key) {
        if(spf==null){
            return null;
        }
        String appList = spf.getString(key,"");
        return appList;
    }

}
