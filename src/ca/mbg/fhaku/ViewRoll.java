package ca.mbg.fhaku;

import ca.mbg.fhaku.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewRoll extends Activity {
    int dx = 100;
    int dy = 100;
    int bw = 0;
    int tt = 0;
    int bt = 0;
    int ll = 0;
    int rl = 0;
    int bzt = 0;
    int bzh = 0;
    double sx = 0;
    double sy = 0;
    String lurl = "";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //setContentView(R.layout.main);

        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        sx = disp.getWidth();
        sy = disp.getHeight();

        double tx = sx / 8;
        double ty = sy / 8;

        dx = (int)tx;
        dy = (int)ty;

        //LinearLayout layout = new LinearLayout(this);
        //layout.setOrientation(LinearLayout.VERTICAL);
        //setContentView(layout);
        //Button btn = new Button(this);
        //btn.setText("動的に作成したボタン");
        //ClickListener listener = new ClickListener();
        //btn.setOnClickListener(listener);
        //layout.addView(btn);

        AbsoluteLayout layout = new AbsoluteLayout(this);
        //layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);


        WebView wv = new WebView(this);
        wv.setWebViewClient(new WebViewClient() {});
        wv.getSettings().setJavaScriptEnabled(true);

        wv.setWebViewClient(new TkFoxClient());

        //wv.loadUrl("file:///android_asset/index.html");
        //wv.loadUrl("https://dl.dropbox.com/u/88219936/index.html");
        wv.setLayoutParams(new AbsoluteLayout.LayoutParams((int)sx, (int)sy, 0, 0));
        wv.setId(5);
        layout.addView(wv);


        //Button btn = new Button(this);
        //int ti = 5;
        //btn.setLayoutParams(new AbsoluteLayout.LayoutParams(dx*6, dy*1, dx, dy*ti));
        //btn.setText("動的に作成したボタン");
        //ClickListener listener = new ClickListener();
        //btn.setOnClickListener(listener);
        //btn.setId(1);
        //layout.addView(btn);

        bw = (int)(sy * 7 / 60);
        tt = (int)(sy * 1 / 30);
        bt = (int)(sy * 51 / 60);
        ll = (int)(sx - (sy * 3 / 10));
        rl = (int)(sx - (sy * 3 / 20));
        bzt = (int)(sy * 49 / 60);
        bzh = (int)(sy * 12 / 60);

        ClickListener listener = new ClickListener();

        ImageView basez = new ImageView(this);
        basez.setLayoutParams(new AbsoluteLayout.LayoutParams(0, 0, 0, 0));
        basez.setBackgroundColor(Color.argb(255, 0, 0, 255));
        basez.setId(4);
        layout.addView(basez);

        ImageView btn01 = new ImageView(this);
        btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, ll, tt));
        btn01.setImageResource(drawable.bt_map);
        btn01.setId(1);
        btn01.setOnClickListener(listener);
        layout.addView(btn01);

        ImageView btn02 = new ImageView(this);
        btn02.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, rl, tt));
        btn02.setImageResource(drawable.bt_scan);
        btn02.setId(2);
        btn02.setOnClickListener(listener);
        layout.addView(btn02);

        ImageView btn03 = new ImageView(this);
        btn03.setLayoutParams(new AbsoluteLayout.LayoutParams(0, 0, 0, 0));
        btn03.setImageResource(drawable.bt_back);
        btn03.setId(3);
        btn03.setOnClickListener(listener);
        layout.addView(btn03);

        wv.loadUrl("http://fhaku.orchestra.io/fn01a001.html");

        //setContentView(R.layout.main);
    }

    public class TkFoxClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            setProgressBarIndeterminateVisibility(false);
            Log.d("PageRead", "Finish:" + url);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            setProgressBarIndeterminateVisibility(true);
            Log.d("PageRead", "Start:" + url);
            int ti = tt;
            //if (url.contains("http://www\\.google\\.com.*")) {
            if (url.startsWith("http://fhaku.orchestra.io/hakutan/")) {
                WebView wv = (WebView)findViewById(5);
                wv.setLayoutParams(new AbsoluteLayout.LayoutParams((int)sx, (int)bzt, 0, 0));
                ImageView basez = (ImageView)findViewById(4);
                basez.setLayoutParams(new AbsoluteLayout.LayoutParams((int)sx, bzh, 0, bzt));
                ImageView btn01 = (ImageView)findViewById(1);
                btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(0, 0, 0, 0));
                ImageView btn02 = (ImageView)findViewById(2);
                btn02.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, ll, bt));
                ImageView btn03 = (ImageView)findViewById(3);
                btn03.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, rl, bt));
            } else {
                WebView wv = (WebView)findViewById(5);
                wv.setLayoutParams(new AbsoluteLayout.LayoutParams((int)sx, (int)sy, 0, 0));
                ImageView basez = (ImageView)findViewById(4);
                basez.setLayoutParams(new AbsoluteLayout.LayoutParams(0, 0, 0, 0));
                ImageView btn01 = (ImageView)findViewById(1);
                btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, ll, tt));
                ImageView btn02 = (ImageView)findViewById(2);
                btn02.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, rl, tt));
                ImageView btn03 = (ImageView)findViewById(3);
                btn03.setLayoutParams(new AbsoluteLayout.LayoutParams(0, 0, 0, 0));
                lurl = url;
            }

            //Button btn01 = (Button)findViewById(1);
            //btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(bw, bw, rl, ti));
        }
    }

    class ClickListener implements OnClickListener {
		public void onClick(View v) {
			//Button b = (Button)v;
			//b.setText("ボタンが押されました");

			if (v.getId() == 1) {
            	Intent intent = new Intent(ViewRoll.this,Fhaku.class);
                startActivity(intent);
			}
			if (v.getId() == 2) {
            	Intent intent = new Intent(ViewRoll.this,MyQRread.class);
                startActivity(intent);
			}
			if (v.getId() == 3) {
                WebView wv = (WebView)findViewById(5);
		        wv.loadUrl(lurl);
				//finish();
            	//Intent intent = new Intent(ViewRoll.this,Fhaku.class);
                //startActivity(intent);
			}

		}
    };

}