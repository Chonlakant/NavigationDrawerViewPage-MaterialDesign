package br.liveo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.liveo.navigationviewpagerliveo.R;


public class ActivityLivePlaying extends Activity {

    private VideoView video_view_;
    final Context context = this;


    WebView webView;
    String htmlPre = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\"></head><body style='marginBlush | :O ; padingBlush | :O ; background-color: black;'>";
    String htmlCode = " <embed style='width:100%; height:100%' src='http://www.platipus.nl/flvplayer/download/1.0/FLVPlayer.swf?fullscreen=true&video=@VIDEO@' " + " autoplay='true' " + " quality='high' bgcolor='#000000' " + " name='VideoPlayer' align='middle'" + // width='640' height='480'
            " allowScriptAccess='*' allowFullScreen='true'" + " type='application/x-shockwave-flash' " +
            " pluginspage='http://www.macromedia.com/go/getflashplayer' />" + "";
    String htmlPost = "</body></html>";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_player_live);

        Intent intent = getIntent();
        String urlLive = intent.getStringExtra("url");

        Toast.makeText(getApplicationContext(), urlLive, Toast.LENGTH_LONG).show();

        CircularImageView imageView = (CircularImageView) findViewById(R.id.imageView10);

        Picasso.with(context)
                .load("https://www.vdomax.com/photos/2015/01/6oCCf_88845_fb60d93c210068b4a03cd16c0018d8dd.jpg")
                .fit().centerCrop()
                .into(imageView);
        webView = (WebView) findViewById(R.id.webview);

        webView.setWebChromeClient(new WebChromeClient());


        webView.getSettings().setAllowFileAccess(true);


        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        String video_link = urlLive;
        htmlCode = htmlCode.replaceAll("@VIDEO@", urlLive);

        System.out.println(htmlPre + htmlCode + htmlPost);
        webView.loadDataWithBaseURL("fake://fake/fake", htmlPre + htmlCode + htmlPost, "text/html", "UTF-8", null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        callHiddenWebViewMethod("onPause");


        webView.pauseTimers();
        if (isFinishing()) {
            webView.loadUrl("about:blank");
            setContentView(new FrameLayout(this));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        callHiddenWebViewMethod("onResume");
        webView.resumeTimers();
    }

    private void callHiddenWebViewMethod(String name) {
// credits: http://stackoverflow.com/questions/3431351/how-do-i-pause-flash-content-in-an-android-webview-when-my-activity-isnt-visible
        if (webView != null) {
            try {
                Method method = WebView.class.getMethod(name);
                method.invoke(webView);
            } catch (NoSuchMethodException e) {
                Log.d("No such method: " + name + e, "");
            } catch (IllegalAccessException e) {
                Log.d("Illegal Access: " + name + e, "");
            } catch (InvocationTargetException e) {
                Log.d("Invocation Target Exception: " + name + e, "");
            }
        }
    }


}








