package z.j.a.onetocuh.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;

import z.j.a.onetocuh.R;
import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowView;
import z.j.a.onetocuh.app.LocalUtil;

public class TwoView extends FloatWindowView {
    private WindowManager windowManager;//更新小悬浮的位置
    private RelativeLayout client_btn;

    private int statusBarHeight;//状态栏高度


    private float xInScreen;//记录当前手指位置在屏幕上的横坐标值
    private float yInScreen;//记录当前手指位置在屏幕上的纵坐标值

    private float xDownInScreen;//记录手指按下时在屏幕上的横坐标的值
    private float yDownInScreen;//记录手指按下时在屏幕上的纵坐标的值

    private float toleranceNumerical = 20;//误差值(手指松开后与按下前，位置相差值)

    private float xInView;//记录手指按下时在小悬浮窗的View上的横坐标的值
    private float yInView;//记录手指按下时在小悬浮窗的View上的纵坐标的值

    public TwoView(Context context) {
        super(context);
//        Toast.makeText(context, "创建第二个悬浮窗" + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
        Float h = context.getResources().getDimension(R.dimen.start_height) * 3;
        Float w = context.getResources().getDimension(R.dimen.start_width) * 3;
        setFloatWindowHeight(h.intValue());
        setFloatWindowWidth(w.intValue());

        Integer[] xy = LocalUtil.getLocal().get(OneView.class.getName());

        setFloatWindowX(xy[0] - (getFloatWindowWidth() / 2 - getFloatWindowWidth() / 3 / 2));
        setFloatWindowY(xy[1] - (getFloatWindowHeight() / 2 - getFloatWindowHeight() / 3 / 2));

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.view_two, this);
        client_btn = (RelativeLayout) findViewById(R.id.client_btn);
        client_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
//                Toast.makeText(context, "展开按钮组2" + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                FloatWindowManager.createFloatWindow(context, OneView.class);
            }
        });


    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        super.setOnHierarchyChangeListener(listener);
    }

    public boolean floatWinTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Toast.makeText(getContext(), "cccc"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                //手指按下时记录必要数据，纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                //手指移动的时候就更新悬浮窗位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                //如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen == yInScreen则视为触发
                if (Math.abs(xDownInScreen - xInScreen) < toleranceNumerical && Math.abs(yDownInScreen - yInScreen) < toleranceNumerical) {
                    Context context = getContext();
                    Toast.makeText(context, "展开按钮组2" + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                    FloatWindowManager.createFloatWindow(context, OneView.class);
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置
     */
    private void updateViewPosition() {
        //Toast.makeText(getContext(), "更新小悬浮窗在屏幕中的位置"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
        updateViewPosition(null, null);
    }

    /***
     * 更新小悬浮窗在屏幕中的位置
     *
     * @param x
     * @param y
     */
    private void updateViewPosition(Integer x, Integer y) {
        //Toast.makeText(getContext(), "更新小悬浮窗在屏幕中的位置"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
        mParams = mParams == null ? getmParams() : mParams;
        if (x == null && y == null) {
            mParams.x = (int) (xInScreen - xInView);
            mParams.y = (int) (yInScreen - yInView);
        } else {
            mParams.x = x;
            mParams.y = y;
        }
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 用于获取状态栏高度
     *
     * @return 返回状态栏高度的像素值
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
        //return statusBarHeight;
    }
}
