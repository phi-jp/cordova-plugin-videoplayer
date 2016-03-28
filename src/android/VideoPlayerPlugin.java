package jp.phi.cordova.plugin.videoplayer;


import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class VideoPlayerPlugin extends CordovaPlugin implements OnCompletionListener, OnPreparedListener, OnErrorListener, OnDismissListener {
  private CallbackContext callbackContext;

  private VideoView videoView;
  private MediaPlayer player;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }


  private void show(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    final String urlString = args.getString(0);
    String path = urlString;

    this.callbackContext = callbackContext;

    // Main container layout
    LinearLayout main = new LinearLayout(cordova.getActivity());
    main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    main.setOrientation(LinearLayout.VERTICAL);
    main.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    main.setVerticalGravity(Gravity.CENTER_VERTICAL);

    videoView = new VideoView(cordova.getActivity());
    videoView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    main.addView(videoView);

    player = new MediaPlayer();
    player.setOnPreparedListener(this);
    player.setOnCompletionListener(this);
    player.setOnErrorListener(this);

    try {
      player.setDataSource(path);
    } catch(Exception e) {
      return ;
    }
  }


  @Override
  public boolean onError(MediaPlayer mp, int what, int extra) {
    // Log.e(LOG_TAG, "MediaPlayer.onError(" + what + ", " + extra + ")");
    // if(mp.isPlaying()) {
    //   mp.stop();
    // }
    // mp.release();
    // dialog.dismiss();
     return false;
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    // mp.start();
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    // Log.d(LOG_TAG, "MediaPlayer completed");
    // mp.release();
    // dialog.dismiss();
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    // Log.d(LOG_TAG, "Dialog dismissed");
    // if (callbackContext != null) {
    //   PluginResult result = new PluginResult(PluginResult.Status.OK);
    //   result.setKeepCallback(false); // release status callback in JS side
    //   callbackContext.sendPluginResult(result);
    //   callbackContext = null;
    // }
  }
}