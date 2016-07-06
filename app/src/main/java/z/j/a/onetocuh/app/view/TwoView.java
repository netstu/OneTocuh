package z.j.a.onetocuh.app.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import z.j.a.onetocuh.R;
import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowView;
import z.j.a.onetocuh.app.utils.AppInfo;
import z.j.a.onetocuh.app.utils.AppSets;
import z.j.a.onetocuh.app.utils.Constants;
import z.j.a.onetocuh.app.utils.ImageUtil;
import z.j.a.onetocuh.app.utils.LocalUtil;

public class TwoView extends FloatWindowView {
    private View client_btn;
    private Context context;

    public TwoView(final Context context) {
        super(context);
        this.context = context;
        Float w = context.getResources().getDimension(R.dimen.start_width) * 3;//当前视图的宽
        setFloatWindowWidth(w.intValue());//设置当前悬浮窗口的宽
        Float h = context.getResources().getDimension(R.dimen.start_height) * 3;//当前视图的高
        setFloatWindowHeight(h.intValue());//设置当前悬浮窗口的高

        Integer[] startXY = LocalUtil.getLocal().get(OneView.class.getName());//获取初始按钮的坐标

        Integer[] strartWH = LocalUtil.getViewSize().get(OneView.class.getName());//初始按钮的宽高

        setFloatWindowX(startXY[0] - (getFloatWindowWidth() / 2 - strartWH[0] / 2));//设置当前窗口在屏幕中的X坐标
        setFloatWindowY(startXY[1] - (getFloatWindowHeight() / 2 - strartWH[1] / 2));//设置当前窗口在屏幕中的Y坐标




        LayoutInflater.from(context).inflate(R.layout.view_two, this);
        client_btn = (View) findViewById(R.id.client_btn);
        client_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowManager.removeFloatView();
                FloatWindowManager.createFloatWindow(getContext(), OneView.class);
            }
        });

        Button left_btn = (Button) findViewById(R.id.left_btn);
        left_btn.setOnClickListener(new btnOnclick());

        Button right_btn = (Button) findViewById(R.id.right_btn);
        right_btn.setOnClickListener(new btnOnclick());

        Button top_btn = (Button) findViewById(R.id.top_btn);
        top_btn.setOnClickListener(new btnOnclick());

        Button bottom_btn = (Button) findViewById(R.id.bottom_btn);
        bottom_btn.setOnClickListener(new btnOnclick());

        Drawable db = null;
        Map<String, AppInfo> appInfoMap = LocalUtil.getAppMaps();
        AppInfo appInfo = appInfoMap.get(AppSets.getAppToFloatWindow(Constants.btnLeft));
        if(appInfo!=null){
            db = appInfo.getAppicon();
            left_btn.setBackgroundDrawable(db);
            left_btn.setText(appInfo.getPackagename());
            left_btn.setTextSize(0);
        }
        appInfo = appInfoMap.get(AppSets.getAppToFloatWindow(Constants.btnRight));
        if(appInfo!=null){
            db = appInfo.getAppicon();
            right_btn.setBackgroundDrawable(db);
            right_btn.setText(appInfo.getPackagename());
            right_btn.setTextSize(0);
        }
        appInfo = appInfoMap.get(AppSets.getAppToFloatWindow(Constants.btnTop));
        if(appInfo!=null){
            db = appInfo.getAppicon();
            top_btn.setBackgroundDrawable(db);
            top_btn.setText(appInfo.getPackagename());
            top_btn.setTextSize(0);
        }
        appInfo = appInfoMap.get(AppSets.getAppToFloatWindow(Constants.btnBottom));
        if(appInfo!=null){
            db = appInfo.getAppicon();
            bottom_btn.setBackgroundDrawable(db);
            bottom_btn.setText(appInfo.getPackagename());
            bottom_btn.setTextSize(0);
        }
    }

    public void launchApp(String appName) throws Exception {
        Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(appName);//"jp.co.johospace.jorte"就是我们获得要启动应用的包名
        if (intent != null) {
            getContext().startActivity(intent);
        } else {
            throw new RuntimeException("应用 " + appName + " 无法以此方式启动");
        }
    }

    public class btnOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                String packageName = String.valueOf(((Button)view).getText());
                launchApp(packageName);
                FloatWindowManager.removeFloatView();
                FloatWindowManager.createFloatWindow(getContext(), OneView.class);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
