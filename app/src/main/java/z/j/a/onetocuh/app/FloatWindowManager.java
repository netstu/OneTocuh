package z.j.a.onetocuh.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import z.j.a.onetocuh.app.utils.Constants;

public class FloatWindowManager {
    private static Intent intent;
    //View实例
    private static FloatWindowView activity;
    public static List<View> activityList;

    //小悬浮View的参数
    private static WindowManager.LayoutParams floatWindowParams;

    //用于控制在屏幕上添加或移除悬浮窗
    private static WindowManager mWindowManager;

    public static void createFloatWindow(Context context,Class cls){
        //WindowManager基本用到:addView，removeView，updateViewLayout
        mWindowManager = getWindowManager(context);
        try {
            Constructor[] con = cls.getConstructors();
            activity = (FloatWindowView)con[0].newInstance(context);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //设置小悬浮窗口的位置以及相关参数
        if (activity != null) {
            activity.setIntent(getIntent());
            floatWindowParams = new WindowManager.LayoutParams();//
            floatWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;//设置窗口的window type
            if(intent!=null && intent.getExtras()!=null && "NO".equals(intent.getExtras().getString(Constants.transparent))){
                //floatWindowParams.dimAmount = 0.9f;
                //如果明确指定窗口不需要透明
            }else{
                floatWindowParams.format = PixelFormat.RGBA_8888;//设置图片格式，效果为背景透明
            }
            //下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
            floatWindowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
            floatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;//调整悬浮窗口位置在左边中间
            floatWindowParams.width = activity.getFloatWindowWidth();
            floatWindowParams.height = activity.getFloatWindowHeight();
            floatWindowParams.x = activity.getFloatWindowX();//设置悬浮窗口位置
            floatWindowParams.y = activity.getFloatWindowY();
            activity.setmParams(floatWindowParams);
            activityList = activityList==null ? new ArrayList<View>() : activityList;
            activityList.add(activity);
            mWindowManager.addView(activity, floatWindowParams);//将需要加到悬浮窗口中的View加入到窗口中
            intent = null;
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return activity != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     *            必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public static void removeFloatView(){
        if(activityList!=null){
            while (activityList.size()>0){
                mWindowManager.removeView(activityList.get(0));
                activityList.remove(activityList.get(0));
            }
        }
    }

    public static Intent getIntent() {
        return intent;
    }

    public static void setIntent(Intent intent) {
        FloatWindowManager.intent = intent;
    }

}
