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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.liveo.navigationviewpagerliveo.R;


public class ActivityLivePlaying extends Activity {

    private VideoView video_view_;
    final Context context = this;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_player_live);
        video_view_ = (VideoView) findViewById(R.id.video_player);

        CircularImageView imageView = (CircularImageView) findViewById(R.id.imageView10);

        Picasso.with(context)
                .load("https://www.vdomax.com/photos/2015/01/6oCCf_88845_fb60d93c210068b4a03cd16c0018d8dd.jpg")
                .fit().centerCrop()
                .into(imageView);


    }


}

