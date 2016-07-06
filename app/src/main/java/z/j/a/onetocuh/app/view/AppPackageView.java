package z.j.a.onetocuh.app.view;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import z.j.a.onetocuh.R;
import z.j.a.onetocuh.app.FloatWindowManager;
import z.j.a.onetocuh.app.FloatWindowView;
import z.j.a.onetocuh.app.utils.AppInfo;
import z.j.a.onetocuh.app.utils.AppSets;
import z.j.a.onetocuh.app.utils.Constants;
import z.j.a.onetocuh.app.utils.LocalUtil;

/**
 * Created by Lumia on 2016-07-05.
 */
public class AppPackageView extends FloatWindowView {
    private TableLayout sysTableLayout;
    private TableLayout userTableLayout;

    public AppPackageView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.app_package_view, this);
        setFloatWindowWidth(screenWidth*18/20);
        setFloatWindowHeight(screenHeight*18/20);
        setFloatWindowX(screenWidth*1/20);
        setFloatWindowY(screenHeight*1/20);
        sysTableLayout = (TableLayout) findViewById(R.id.sys_app_list);
        userTableLayout = (TableLayout) findViewById(R.id.user_app_list);
        LocalUtil.getAppList();
        addRow(LocalUtil.getAppList());
    }


    private void addRow(List<AppInfo> appList){
        TableRow tableRow = null;
        TextView textView = null;
        int i = 0;
        for(final AppInfo appInfo : appList){
            LocalUtil.puttAppID(20160101+i,appInfo.getPackagename());
            tableRow = new TableRow(getContext());

            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(appInfo.getAppicon());
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(110);
            imageView.setMaxWidth(110);
            imageView.setId(20160101+i);

            tableRow.addView(imageView,0);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btnName = getIntent().getExtras().getString(Constants.btnName);
                    AppSets.setAppToFloatWindow(btnName,LocalUtil.getAppID().get(view.getId()));
                    FloatWindowManager.removeFloatView();
                    FloatWindowManager.createFloatWindow(getContext(),OneView.class);
                }
            });

            textView = new TextView(getContext());
            textView.setText(String.valueOf(appInfo.getAppname()));
            tableRow.addView(textView,1);

            tableRow.setHorizontalGravity(Gravity.LEFT);
            tableRow.setVerticalGravity(Gravity.CENTER_VERTICAL);

            tableRow.setMinimumHeight(130);

            i++;
            if(appInfo.getSysapp()){
                sysTableLayout.addView(tableRow);
            }else{
                userTableLayout.addView(tableRow);
            }
        }
    }

}
