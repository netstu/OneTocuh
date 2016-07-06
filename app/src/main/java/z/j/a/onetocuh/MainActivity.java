package z.j.a.onetocuh;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowService;
import z.j.a.onetocuh.app.utils.AppInfo;
import z.j.a.onetocuh.app.utils.AppSets;
import z.j.a.onetocuh.app.utils.Constants;
import z.j.a.onetocuh.app.utils.LocalUtil;
import z.j.a.onetocuh.app.view.AppPackageView;
import z.j.a.onetocuh.app.view.FullscreenView;
import z.j.a.onetocuh.app.view.OneView;
import z.j.a.onetocuh.app.view.TwoView;

public class MainActivity extends Activity {
    private Button loadStartIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSets.getInstance(getBaseContext());
        AppSets.setAppToFloatWindow("myList");
        if(LocalUtil.getAppList()==null){
            new InitAppThread().run();
        }
        initUI();
    }

    private void initUI() {
        loadStartIcon = (Button)findViewById(R.id.loadStartIcon);
        loadStartIcon.setOnClickListener(new loadStartIcon());


        Button btn4 = (Button)findViewById(R.id.left_btn);
        btn4.setText(Constants.btnLeft);
        btn4.setOnClickListener(new btn4Listener());

        btn4 = (Button)findViewById(R.id.right_btn);
        btn4.setText(Constants.btnRight);
        btn4.setOnClickListener(new btn4Listener());

        btn4 = (Button)findViewById(R.id.top_btn);
        btn4.setText(Constants.btnTop);
        btn4.setOnClickListener(new btn4Listener());

        btn4 = (Button)findViewById(R.id.bottom_btn);
        btn4.setText(Constants.btnBottom);
        btn4.setOnClickListener(new btn4Listener());

    }

    public class loadStartIcon implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            //启动悬浮窗口
            startMyService(OneView.class.getName());
        }
    }

    public class btn4Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String btnName = String.valueOf(((Button) view).getText());
            Intent intent = new Intent(MainActivity.this, FloatWindowManager.class);
            intent.putExtra(Constants.btnName, btnName);
            intent.putExtra(Constants.transparent, "NO");
            //intent.putExtra(Constants.className, AppPackageView.class.getName());
            //startService(intent);

            FloatWindowManager.removeFloatView();
            FloatWindowManager.createFloatWindow(getBaseContext(),FullscreenView.class);
            FloatWindowManager.setIntent(intent);
            FloatWindowManager.createFloatWindow(getBaseContext(),AppPackageView.class);

        }
    }

    class InitAppThread implements Runnable{
        public void run(){
            initAppList();
        }
    }

    public List<AppInfo> initAppList(){
        ArrayList<AppInfo> appList = LocalUtil.getAppList();
        if(appList!=null){
            return appList;
        }
        appList = new ArrayList<AppInfo>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.setAppname(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            tmpInfo.setPackagename(packageInfo.packageName);
            tmpInfo.setVersionName(packageInfo.versionName);
            tmpInfo.setVersionCode(packageInfo.versionCode);
            tmpInfo.setSysapp((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0);
            tmpInfo.setAppicon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            appList.add(tmpInfo);
            LocalUtil.setAppMap(packageInfo.packageName,tmpInfo);
        }
        LocalUtil.setAppList(appList);
        return appList;
    }

    public void startMyService(String target){
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        intent.putExtra(Constants.className, target);
        startService(intent);
        //finish();
    }

}
