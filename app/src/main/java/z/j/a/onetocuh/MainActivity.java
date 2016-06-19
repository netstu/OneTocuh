package z.j.a.onetocuh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Button suspend;
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
    }
    public class suspendListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //启动悬浮窗口关闭本窗口
            Intent intent = new Intent(MainActivity.this,WindowService.class);
            startService(intent);
            finish();
        }
    }

}
