package z.j.a.onetocuh.app.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import z.j.a.onetocuh.R;
import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowService;
import z.j.a.onetocuh.app.LocalUtil;
import z.j.a.onetocuh.app.view.OneView;
import z.j.a.onetocuh.app.view.TwoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    private Context context;
    private boolean isCurrentRunningForeground = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalUtil.addActivity(this);
        context = getBaseContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);
        Intent intent = new Intent(FullscreenActivity.this, FloatWindowService.class);
        intent.putExtra("cls", TwoView.class.getName());
        startService(intent);

        findViewById(R.id.fullscreen_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.createFloatWindow(context, OneView.class);
                finish();
            }
        });
    }

    /**
     * 判断当前Activity是否在前台运行
     * @return
     */
    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
