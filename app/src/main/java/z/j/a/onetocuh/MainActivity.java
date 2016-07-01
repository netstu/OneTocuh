package z.j.a.onetocuh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import z.j.a.onetocuh.app.FloatWindowService;
import z.j.a.onetocuh.app.view.OneView;

public class MainActivity extends Activity {
    private Button suspend;
    private Button loadStartIcon;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        //	WindowService wind = new WindowService();
        suspend = (Button)findViewById(R.id.suspend);
        suspend.setOnClickListener(new suspendListener());

        loadStartIcon = (Button)findViewById(R.id.loadStartIcon);
        loadStartIcon.setOnClickListener(new loadStartIcon());
    }
    public class suspendListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            //启动悬浮窗口
            startMyService(OneView.class.getName());
            //startMyService(SmallWindowActivity.class.getName());
        }
    }

    public class loadStartIcon implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            //启动悬浮窗口
            startMyService(OneView.class.getName());
        }
    }

    public void startMyService(String target){
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        intent.putExtra("model",target);
        intent.putExtra("cls", target);
        startService(intent);
        finish();
    }

}
