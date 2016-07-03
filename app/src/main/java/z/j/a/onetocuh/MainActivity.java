package z.j.a.onetocuh;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import z.j.a.onetocuh.app.FloatWindowService;
import z.j.a.onetocuh.app.view.OneView;

public class MainActivity extends Activity {
    private Button loadStartIcon;
    private Button loadAppList;

    private TableLayout sysTableLayout;
    private TableLayout userTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        loadStartIcon = (Button)findViewById(R.id.loadStartIcon);
        loadStartIcon.setOnClickListener(new loadStartIcon());

        loadAppList = (Button)findViewById(R.id.loadAppList);
        loadAppList.setOnClickListener(new loadAppList());

        sysTableLayout = (TableLayout) findViewById(R.id.sys_app_list);
        userTableLayout = (TableLayout) findViewById(R.id.user_app_list);
    }

    public class loadStartIcon implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            //启动悬浮窗口
            startMyService(OneView.class.getName());
        }
    }

    public class loadAppList implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            List<AppInfo> appList = getAppList();
            addRow(appList);
        }
    }

    private void addRow(List<AppInfo> appList){
        TableRow tableRow = null;
        TextView textView = null;
        for(AppInfo appInfo : appList){
            tableRow = new TableRow(MainActivity.this);

            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(appInfo.appicon);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(150,150));


            tableRow.addView(imageView,0);

            textView = new TextView(MainActivity.this);
            textView.setText(String.valueOf(appInfo.appname));
            tableRow.addView(textView,1);

            textView = new TextView(MainActivity.this);
            textView.setText(String.valueOf(appInfo.packagename));
            tableRow.addView(textView,2);

            textView = new TextView(MainActivity.this);
            textView.setText(String.valueOf(appInfo.versionCode));
            tableRow.addView(textView,3);


            if(appInfo.sysapp){
                sysTableLayout.addView(tableRow);
            }else{
                userTableLayout.addView(tableRow);
            }
        }
    }

    public List<AppInfo> getAppList(){
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.appname = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            tmpInfo.packagename = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.sysapp = (packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0;
            tmpInfo.appicon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            appList.add(tmpInfo);
        }
        return appList;
    }

    public class AppInfo{
        private Integer versionCode=0;
        private String appname="";
        private String packagename="";
        private String versionName="";
        private Drawable appicon=null;
        private Boolean sysapp=false;
    }

    public void startMyService(String target){
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        intent.putExtra("model",target);
        intent.putExtra("cls", target);
        startService(intent);
        finish();
    }

}
