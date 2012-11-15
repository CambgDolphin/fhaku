package ca.mbg.fhaku;

import android.app.Activity;
import android.os.Bundle;
import java.io.IOException;

import ca.mbg.fhaku.R.drawable;

import com.google.zxing.client.android.PlanarYUVLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.KeyEvent;
import android.os.*;

public class MyQRread extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback, Camera.AutoFocusCallback {
	private static final String TAG = "ZXingBase";
    private static final int MIN_PREVIEW_PIXCELS = 320 * 240;
    private static final int MAX_PREVIEW_PIXCELS = 800 * 480;
    //private static final int MAX_PREVIEW_PIXCELS = 3000 * 2000;
    private Camera myCamera;
    private SurfaceView surfaceView;
    private Boolean hasSurface;
    private Boolean initialized;
    private Point screenPoint;
    private Point previewPoint;
    private static final int DIALOG_EXIT = 0;
    private Handler handler;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        hasSurface = false;
        initialized = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Configuration config = getResources().getConfiguration();
        //this.setRequestedOrientation(Configuration.ORIENTATION_LANDSCAPE);

        //WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        //Display disp = wm.getDefaultDisplay();
        //double sx = disp.getWidth();
        //double sy = disp.getHeight();
        //int ty = (int)(sy * 7 / 60);
        //int tt1 = (int)(sy * 2 / 60);
        //int tt2 = (int)(sy * 11 / 60);
        //int tl = (int)(sx - (sy * 9 / 60));
        //ImageView btn01 = (ImageView)findViewById(R.id.qrbt01);
        //AbsoluteLayout layout = (AbsoluteLayout)findViewById(R.id.qral);
        //ImageView btn01 = new ImageView(this);
        //btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 20, 20, 20));
        //btn01.setImageResource(drawable.bt_back);
        //layout.addView(btn01);

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

        LinearLayout layout = (LinearLayout)findViewById(R.id.qrbtnbase);
        float scale = this.getResources().getDisplayMetrics().density;
        int px = (int)(dx * scale);
        int py = (int)(dy * scale);
        //Toast.makeText(this, "" + px + ":" + py + " " + scale, Toast.LENGTH_LONG).show();
        //layout.setPadding(px * 2, py * 2, 0, 0);

        //ImageView btn01 = (ImageView)findViewById(R.id.qrbut01);
        //btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 20, 20, 20));
        /*
        ImageView btn01 = (ImageView)findViewById(R.id.qrbut01);
        btn01.setLayoutParams(new AbsoluteLayout.LayoutParams(ty, ty, tl, tt1));
        ImageView btn02 = (ImageView)findViewById(R.id.qrbut02);
        btn02.setLayoutParams(new AbsoluteLayout.LayoutParams(ty, ty, tl, tt2));
        */

        setContentView(R.layout.qrread);

