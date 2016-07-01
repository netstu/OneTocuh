package z.j.a.onetocuh;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import z.j.a.onetocuh.app.FloatWindowView;

public class MyWindowManager{
    //View实例
    private static FloatWindowView activity;

    //小悬浮View的参数
    private static WindowManager.LayoutParams floatWindowParams;

    //小悬浮窗View的实例
    private static SmallWindowActivity smallWindowActivity;

    //小悬浮View的参数
    private static WindowManager.LayoutParams smallWindowParams;

    //用于控制在屏幕上添加或移除悬浮窗
    private static WindowManager mWindowManager;

    public static void createFloatWindow(Context context,Class cls){
        //WindowManager基本用到:addView，removeView，updateViewLayout
        WindowManager windowManager = getWindowManager(context);
        if(activity!=null){
            windowManager.removeView(activity);
        }
        //获取屏幕宽高 abstract Display  getDefaultDisplay()；  //获取默认显示的 Display 对象
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        try {
            Constructor[] con = cls.getConstructors();
            activity = (FloatWindowView)con[0].newInstance(context);
            //activity = (View) cls.getConstructor().newInstance(context);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //设置小悬浮窗口的位置以及相关参数
        if (activity != null) {
            if (floatWindowParams == null) {
                floatWindowParams = new WindowManager.LayoutParams();//
                floatWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;//设置窗口的window type
                floatWindowParams.format = PixelFormat.RGBA_8888;//设置图片格式，效果为背景透明
                floatWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
                floatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;//调整悬浮窗口位置在左边中间
                floatWindowParams.width = SmallWindowActivity.viewWidth;//设置悬浮窗口的宽高
                floatWindowParams.height = SmallWindowActivity.viewHeight;
                floatWindowParams.x = screenWidth;//设置悬浮窗口位置
                floatWindowParams.y = screenHeight / 2;
            }
            activity.setmParams(floatWindowParams);
            windowManager.addView(activity, floatWindowParams);//将需要加到悬浮窗口中的View加入到窗口中
        }
    }

    public static void createSmallWindow(Context context){
        //WindowManager基本用到:addView，removeView，updateViewLayout
        WindowManager windowManager = getWindowManager(context);
        //获取屏幕宽高 abstract Display  getDefaultDisplay()；  //获取默认显示的 Display 对象
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        //设置小悬浮窗口的位置以及相关参数
        if (smallWindowActivity == null) {
            smallWindowActivity = new SmallWindowActivity(context);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();//
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;//设置窗口的window type
                smallWindowParams.format = PixelFormat.RGBA_8888;//设置图片格式，效果为背景透明
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;//调整悬浮窗口位置在左边中间
                smallWindowParams.width = SmallWindowActivity.viewWidth;//设置悬浮窗口的宽高
                smallWindowParams.height = SmallWindowActivity.viewHeight;
                smallWindowParams.x = screenWidth;//设置悬浮窗口位置
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindowActivity.setParams(smallWindowParams);
            windowManager.addView(smallWindowActivity, smallWindowParams);//将需要加到悬浮窗口中的View加入到窗口中
        }
    }


    /**
     * 将小悬浮窗从屏幕上移除。
     * abstract void removeViewImmediate(View view)；//是removeView(View) 的一个特殊扩展，
     * 在方法返回前能够立即调用该视图层次的View.onDetachedFromWindow() 方法。
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindowActivity != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindowActivity);//移除悬浮窗口
            smallWindowActivity = null;
        }
    }

    public static void removeFloatWindow(Context context) {
        if (smallWindowActivity != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(activity);//移除悬浮窗口
            smallWindowActivity = null;
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return activity != null;
        //return smallWindowActivity != null;
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

}
