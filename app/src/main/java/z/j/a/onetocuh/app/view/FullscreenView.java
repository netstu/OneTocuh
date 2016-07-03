package z.j.a.onetocuh.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import z.j.a.onetocuh.R;
import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowView;

public class FullscreenView extends FloatWindowView {
    private RelativeLayout fullscreen_view;

    public FullscreenView(Context context) {
        super(context);
        Float w = getScreenWidth().floatValue();//当前视图的宽
        setFloatWindowWidth(w.intValue());//设置当前悬浮窗口的宽
        Float h = getScreenHeight().floatValue();//当前视图的高
        setFloatWindowHeight(h.intValue());//设置当前悬浮窗口的高

        setFloatWindowX(0);//设置当前窗口在屏幕中的X坐标
        setFloatWindowY(0);//设置当前窗口在屏幕中的Y坐标

        LayoutInflater.from(context).inflate(R.layout.view_fullscreen, this);
        fullscreen_view = (RelativeLayout) findViewById(R.id.fullscreen_view);
        fullscreen_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowManager.removeFloatView();
                FloatWindowManager.createFloatWindow(getContext(), OneView.class);
            }
        });
    }
}
