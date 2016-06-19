package z.j.a.onetocuh;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyWindowManager{
    //小悬浮窗View的实例
    private static SmallWindowActivity smallWindowActivity;

    //大悬浮窗View的实例
    private static BigWindowActivity bigWindowActivity;

    //小悬浮View的参数
    private static WindowManager.LayoutParams smallWindowParams;

    //大悬浮View的参数
    private  static WindowManager.LayoutParams bigWindowParams;

    //用于控制在屏幕上添加或移除悬浮窗
    private static WindowManager mWindowManager;

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
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
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
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context
     *            必须为应用程序的Context.
     */
//    @SuppressWarnings("deprecation")
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (bigWindowActivity == null) {
            bigWindowActivity = new BigWindowActivity(context);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                bigWindowParams.x = screenWidth / 3 - BigWindowActivity.viewWidth / 3;
                bigWindowParams.y = screenHeight / 3 - BigWindowActivity.viewHeight / 3;
                bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = BigWindowActivity.viewWidth;
                bigWindowParams.height = BigWindowActivity.viewHeight;
            }
            bigWindowParams.x = smallWindowParams.x;
            bigWindowParams.y = smallWindowParams.y;
            RelativeLayout menu_group = (RelativeLayout) bigWindowActivity.findViewById(R.id.menu_group);

            menu_group.setX(smallWindowParams.x);
            menu_group.setY(smallWindowParams.y);
            windowManager.addView(bigWindowActivity, bigWindowParams);
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

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (bigWindowActivity != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigWindowActivity);
            bigWindowActivity = null;
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindowActivity != null || bigWindowActivity != null;
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
