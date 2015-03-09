package br.liveo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import br.liveo.navigationviewpagerliveo.R;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class ActivityLivePlaying extends Activity {

    private String path = "";
    private VideoView mVideoView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.acivity_player_live);
        mVideoView = (VideoView) findViewById(R.id.surface_view);


        path = "http://server-a.vdomax.com:8080/record/Nuchiko-260115_20:24:33.flv";
        if (path == "") {

            Toast.makeText(getApplication(), "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
            return;
        } else {

            mVideoView.setVideoPath(path);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }

    }
}








