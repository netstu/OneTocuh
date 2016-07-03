package z.j.a.onetocuh.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import z.j.a.onetocuh.R;
import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowView;
import z.j.a.onetocuh.app.LocalUtil;

public class TwoView extends FloatWindowView {
    private RelativeLayout client_btn;

    public TwoView(Context context) {
        super(context);
        Float w = context.getResources().getDimension(R.dimen.start_width) * 3;//当前视图的宽
        setFloatWindowWidth(w.intValue());//设置当前悬浮窗口的宽
        Float h = context.getResources().getDimension(R.dimen.start_height) * 3;//当前视图的高
        setFloatWindowHeight(h.intValue());//设置当前悬浮窗口的高

        Integer[] startXY = LocalUtil.getLocal().get(OneView.class.getName());//获取初始按钮的坐标

        Integer[] strartWH = LocalUtil.getViewSize().get(OneView.class.getName());//初始按钮的宽高

        setFloatWindowX(startXY[0] - (getFloatWindowWidth() / 2 - strartWH[0] / 2));//设置当前窗口在屏幕中的X坐标
        setFloatWindowY(startXY[1] - (getFloatWindowHeight() / 2 - strartWH[1] / 2));//设置当前窗口在屏幕中的Y坐标

        LayoutInflater.from(context).inflate(R.layout.view_two, this);
        client_btn = (RelativeLayout) findViewById(R.id.client_btn);
        client_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowManager.removeFloatView();
                FloatWindowManager.createFloatWindow(getContext(), OneView.class);
            }
        });
    }
}
