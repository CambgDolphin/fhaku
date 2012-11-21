package ca.mbg.fhaku;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class InredisChromeClient extends WebChromeClient implements OnCompletionListener, OnErrorListener {
    private ViewRoll interfazWeb; // Use Your WebView instance instead

    private VideoView mCustomVideoView;

    private LinearLayout mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private LinearLayout mErrorConsoleContainer;
    static final FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.FILL_PARENT, Gravity.CENTER);

    public InredisChromeClient(ViewRoll iwi) {
        super();
        this.interfazWeb = iwi;
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        // super.onShowCustomView(view, callback);
        if (view instanceof FrameLayout) {
            mCustomViewContainer = (FrameLayout) view;
            mCustomViewCallback = callback;
            mContentView = (LinearLayout) interfazWeb.findViewById(R.id.mainContainer);
            if (mCustomViewContainer.getFocusedChild() instanceof VideoView) {
                mCustomVideoView = (VideoView) mCustomViewContainer.getFocusedChild();
                // frame.removeView(video);
                mContentView.setVisibility(View.GONE);
                mCustomViewContainer.setVisibility(View.VISIBLE);
                interfazWeb.setContentView(mCustomViewContainer);
                mCustomVideoView.setOnCompletionListener(this);
                mCustomVideoView.setOnErrorListener(this);
                mCustomVideoView.start();
            }
        }
    }

    public void onHideCustomView() {
        if (mCustomVideoView == null)
            return;
        // Hide the custom view.
        mCustomVideoView.setVisibility(View.GONE);
        // Remove the custom view from its container.
        mCustomViewContainer.removeView(mCustomVideoView);
        mCustomVideoView = null;
        mCustomViewContainer.setVisibility(View.GONE);
        mCustomViewCallback.onCustomViewHidden();
        // Show the content view.
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mCustomViewContainer.setVisibility(View.GONE);
        onHideCustomView();
        interfazWeb.setContentView(mContentView);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        interfazWeb.setContentView(R.layout.main);
        return true;
    }
}