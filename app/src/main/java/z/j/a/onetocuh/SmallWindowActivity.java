package z.j.a.onetocuh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;

public class SmallWindowActivity extends LinearLayout {
    public RelativeLayout floatWin;//悬浮窗口对象
    public RelativeLayout start_btn; //悬浮窗口中开始按钮对象
    public RelativeLayout menu_group; //悬浮窗口中按钮组对象

    public static int viewWidth;//小悬浮宽度
    public static int viewHeight;//小悬浮高度

    private int statusBarHeight;//状态栏高度

    private WindowManager windowManager;//更新小悬浮的位置
    private WindowManager.LayoutParams mParams;//小悬浮高度

    private float xInScreen;//记录当前手指位置在屏幕上的横坐标值
    private float yInScreen;//记录当前手指位置在屏幕上的纵坐标值

    private float xDownInScreen;//记录手指按下时在屏幕上的横坐标的值
    private float yDownInScreen;//记录手指按下时在屏幕上的纵坐标的值

    private float toleranceNumerical = 20;//误差值(手指松开后与按下前，位置相差值)

    private float xInView;//记录手指按下时在小悬浮窗的View上的横坐标的值
    private float yInView;//记录手指按下时在小悬浮窗的View上的纵坐标的值

    private int screenWidth;  //手机屏幕宽度
    private int screenHeight; //手机屏幕高度

    private int clientX; //初始按钮的中心位置
    private int clientY; //初始按钮的中心位置

    /**
     *
     * @param context
     */
    public SmallWindowActivity(Context context) {
        super(context);
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();//屏幕宽度
        screenHeight = windowManager.getDefaultDisplay().getHeight();//屏幕高度
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.activity_small_window,this);
        floatWin = (RelativeLayout) findViewById(R.id.small_window_layout);
        start_btn = (RelativeLayout) findViewById(R.id.start_btn);
        menu_group = (RelativeLayout) findViewById(R.id.menu_group);

        //获取手机屏幕宽高
        viewWidth = floatWin.getLayoutParams().width;
        viewHeight = floatWin.getLayoutParams().height;

        start_btn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return floatWinTouchEvent(event);
            }
        });

        Button back = (Button) findViewById(R.id.fanhui);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView();
            }
        });
    }

    public void getStartLoaclhost(MotionEvent event) {
        float startX = event.getRawX() - event.getX() - start_btn.getWidth() / 2;
        float startY = event.getRawY() - event.getY() - start_btn.getHeight() / 2;
        int h = menu_group.getHeight();
        h = h==0 ? menu_group.getLayoutParams().height : h;
        int w = menu_group.getWidth();
        w = w==0 ? menu_group.getLayoutParams().width : w;
        if (screenWidth < (startX + w / 2)) {
            startX = screenWidth - w / 2 - 1;
        }
        if (screenHeight < (startY + h / 2)) {
            startY = screenHeight - h / 2 - 1;
        }
        if (0 > (startX - w / 2)) {
            startX = w / 2 + 1;
        }
        if (0 > (startY - h / 2)) {
            startY = h / 2 + 1;
        }
        clientX = Float.valueOf(startX).intValue();
        clientY = Float.valueOf(startY).intValue();
    }

    /**
     * 改变 开始按钮 与 菜单组 的显示隐藏
     */
    public void changeView(){
        if(start_btn.getVisibility() == VISIBLE){
            setMenuGroupClient();
            floatWin.setLayoutParams(new LayoutParams(screenWidth,screenHeight));
            start_btn.setVisibility(GONE);
            menu_group.setVisibility(VISIBLE);
        }else{
            floatWin.setLayoutParams(new LayoutParams(start_btn.getWidth(),start_btn.getHeight()));
            start_btn.setVisibility(VISIBLE);
            menu_group.setVisibility(GONE);
        }
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
                    getStartLoaclhost(event);
                    changeView();
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
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
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
        floatWin.setLayoutParams(new LayoutParams(mParams.width,mParams.height));
        menu_group.setVisibility(GONE);
        start_btn.setVisibility(VISIBLE);
        return super.onTouchEvent(event);
    }

    /**
     * 设置视图的位置
     * @param view 视图
     * @param left 左
     * @param top 上
     * @param right 右
     * @param bottom 下
     */
    public void setLayout(View view,int left, int top, int right, int bottom) {
        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(left, top,right,bottom);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置展开菜单的中心的位置
     */
    public void setMenuGroupClient() {
        int h = menu_group.getLayoutParams().height;
        int w = menu_group.getLayoutParams().width;
        clientX = clientX - Float.valueOf(w/2).intValue();
        clientY = clientY - Float.valueOf(h/2).intValue();
        setLayout(menu_group,clientX,clientY,0,0);
    }
}
