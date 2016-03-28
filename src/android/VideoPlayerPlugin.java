package jp.phi.cordova.plugin.videoplayer;


import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

import android.app.Dialog;
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
  private CallbackContext callbackContext = null;

  private Dialog dialog;
  private VideoView videoView;
  private MediaPlayer player;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }


  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("show")) {
      show(action, args, callbackContext);
      return true;
    } else if (action.equals("destroy")){
      // showAds(action, args, callbackContext);
      return true;
    }

    // method not found
    return false;
  }

  private void show(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    final String urlString = args.getString(0);
    String path = urlString;

    this.callbackContext = callbackContext;

    // dialog
    dialog = new Dialog(cordova.getActivity(), android.R.style.Theme_NoTitleBar);
    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(true);
    dialog.setOnDismissListener(this);

    // Main container layout
    LinearLayout main = new LinearLayout(cordova.getActivity());
    main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    main.setOrientation(LinearLayout.VERTICAL);
    main.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    main.setVerticalGravity(Gravity.CENTER_VERTICAL);

    videoView = new VideoView(cordova.getActivity());
    videoView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//    videoView.setVideoPath(path);
    videoView.setVideoURI(Uri.parse(path));
    videoView.start();
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

    final SurfaceHolder mHolder = videoView.getHolder();
    mHolder.setKeepScreenOn(true);
    mHolder.addCallback(new SurfaceHolder.Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
        try {
          player.prepare();
        } catch (Exception e) {
          PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
          result.setKeepCallback(false); // release status callback in JS side
//          callbackContext.sendPluginResult(result);
//          callbackContext = null;
        }
      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
        player.release();
      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      }
    });

    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.MATCH_PARENT;

    dialog.setContentView(main);
    dialog.show();
    dialog.getWindow().setAttributes(lp);
//    cordova.getActivity().setContentView(main);
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
     mp.start();
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
//     Log.d("MediaPlayer completed");
     mp.release();
     dialog.dismiss();
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