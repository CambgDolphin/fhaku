package ca.mbg.fhaku;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Fhaku extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.main);
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

        LinearLayout layout = (LinearLayout)findViewById(R.id.mitorizubase);
        float scale = this.getResources().getDisplayMetrics().density;
        int px = (int)(dx * scale);
        int py = (int)(dy * scale);
        //Toast.makeText(this, "" + px + ":" + py + " " + scale, Toast.LENGTH_LONG).show();
        layout.setPadding(px, py, px, py);

        ImageView but01 = (ImageView)findViewById(R.id.but01);
        but01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Stoast("あああ");
            }
        });
        ImageView but02 = (ImageView)findViewById(R.id.but02);
        but02.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Stoast("いいい");
            	Intent intent = new Intent(Fhaku.this, MyQRread.class);
                startActivity(intent);
            }
        });
        ImageView but03 = (ImageView)findViewById(R.id.but03);
        but03.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Stoast("ううう");
            }
        });

        ImageView ico01 = (ImageView)findViewById(R.id.ico_knin);
        ico01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(Fhaku.this, ViewRoll.class);
                startActivity(intent);
                //Stoast("ううう");
            }
        });

    }

    public void Stoast(String c) {
        Toast.makeText(this,c, Toast.LENGTH_LONG).show();
    }





}
