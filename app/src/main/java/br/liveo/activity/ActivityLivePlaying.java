package br.liveo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import br.liveo.navigationviewpagerliveo.R;


public class ActivityLivePlaying extends Activity {

    private VideoView video_view_;
    final Context context = this;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_player_live);
        video_view_ = (VideoView) findViewById(R.id.video_player);


    }


}

