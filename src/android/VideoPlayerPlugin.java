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

import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class VideoPlayerPlugin extends CordovaPlugin {

  private CallbackContext callbackContext = null;

  private Dialog dialog = null;
  private VideoView videoView;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }


  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("show")) {
      show(action, args, callbackContext);
      return true;
    } else if (action.equals("destory")){
      destory(action, args, callbackContext);
      return true;
    }

    // method not found
    return false;
  }

  private void show(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (dialog != null) {
      dialog.dismiss();
      dialog = null;
    }

    final String urlString = args.getString(0);
    final JSONArray rect = args.getJSONArray(1);
    int x = rect.getInt(0);
    int y = rect.getInt(1);
    int width = rect.getInt(2);
    int height = rect.getInt(3);
    String path = urlString;
    Uri uri = Uri.parse(path);

    this.callbackContext = callbackContext;

    // dialog
    dialog = new Dialog(cordova.getActivity(), android.R.style.Theme_NoTitleBar);
    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

    // Main container layout
    LinearLayout main = new LinearLayout(cordova.getActivity());
    main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    main.setOrientation(LinearLayout.VERTICAL);
    main.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    main.setVerticalGravity(Gravity.CENTER_VERTICAL);

    videoView = new VideoView(cordova.getActivity());
    videoView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    videoView.setVideoURI(uri);
    videoView.start();
    main.addView(videoView);

    WindowManager wm = cordova.getActivity().getWindowManager();
    Display disp = wm.getDefaultDisplay();
    int dispWidth = disp.getWidth();
    int dispHeight = disp.getHeight();
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.x = x - dispWidth/2 + width/2;
    lp.y = y - dispHeight/2 + height/2;
    lp.width = width;
    lp.height = height;

    dialog.setContentView(main);
    dialog.show();
    dialog.getWindow().setAttributes(lp);

    // On Completion
    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mediaplayer) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, "Done"));
        dialog.dismiss();
        dialog = null;
      }
    });
  }

  private void destory(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (dialog != null) {
      dialog.dismiss();
      dialog = null;
    }
  }
}