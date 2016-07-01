package z.j.a.onetocuh.app;

import android.content.Context;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FloatWindowView extends LinearLayout {
    protected WindowManager windowManager;//悬浮窗口
    protected WindowManager.LayoutParams mParams;//悬浮窗口参数
    protected int floatWindowWidth;//悬浮窗口的宽度
    protected int floatWindowHeight;//悬浮窗口的高度
    protected int floatWindowX;//悬浮窗口的水平中心位置
    protected int floatWindowY;//悬浮窗口的垂直中心位置
    protected int screenWidth;//屏幕宽度
    protected int screenHeight;//屏幕高度

    public FloatWindowView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context){
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public int getFloatWindowWidth() {
        return floatWindowWidth;
    }

    public void setFloatWindowWidth(int floatWindowWidth) {
        this.floatWindowWidth = floatWindowWidth;
    }

    public int getFloatWindowHeight() {
        return floatWindowHeight;
    }

    public void setFloatWindowHeight(int floatWindowHeight) {
        this.floatWindowHeight = floatWindowHeight;
    }

    public WindowManager.LayoutParams getmParams() {
        return mParams;
    }

    public void setmParams(WindowManager.LayoutParams mParams) {
        this.mParams = mParams;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFloatWindowX() {
        return floatWindowX;
    }

    public void setFloatWindowX(int floatWindowX) {
        this.floatWindowX = floatWindowX;
    }

    public int getFloatWindowY() {
        return floatWindowY;
    }

    public void setFloatWindowY(int floatWindowY) {
        this.floatWindowY = floatWindowY;
    }
}
