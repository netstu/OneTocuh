package z.j.a.onetocuh;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;

public class SmallWindowActivity extends LinearLayout {
    public RelativeLayout floatWin;//悬浮窗口对象
    public RelativeLayout menu_group;//悬浮窗口对象
    public RelativeLayout client_btn;//悬浮窗口中间按钮

    public static int viewWidth;//小悬浮宽度
    public static int viewHeight;//小悬浮高度

    private int[] location;

    private int statusBarHeight;//状态栏高度

    private WindowManager windowManager;//更新小悬浮的位置
    private WindowManager.LayoutParams mParams;//小悬浮高度
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;//记录当前手指位置在屏幕上的横坐标值
    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;//记录当前手指位置在屏幕上的纵坐标值

    private float xDownInScreen;//记录手指按下时在屏幕上的横坐标的值
    private float yDownInScreen;//记录手指按下时在屏幕上的纵坐标的值

    private float toleranceNumerical = 20;//误差值(手指松开后与按下前，位置相差值)

    private float xInView;//记录手指按下时在小悬浮窗的View上的横坐标的值
    private float yInView;//记录手指按下时在小悬浮窗的View上的纵坐标的值

    private int screenWidth;  //手机屏幕宽度
    private int screenHeight; //手机屏幕高度

    /**
     *
     * @param context
     */
    public SmallWindowActivity(Context context) {
        super(context);
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.activity_small_window,this);
        floatWin = (RelativeLayout) findViewById(R.id.small_window_layout);
        menu_group = (RelativeLayout) findViewById(R.id.menu_group);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        //screenWidth = windowManager.getDefaultDisplay().getWidth();//屏幕宽度
        //screenHeight = windowManager.getDefaultDisplay().getHeight();//屏幕高度

        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        //获取手机屏幕宽高(当前悬浮窗口的宽高)
        viewWidth = floatWin.getLayoutParams().width;
        viewHeight = floatWin.getLayoutParams().height;
        client_btn = (RelativeLayout) findViewById(R.id.client_btn);

        client_btn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return floatWinTouchEvent(event);
            }
        });

    }

    /**
     * 手指触摸主按钮的处理
     */
    public boolean floatWinTouchEvent(MotionEvent event) {
        switch (event.getAction()){
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
                yInScreen = event.getRawY()-getStatusBarHeight();
                //手指移动的时候就更新悬浮窗位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                //如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen == yInScreen则视为触发
                if (Math.abs(xDownInScreen - xInScreen) < toleranceNumerical && Math.abs(yDownInScreen - yInScreen) < toleranceNumerical) {
                    Context context = getContext();
                    goBack(context);
                    Toast.makeText(context, "展开按钮组"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return true;
    }

    //将悬浮窗的参数传入，用于更新小悬浮窗的位置
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    //更新小悬浮窗在屏幕中的位置
    private void updateViewPosition() {
        //Toast.makeText(getContext(), "更新小悬浮窗在屏幕中的位置"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
        updateViewPosition(null,null);
    }

    //更新小悬浮窗在屏幕中的位置
    private void updateViewPosition(Integer x,Integer y) {
        //Toast.makeText(getContext(), "更新小悬浮窗在屏幕中的位置"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
        if (x == null && y == null) {
            mParams.x = (int) (xInScreen - xInView);
            mParams.y = (int) (yInScreen - yInView);
        }else{
            mParams.x = x;
            mParams.y = y;
        }
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 用于获取状态栏高度
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
        return statusBarHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        findViewById(R.id.menu_group).setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View btnView = null;
        btnView = findViewById(R.id.client_btn);
        btnView.setVisibility(GONE);
        btnView = findViewById(R.id.left_btn);
        btnView.setVisibility(GONE);
        btnView = findViewById(R.id.top_btn);
        btnView.setVisibility(GONE);
        btnView = findViewById(R.id.right_btn);
        btnView.setVisibility(GONE);
        btnView = findViewById(R.id.bottom_btn);
        btnView.setVisibility(GONE);
        btnView = findViewById(R.id.client_btn);
        btnView.setVisibility(VISIBLE);
        updateViewPosition(location[0], location[1]-75);
        return super.onTouchEvent(event);
    }

    public void goBack(Context context) {

        RelativeLayout.LayoutParams params = null;
        View btnView = null;
        btnView = findViewById(R.id.client_btn);
        btnView.setVisibility(GONE);

        location = new  int[2];
        floatWin.getLocationOnScreen(location);

        btnView = findViewById(R.id.menu_group);
        btnView.setVisibility(VISIBLE);
        updateViewPosition(location[0] - btnView.getWidth(), location[1] - btnView.getHeight()*3/2);

        btnView = findViewById(R.id.client_btn);
        btnView.setVisibility(VISIBLE);
        btnView = findViewById(R.id.left_btn);
        btnView.setVisibility(VISIBLE);
        btnView = findViewById(R.id.top_btn);
        btnView.setVisibility(VISIBLE);
        btnView = findViewById(R.id.right_btn);
        btnView.setVisibility(VISIBLE);
        btnView = findViewById(R.id.bottom_btn);
        btnView.setVisibility(VISIBLE);


    }

}