        ImageView but01 = (ImageView)findViewById(R.id.qrbut01);
        //but01.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 20, 20, 20));
        but01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myCamera.cancelAutoFocus();
            	closeCamera();
                finish();
            }
        });
        ImageView but02 = (ImageView)findViewById(R.id.qrbut02);
        but02.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myCamera.cancelAutoFocus();
            	closeCamera();
            	Intent intent = new Intent(MyQRread.this, Fhaku.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView = (SurfaceView)findViewById(R.id.preview_view);
        SurfaceHolder holder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(holder);
        } else {
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    protected void onPause() {
        closeCamera();
        if (!hasSurface) {
            SurfaceHolder holder = surfaceView.getHolder();
            holder.removeCallback(this);
        }
        super.onPause();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void onAutoFocus(boolean success, Camera camera) {
    	if (myCamera != null) {
            if (success)
                camera.setOneShotPreviewCallback(this);
            	//camera.setPreviewCallback(this);
            	//camera.setPreviewCallbackWithBuffer(this);
            	//camera.setPreviewCallback(this);
    	}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg){

        switch(keyCode){

            case android.view.KeyEvent.KEYCODE_MENU:
            case android.view.KeyEvent.KEYCODE_CAMERA:

                if (myCamera != null) {

                        Camera.Parameters parameters = myCamera.getParameters();
                        if (!parameters.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_FIXED)) {
                            myCamera.autoFocus(this);
                        }

                }

                return true;

            case android.view.KeyEvent.KEYCODE_BACK :
                //showDialog( DIALOG_EXIT );
                myCamera.cancelAutoFocus();
            	closeCamera();
                finish();
                return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent msg){

        if (myCamera != null) {

            Camera.Parameters parameters = myCamera.getParameters();
            if (!parameters.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_FIXED)) {
                myCamera.autoFocus(this);
            }

            return true;
        }

        return false;
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
    	//camera.setPreviewCallback(null);  // プレビューコールバックを解除
        //camera.stopPreview();
    	if (myCamera != null) {

	        View finderView = (View)findViewById(R.id.viewfinder_view);

	        int left = finderView.getLeft() * previewPoint.x / screenPoint.x;
	        int top = finderView.getTop() * previewPoint.y / screenPoint.y;
	        int width = finderView.getWidth() * previewPoint.x / screenPoint.x;
	        int height = finderView.getHeight() * previewPoint.y / screenPoint.y;

	        final int[] anchorPos = new int[2];
	        finderView.getLocationInWindow(anchorPos);
	        left = anchorPos[0];
	        top = anchorPos[1];

	        //Toast.makeText(this, "(" + screenPoint.x + "," + screenPoint.y + ")", Toast.LENGTH_LONG).show();
	        //
	        //Toast.makeText(this, "(" + previewPoint.x + "," + previewPoint.y + ")", Toast.LENGTH_LONG).show();
	        Log.i("てすと","(" + previewPoint.x + "," + previewPoint.y + ")");
	        //Toast.makeText(this, "(" + left + "," + top + ")-("+ width + "," + height + ")", Toast.LENGTH_LONG).show();
	        Log.i("テスト","01");
	        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data,previewPoint.x,previewPoint.y,left,top,width,height,false);
	        Log.i("テスト","02");

	        //int rotation = getWindowManager().getDefaultDisplay().getRotation();
	        //Toast.makeText(this, "" + rotation, Toast.LENGTH_LONG).show();

	        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	        Log.i("テスト","03");
	        MultiFormatReader reader = new MultiFormatReader();
	        try {
	            Result result = reader.decode(bitmap);
	            //Toast.makeText(this, result.getText(), Toast.LENGTH_LONG).show();
                myCamera.cancelAutoFocus();
            	closeCamera();
            	Intent intent = new Intent(MyQRread.this, ViewRoll.class);
                startActivity(intent);
	        } catch (Exception e) {
	            //Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
	            //Toast.makeText(this, "エラー発生: " + e.getMessage(), Toast.LENGTH_LONG).show();
	            Camera.Parameters parameters = myCamera.getParameters();
	            //if (!parameters.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_FIXED)) {
		        if (!parameters.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_INFINITY)) {
	                myCamera.autoFocus(this);
	                //camera.setOneShotPreviewCallback(this);
	            }
	            //Toast.makeText(this, "認識できませんでした。", Toast.LENGTH_LONG).show();
	        }
    	}
    	//camera.startPreview();
    	//camera.addCallbackBuffer();
    }

    private void initCamera(SurfaceHolder holder) {
        try {
            openCamera(holder);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    private void openCamera(SurfaceHolder holder) throws IOException {
        if (myCamera == null) {
            myCamera = Camera.open();
            if (myCamera == null) {
                throw new IOException();
            }
        }
        myCamera.setPreviewDisplay(holder);

        if (!initialized) {
            initialized = true;
            initFromCameraParameters(myCamera);
        }

        setCameraParameters(myCamera);
        myCamera.startPreview();
        Log.i("チェック","01");
        try {
        	Thread.sleep(2000);
        } catch (Exception e) {
        }
        //handler.post(new Runnable() {
		//	public void run() {
		//        Log.i("チェック","03");
		//        myCamera.autoFocus(MyQRread.this);
		//	}
		//});
		myCamera.autoFocus(MyQRread.this);

        Log.i("チェック","02");
    }

    //public void run() {
    //    Log.i("チェック","03");
    //    myCamera.autoFocus(this);
    //}

    private void closeCamera() {
        if (myCamera != null) {
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }
    }


    private void setCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        parameters.setPreviewSize(previewPoint.x, previewPoint.y);
        camera.setParameters(parameters);
    }

    private void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        WindowManager manager = (WindowManager)getApplication().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        if (width < height) {
            int tmp = width;
            width = height;
            height = tmp;
        }

        screenPoint = new Point(width, height);
        Log.d(TAG, "screenPoint = " + screenPoint);
        previewPoint = findPreviewPoint(parameters, screenPoint, false);
        Log.d(TAG, "previewPoint = " + previewPoint);

        /*
        int degrees = 0;
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result = (90 - degrees + 360) % 360;
        camera.setDisplayOrientation(result);
        */
    }

    private Point findPreviewPoint(Camera.Parameters parameters, Point screenPoint, boolean portrait) {
        Point previewPoint = null;
        int diff = Integer.MAX_VALUE;

        for (Camera.Size supportPreviewSize : parameters.getSupportedPreviewSizes()) {
            int pixels = supportPreviewSize.width * supportPreviewSize.height;
            if (pixels < MIN_PREVIEW_PIXCELS || pixels > MAX_PREVIEW_PIXCELS) {
                continue;
            }

            int supportedWidth = portrait ? supportPreviewSize.height : supportPreviewSize.width;
            int supportedHeight = portrait ? supportPreviewSize.width : supportPreviewSize.height;
            int newDiff = Math.abs(screenPoint.x * supportedHeight - supportedWidth * screenPoint.y);

            if (newDiff == 0) {
                previewPoint = new Point(supportedWidth, supportedHeight);
                break;
            }

            if (newDiff < diff) {
                previewPoint = new Point(supportedWidth, supportedHeight);
                diff = newDiff;
            }
        }
        if (previewPoint == null) {
            Camera.Size defaultPreviewSize = parameters.getPreviewSize();
            previewPoint = new Point(defaultPreviewSize.width, defaultPreviewSize.height);
        }

        return previewPoint;
    }

    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) {
            case DIALOG_EXIT:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(
                    "Really want to quit the application?"
                )
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	myCamera.cancelAutoFocus();
                    	closeCamera();
                        finish();
                    }})
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});
                    dialog = builder.create();
                    break;
            }
            default:
            dialog = null;
        }
        return dialog;
    }

}