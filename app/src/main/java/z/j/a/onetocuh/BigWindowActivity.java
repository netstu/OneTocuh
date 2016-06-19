package z.j.a.onetocuh;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BigWindowActivity extends LinearLayout {

    //记录大悬浮窗的宽度
    public static int viewWidth;

    //记录大悬浮窗高度
    public static int viewHeight;

    private Button leftButton;
    int screenWidth, screenHeight;
    int lastX, lastY;//记录移动的最后的位置
    int dx, dy;

    public BigWindowActivity(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.activity_big_window, this);
        View bigView = findViewById(R.id.big_window_layout);
        viewWidth = bigView.getLayoutParams().width;
        viewHeight = bigView.getLayoutParams().height;
        RelativeLayout menu_group = (RelativeLayout) findViewById(R.id.menu_group);
        Toast.makeText(context, menu_group.getLayoutParams().width +"<W,H>" + menu_group.getLayoutParams().height, Toast.LENGTH_SHORT).show();
        initUI();
    }

    private void initUI() {
        //获取屏幕的分辨率
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        Button back = (Button) findViewById(R.id.fanhui);
        back.setOnTouchListener(new OnTouchListener() {//添加返回按钮的触摸监听
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                comeback(getContext());
                return false;
            }
        });

        leftButton = (Button) findViewById(R.id.leftBtn);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击左边", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 点击返回移除大窗口创建小窗口
     *
     * @param context
     */
    public void comeback(Context context) {
        // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
        MyWindowManager.removeBigWindow(context);
        MyWindowManager.createSmallWindow(context);
    }

    //发短信
    public void sms() {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.putExtra("sms_body", "The SMS text");
        it.setType("vnd.android-dir/mms-sms");
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(it);
        comeback(getContext());
    }

    /**
     * 移动控件设置位置
     *
     * @param v
     * @param event
     */
    public void mobilesetting(View v, MotionEvent event) {
        //移动中动态设置位置
        dx = (int) event.getRawX() - lastX;//移动中x当前位置
        dy = (int) event.getRawY() - lastY;

        int left = v.getLeft() + dx;
        int top = v.getTop() + dy;
        int right = v.getRight() + dx;
        int bottom = v.getBottom() + dy;

        if (left < 0) {
            left = 0;
            right = left + v.getWidth();//0
        }
        if (right > screenWidth) {
            right = screenWidth;
            left = right - v.getWidth();//max
        }
        if (top < 0) {
            top = 0;
            bottom = top + v.getHeight();
        }
        if (bottom > screenHeight) {
            bottom = screenHeight;
            top = bottom - v.getHeight();
        }
        v.layout(left, top, right, bottom);
        //将当前的位置再次设置
        lastX = (int) event.getRawX();
        lastY = (int) event.getRawY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //点击屏幕弹出的框会消失
        MyWindowManager.removeBigWindow(getContext());
        MyWindowManager.createSmallWindow(getContext());
        return super.onTouchEvent(event);
    }
}