package z.j.a.onetocuh.app;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import z.j.a.onetocuh.app.utils.Constants;

public class FloatWindowService extends Service {
    private static final String TAG = "PACKAGENAME";
    private Class cls;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 启动service的时候，onCreate方法只有第一次会调用，onStartCommand和onStart每次都被调用。
     * onStartCommand会告诉系统如何重启服务，如判断是否异常终止后重新启动，在何种情况下异常终止
     * 这个整形可以有四个返回值：start_sticky、start_no_sticky、START_REDELIVER_INTENT、START_STICKY_COMPATIBILITY。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            cls = (intent!=null && intent.getExtras()!=null) ? Class.forName(intent.getExtras().getString(Constants.className)) : null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(cls!=null){
            FloatWindowManager.removeFloatView();
            FloatWindowManager.setIntent(intent);
            FloatWindowManager.createFloatWindow(getApplicationContext(),cls);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 判断当前界面是否桌面
     */
    private boolean isHome(){
        ActivityManager mactivityManager =(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mactivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes(){
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo){
            names.add(ri.activityInfo.packageName);
            System.out.println("packageName" + names);
            Log.d(TAG,"tag:"+names);
        }
        return names;
    }
}
