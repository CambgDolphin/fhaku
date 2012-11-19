package ca.mbg.fhaku;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Setting extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.setting);
        int dx = 100;
        int dy = 100;

        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        //Point pw = new Point();
        double sx = disp.getWidth();
        double sy = disp.getHeight();

        double tx = sx / 4;
        double ty = sy / 3;

        double ap = 0;

        if (tx > ty) {
        	ap = (sx - (ty * 4));
        	dx = (int)(ap / 2);
        	dy = 0;
        } else {
        	dx = 0;
        	ap = (sy - (tx * 3));
        	dy = (int)(ap / 2);
        }

        LinearLayout layout = (LinearLayout)findViewById(R.id.settingb);
        float scale = this.getResources().getDisplayMetrics().density;
        int px = (int)(dx * scale);
        int py = (int)(dy * scale);
        //Toast.makeText(this, "" + px + ":" + py + " " + scale, Toast.LENGTH_LONG).show();
        layout.setPadding(px, py, px, py);

        ImageView ico01 = (ImageView)findViewById(R.id.x03back);
        ico01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//Intent intent = new Intent(Fhaku.this, ViewRoll.class);
                //startActivity(intent);
            	finish();
                //Stoast("ううう");
            }
        });

        //setContentView(R.layout.setting);
    }

}
